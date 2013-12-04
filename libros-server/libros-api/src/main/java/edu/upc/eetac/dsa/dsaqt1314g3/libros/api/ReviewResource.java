package edu.upc.eetac.dsa.dsaqt1314g3.libros.api;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.print.attribute.standard.Media;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import edu.upc.eetac.dsa.dsaqt1314g3.libros.api.links.BookAPILinkBuilder;
import edu.upc.eetac.dsa.dsaqt1314g3.libros.api.model.Review;
import edu.upc.eetac.dsa.dsaqt1314g3.libros.api.model.ReviewCollection;


@Path("/books/{bookid}")
public class ReviewResource {
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();

	@Context
	private UriInfo uriInfo;

	@GET
	@Path("/review")
	@Produces(MediaType.BOOKS_API_REVIEW_COLLECTION)
	public ReviewCollection getReviews(@PathParam("bookid") String bookid) {
		ReviewCollection reviews = new ReviewCollection();
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServiceUnavailableException(e.getMessage());
		}
		try {
			Statement stmt = conn.createStatement();
			String sql = null;
			if (bookid!=null){
				sql = "select * from reviews where bookid=" + bookid;
			}
			else{
				throw new BookNotFoundException();
			}
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Review reseña = new Review();
				reseña.setId(rs.getString("id"));
				reseña.setUsername(rs.getString("username"));
				reseña.setContent(rs.getString("content"));
				reseña.setLast_modified(rs.getTimestamp("last_modified"));
				//reseña.addLink(BookAPILinkBuilder.buildURIReviewId(uriInfo, "self", reseña.getId()));  FALTA AÑADIR EL LINK SELF AL COMENTARIO
				reviews.addReview(reseña);
			}
			reviews.addLink(BookAPILinkBuilder.buildURIReviews(uriInfo, "self", bookid));
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new InternalServerException(e.getMessage());
		}		
		return reviews;
	}
	
	@GET
	@Path("/review/{reviewid}")
	@Produces(MediaType.BOOKS_API_REVIEW)
	public Review getReview(@PathParam("bookid") String bookid, @PathParam("reviewid") String reviewid){
		Review reseña = null;
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServiceUnavailableException(e.getMessage());
		}
		try {
			Statement stmt = conn.createStatement();
			String sql = null;
			if (bookid!=null && reviewid!=null){
				sql = "select * from reviews where (bookid=" + bookid + " AND id=" + reviewid;
			}
			else{
				throw new ReviewNotFoundException();
			}
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				reseña = new Review();
				reseña.setId(rs.getString("id"));
				reseña.setUsername(rs.getString("username"));
				reseña.setContent(rs.getString("content"));
				reseña.setLast_modified(rs.getTimestamp("last_modified"));
				//reseña.addLink(BookAPILinkBuilder.buildURIReviewId(uriInfo, "self", reviewid)); FALTA AÑADIR EL LINK SELF AL COMENTARIO
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new InternalServerException(e.getMessage());
		}		
		return reseña;
	}
}