package edu.upc.eetac.dsa.dsaqt1314g3.libros.api;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import edu.upc.eetac.dsa.dsaqt1314g3.libros.api.model.BookError;


public class UserNotFoundException extends WebApplicationException {
	private final static String MESSAGE = "User not found";

	public UserNotFoundException() {
		super(Response
				.status(Response.Status.NOT_FOUND)
				.entity(new BookError(Response.Status.NOT_FOUND
						.getStatusCode(), MESSAGE))
				.type(MediaType.BOOKS_API_ERROR).build());
	}

}