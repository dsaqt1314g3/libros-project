package edu.upc.eetac.dsa.dsaqt1314g3.libros.api;

public interface MediaType {
	public final static String BOOKS_API_USER = "application/vnd.books.api.user+json";
	public final static String BOOKS_API_USER_COLLECTION = "application/vnd.books.api.user.collection+json";
	public final static String BOOKS_API_BOOK = "application/vnd.books.api.book+json";
	public final static String BOOKS_API_BOOK_COLLECTION = "application/vnd.books.api.book.collection+json";
	public final static String BOOKS_API_REVIEW = "application/vnd.books.api.review+json";
	public final static String BOOKS_API_REVIEW_COLLECTION = "application/vnd.books.api.review.collection+json";
	public final static String BOOKS_API_ERROR = "application/vnd.dsa.books.error+json";
	public final static String BOOKS_API_LINK = "application/vnd.dsa.books.link+json";
	public final static String BOOKS_API_LINK_COLLECTION = "application/vnd.dsa.books.link.collection+json";
}