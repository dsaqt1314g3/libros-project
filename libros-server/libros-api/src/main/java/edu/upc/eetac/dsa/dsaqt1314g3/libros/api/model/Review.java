package edu.upc.eetac.dsa.dsaqt1314g3.libros.api.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.upc.eetac.dsa.dsaqt1314g3.libros.api.links.Link;


public class Review {
	private String id;
	private String bookid;
	private String username;
	private String author;
	private String content;
	private Date last_modified;
	private List<Link> links = new ArrayList<Link>();
	
	public String getBookid() {
		return bookid;
	}
	public void setBookid(String bookid) {
		this.bookid = bookid;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getLast_modified() {
		return last_modified;
	}
	public void setLast_modified(Date last_modified) {
		this.last_modified = last_modified;
	}
	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public void addLink(Link link) {
		links.add(link);
	}
}
