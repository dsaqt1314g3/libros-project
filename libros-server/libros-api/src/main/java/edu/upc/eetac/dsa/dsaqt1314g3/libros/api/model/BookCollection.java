package edu.upc.eetac.dsa.dsaqt1314g3.libros.api.model;

import java.util.ArrayList;
import java.util.List;

import edu.upc.eetac.dsa.dsaqt1314g3.libros.api.links.Link;

public class BookCollection {
	private List<Book> Books;
	private List<Link> links = new ArrayList<Link>();

	public BookCollection() {
		super();
		Books = new ArrayList<Book>();
	}

	public List<Book> getBooks() {
		return Books;
	}

	public void setBooks(List<Book> Books) {
		this.Books = Books;
	}

	public void addBook(Book Book) {
		Books.add(Book);
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}
	
	public void addLink(Link link){
		links.add(link);
	}
}