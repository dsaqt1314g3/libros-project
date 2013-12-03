package edu.upc.eetac.dsa.dsaqt1314g3.libros.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import edu.upc.eetac.dsa.dsaqt1314g3.libros.api.links.BookAPILinkBuilder;




@Path("/")
public class BookRootAPIResource {
	@Context
	private UriInfo uriInfo;
	// TODO: Return links
	@GET
	@Produces(MediaType.BOOKS_API_LINK_COLLECTION)
	public BookRootAPI getLinks(){
		BookRootAPI root = new BookRootAPI();
		root.addLink(BookAPILinkBuilder.buildURIRootAPI(uriInfo));
		root.addLink(BookAPILinkBuilder.buildTemplatedURIBooks(uriInfo, "books"));
		root.addLink(BookAPILinkBuilder.buildTemplatedURIBooks(uriInfo, "books", true, true));
		//root.addLink(BookAPILinkBuilder.buildURIBooks(uriInfo, "create"));
		return root;
	}

}
