package edu.upc.eetac.dsa.dsaqt1314g3.libros.api.links;

import javax.ws.rs.core.UriBuilder;
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
		return buildURIBooks(uriInfo, null, null, null, null, rel);
	}

	public static final Link buildURIBooks(UriInfo uriInfo, String offset,
			String length, String titulo, String autor, String rel) {
		URI uriBooks = null;
		if (offset == null && length == null)
			uriBooks = uriInfo.getBaseUriBuilder().path(BookResource.class)
					.build();
		else {
			if (titulo == null && autor == null)
				uriBooks = uriInfo.getBaseUriBuilder().path(BookResource.class)
						.queryParam("offset", offset)
						.queryParam("length", length).build();
			else if (titulo == null)
				uriBooks = uriInfo.getBaseUriBuilder().path(BookResource.class)
						.queryParam("offset", offset)
						.queryParam("length", length)
						.queryParam("autor", autor).build();
			else if (autor == null)
				uriBooks = uriInfo.getBaseUriBuilder().path(BookResource.class)
						.queryParam("offset", offset)
						.queryParam("length", length)
						.queryParam("titulo", titulo).build();
			else
				uriBooks = uriInfo.getBaseUriBuilder().path(BookResource.class)
						.queryParam("offset", offset)
						.queryParam("length", length)
						.queryParam("titulo", titulo)
						.queryParam("autor", autor).build();
		}
		Link self = new Link();
		self.setUri(uriBooks.toString());
		self.setRel(rel);
		self.setTitle("Books collection");
		self.setType(MediaType.BOOKS_API_BOOK_COLLECTION);

		return self;
	}

	public static final Link buildTemplatedURIBooks(UriInfo uriInfo, String rel) {

		return buildTemplatedURIBooks(uriInfo, rel, false, false);
	}

	public static final Link buildTemplatedURIBooks(UriInfo uriInfo,
			String rel, boolean titulo, boolean autor) {
		URI uriBooks;
		if (titulo && autor)
			uriBooks = uriInfo.getBaseUriBuilder().path(BookResource.class)
					.queryParam("offset", "{offset}")
					.queryParam("length", "{length}")
					.queryParam("titulo", "{titulo}")
					.queryParam("autor", "{autor}").build();
		else if (titulo && !autor) {
			uriBooks = uriInfo.getBaseUriBuilder().path(BookResource.class)
					.queryParam("offset", "{offset}")
					.queryParam("length", "{length}")
					.queryParam("titulo", "{titulo}").build();
		} else if (!titulo && autor) {
			uriBooks = uriInfo.getBaseUriBuilder().path(BookResource.class)
					.queryParam("offset", "{offset}")
					.queryParam("length", "{length}")
					.queryParam("autor", "{autor}").build();
		} else
			uriBooks = uriInfo.getBaseUriBuilder().path(BookResource.class)
					.queryParam("offset", "{offset}")
					.queryParam("length", "{length}").build();

		Link link = new Link();
		link.setUri(URITemplateBuilder.buildTemplatedURI(uriBooks));
		link.setRel(rel);
		if (titulo && autor)
			link.setTitle("Books collection resource filtered by {titulo} or {autor}");
		else if (titulo && !autor)
			link.setTitle("Books collection resource filtered by {titulo}");
		else if (!titulo && autor)
			link.setTitle("Books collection resource filtered by {autor}");
		else
			link.setTitle("Books collection resource");
		link.setType(MediaType.BOOKS_API_BOOK_COLLECTION);
		return link;
	}

	public final static Link buildURIBook(UriInfo uriInfo, Book book) {
		URI stingURI = uriInfo.getBaseUriBuilder().path(BookResource.class)
				.build();
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
		link.setRel(rel);
		link.setTitle("Book " + id);
		link.setType(MediaType.BOOKS_API_BOOK);

		return link;
	}

	public final static Link buildURIReviews(UriInfo uriInfo,
			String rel, String bookid) {
		URI uriReview = uriInfo.getBaseUriBuilder().path(BookResource.class)
				.path(BookResource.class, "getReviews").build(bookid);
		Link link = new Link();
		link.setUri(uriReview.toString());
		link.setRel(rel);
		link.setTitle("Review collection resource from Book id "+ bookid);
		link.setType(MediaType.BOOKS_API_REVIEW_COLLECTION);
		return link;
	}
	
	public final static Link buildURIReviewId(UriInfo uriInfo,
			String rel, String reviewid, String bookid) {
		URI uriReview = uriInfo.getBaseUriBuilder().path(BookResource.class).path(BookResource.class, "getReview").build(bookid,reviewid);
		Link link = new Link();
		link.setUri(uriReview.toString());
		link.setRel(rel);
		link.setTitle("Review "+ reviewid);
		link.setType(MediaType.BOOKS_API_REVIEW);
		return link;
	}

}
