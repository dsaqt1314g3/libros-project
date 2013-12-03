package edu.upc.eetac.dsa.dsaqt1314g3.libros.api.links;

import javax.ws.rs.core.UriInfo;

import edu.upc.eetac.dsa.dsaqt1314g3.libros.api.BookResource;
import edu.upc.eetac.dsa.dsaqt1314g3.libros.api.MediaType;
import edu.upc.eetac.dsa.dsaqt1314g3.libros.api.BookRootAPIResource;
import edu.upc.eetac.dsa.dsaqt1314g3.libros.api.model.Book;

import java.net.URI;
public class BookAPILinkBuilder {
	public final static Link buildURIRootAPI(UriInfo uriInfo) {
		URI uriRoot = uriInfo.getBaseUriBuilder()
				.path(BookRootAPIResource.class).build();
		Link link = new Link();
		link.setUri(uriRoot.toString());
		link.setRel("self bookmark");
		link.setTitle("Books API");
		link.setType(MediaType.BOOKS_API_LINK_COLLECTION);
 
		return link;
	}
 
	public static final Link buildURIBooks(UriInfo uriInfo, String rel) {
		return buildURIBooks(uriInfo, null, null, null, rel);
	}
 
	public static final Link buildURIBooks(UriInfo uriInfo, String offset,
			String length, String consulta, String rel) {
		URI uriBooks;
		if (offset == null && length == null)
			uriBooks = uriInfo.getBaseUriBuilder().path(BookResource.class)
					.build();
		else {
			if (consulta == null)
				uriBooks = uriInfo.getBaseUriBuilder()
						.path(BookResource.class).queryParam("offset", offset)
						.queryParam("length", length).build();
			else
				uriBooks = uriInfo.getBaseUriBuilder()
						.path(BookResource.class).queryParam("offset", offset)
						.queryParam("length", length)
						.queryParam("consulta", consulta).build();
		}
 
		Link self = new Link();
		self.setUri(uriBooks.toString());
		self.setRel(rel);
		self.setTitle("Books collection");
		self.setType(MediaType.BOOKS_API_BOOK_COLLECTION);
 
		return self;
	}
 
	public static final Link buildTemplatedURIBooks(UriInfo uriInfo, String rel) {
 
		return buildTemplatedURIBooks(uriInfo, rel, false);
	}
 
	public static final Link buildTemplatedURIBooks(UriInfo uriInfo,
			String rel, boolean consulta) {
		URI uriBooks;
		if (consulta)
			uriBooks = uriInfo.getBaseUriBuilder().path(BookResource.class)
					.queryParam("offset", "{offset}")
					.queryParam("length", "{length}")
					.queryParam("consulta", "{consulta}").build();
		else
			uriBooks = uriInfo.getBaseUriBuilder().path(BookResource.class)
					.queryParam("offset", "{offset}")
					.queryParam("length", "{length}").build();
 
		Link link = new Link();
		link.setUri(URITemplateBuilder.buildTemplatedURI(uriBooks));
		link.setRel(rel);
		if (consulta)
			link.setTitle("Books collection resource filtered by {consulta}");
		else
			link.setTitle("Books collection resource");
		link.setType(MediaType.BOOKS_API_BOOK_COLLECTION);
 
		return link;
	}
 
	public final static Link buildURIBook(UriInfo uriInfo, Book book) {
		URI stingURI = uriInfo.getBaseUriBuilder().path(BookResource.class).build();
		Link link = new Link();
		link.setUri(stingURI.toString());
		link.setRel("self");
		link.setTitle("Book " + book.getId());
		link.setType(MediaType.BOOKS_API_BOOK);
 
		return link;
	}
 
	public final static Link buildURIBookId(UriInfo uriInfo, String id,
			String rel) {
		URI bookURI = uriInfo.getBaseUriBuilder().path(BookResource.class)
				.path(BookResource.class, "getBook").build(id);
		Link link = new Link();
		link.setUri(bookURI.toString());
		link.setRel("self");
		link.setTitle("Book " + id);
		link.setType(MediaType.BOOKS_API_BOOK);
 
		return link;
	}
 
}
