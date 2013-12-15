package edu.upc.edu.dsa.dsaqt1314g3.android.libros.api;

import java.util.ArrayList;
import java.util.List;

public class ReviewCollection {
	private List<Review> reviews;
	private List<Link> links = new ArrayList<Link>();

	public ReviewCollection() {
		super();
		reviews = new ArrayList<Review>();
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public void addReview(Review review) {
		reviews.add(review);
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
