package edu.upc.eetac.dsa.dsaqt1314g3.libros.api;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

import edu.upc.eetac.dsa.dsaqt1314g3.libros.api.model.BookCollection;
import edu.upc.eetac.dsa.dsaqt1314g3.libros.api.model.Book;
import edu.upc.eetac.dsa.dsaqt1314g3.libros.api.links.BookAPILinkBuilder;

@Path("/books")
public class BookResource {
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();

	@Context
	private UriInfo uriInfo;

	@GET
	@Produces(MediaType.BOOKS_API_BOOK_COLLECTION)
	public BookCollection getBooks(@QueryParam("titulo") String titulo,
			@QueryParam("autor") String autor,
			@QueryParam("offset") String offset,
			@QueryParam("length") String length,
			@QueryParam("username") String username) {
		if ((offset == null) || (length == null))
			throw new BadRequestException(
					"offset and length are mandatory parameters");
		int ioffset, ilength, icount = 0;
		try {
			ioffset = Integer.parseInt(offset);
			if (ioffset < 0)
				throw new NumberFormatException();
		} catch (NumberFormatException e) {
			throw new BadRequestException(
					"offset must be an integer greater or equal than 0.");
		}
		try {
			ilength = Integer.parseInt(length);
			if (ilength < 1)
				throw new NumberFormatException();
		} catch (NumberFormatException e) {
			throw new BadRequestException(
					"length must be an integer greater or equal than 1.");
		}
		BookCollection books = new BookCollection();
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServiceUnavailableException(e.getMessage());
		}

		try {
			Statement stmt = conn.createStatement();
			String sql = null;
			if (autor != null && titulo != null) {
				sql = "select * from books where (titulo like '%" + titulo
						+ "%' AND autor like '%" + autor
						+ "%') ORDER BY lastModified desc LIMIT " + offset + ","
						+ length;
			} else if (autor == null && titulo != null) {
				sql = "select * from books where titulo like '%" + titulo
						+ "%' ORDER BY lastModified desc LIMIT " + offset + ","
						+ length;
			} else if (titulo == null && autor != null) {
				sql = "select * from books where autor like '%" + autor
						+ "%' ORDER BY lastModified desc LIMIT " + offset + ","
						+ length;
			} else {
				sql = "select * from books ORDER BY lastModified LIMIT "
						+ offset + "," + length;
			}
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Book libro = new Book();
				libro.setId(rs.getString("id"));
				libro.setTitulo(rs.getString("titulo"));
				libro.setAutor(rs.getString("autor"));
				libro.setLengua(rs.getString("lengua"));
				libro.setEdicion(rs.getString("edicion"));
				libro.setFedicion(rs.getString("fedicion"));
				libro.setFimpresion(rs.getString("fimpresion"));
				libro.setEditorial(rs.getString("editorial"));
				libro.setLastModified(rs.getTimestamp("lastModified"));
				libro.addLink(BookAPILinkBuilder.buildURIBookId(uriInfo,
						libro.getId(), "self"));
				books.addBook(libro);
				icount++;
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new InternalServerException(e.getMessage());
		}
		if (ioffset != 0) {
			String prevoffset = "" + (ioffset - ilength);
			books.addLink(BookAPILinkBuilder.buildURIBooks(uriInfo, prevoffset,
					length, titulo, autor, "prev"));
		}
		books.addLink(BookAPILinkBuilder.buildURIBooks(uriInfo, offset, length,
				titulo, autor, "self"));
		String nextoffset = "" + (ioffset + ilength);
		if (ilength <= icount) {
			books.addLink(BookAPILinkBuilder.buildURIBooks(uriInfo, nextoffset,
					length, titulo, autor, "next"));
		}
		return books;
	}

	@GET
	@Path("/{bookid}")
	@Produces(MediaType.BOOKS_API_BOOK)
	public Response getBook(@PathParam("bookid") String bookid,
			@Context Request req) {
		CacheControl cc = new CacheControl();
		Book libro = new Book();
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServiceUnavailableException(e.getMessage());
		}
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			String sql = "select * from books where id=" + bookid;
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				libro.setId(rs.getString("id"));
				libro.setTitulo(rs.getString("titulo"));
				libro.setAutor(rs.getString("autor"));
				libro.setLengua(rs.getString("lengua"));
				libro.setEdicion(rs.getString("edicion"));
				libro.setFedicion(rs.getString("fedicion"));
				libro.setFimpresion(rs.getString("fimpresion"));
				libro.setEditorial(rs.getString("editorial"));
				libro.setLastModified(rs.getTimestamp("lastModified"));
				libro.addLink(BookAPILinkBuilder.buildURIBookId(uriInfo,
						libro.getId(), "self"));
			} else {
				throw new BookNotFoundException();
			}
		} catch (SQLException e) {
			throw new InternalServerException(e.getMessage());
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				throw new InternalServerException(e.getMessage());
			}
		}
		EntityTag eTag = new EntityTag(Integer.toString(libro.getLastModified()
				.hashCode()));
		// Verify if it matched with etag available in http request
		Response.ResponseBuilder rb = req.evaluatePreconditions(eTag);
		// If ETag matches the rb will be non-null;
		// Use the rb to return the response without any further processing
		if (rb != null) {
			return rb.cacheControl(cc).tag(eTag).build();
		}
		// If rb is null then either it is first time request; or resource is
		// modified
		// Get the updated representation and return with Etag attached to it
		rb = Response.ok(libro).cacheControl(cc).tag(eTag);

		return rb.build();
	}

	@PUT
	@Path("/{bookid}")
	@Consumes(MediaType.BOOKS_API_BOOK)
	@Produces(MediaType.BOOKS_API_BOOK)
	public Book updateSting(@PathParam("bookid") String id, Book libro) {
		// IF de content > a 0
		// if (security.isUserInRole("registered")) {
		// if (!security.getUserPrincipal().getName()
		// .equals(sting.getUsername()))
		// throw new ForbiddenException("you are not allowed...");
		// } else {
		// // Si fuera admin le dejo pasar
		// }
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServiceUnavailableException(e.getMessage());
		}
		try {
			Statement stmt = conn.createStatement();
			String sql = "update books set books.titulo='" + libro.getTitulo()
					+ "',books.autor='" + libro.getAutor() + "',books.lengua='"
					+ libro.getLengua() + "',books.edicion='"
					+ libro.getEdicion() + "',books.fedicion='"
					+ libro.getFedicion() + "',books.fimpresion='"
					+ libro.getFimpresion() + "',books.editorial='"
					+ libro.getEditorial() + "' where books.id=" + id;
			int rs2 = stmt.executeUpdate(sql);
			if (rs2 == 0)
				throw new BookNotFoundException();
			sql = "select books.lastModified from books where books.id = " + id;
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				libro.setLastModified(rs.getTimestamp("lastModified"));
				libro.setId(id);
				libro.addLink(BookAPILinkBuilder.buildURIBookId(uriInfo,
						libro.getId(), "self"));
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new InternalServerException(e.getMessage());
		}
		return libro;
	}
	
	@DELETE
	@Path("/{bookid}")
	public void deleteSting(@PathParam("bookid") String id) {
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServiceUnavailableException(e.getMessage());
		}
		Statement stmt = null;
		String sql;
		try {
			stmt = conn.createStatement();
			sql = "delete from books where id=" + id;
			int rs2 = stmt.executeUpdate(sql);
			if (rs2 == 0)
				throw new BookNotFoundException();

		} catch (SQLException e) {
			throw new InternalServerException(e.getMessage());
		}
		finally {
			try {
				conn.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
