package edu.upc.eetac.dsa.dsaqt1314g3.libros.api;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import edu.upc.eetac.dsa.dsaqt1314g3.libros.api.MediaType;
import edu.upc.eetac.dsa.dsaqt1314g3.libros.api.model.BookError;

public class ForbiddenException extends WebApplicationException {
	private final static String MESSAGE = "No tienes permisos para esta acción";

	public ForbiddenException(String message) {
		super(Response
				.status(Response.Status.FORBIDDEN)
				.entity(new BookError(Response.Status.FORBIDDEN.getStatusCode(), message))
				.type(MediaType.BOOKS_API_ERROR).build());
	}

}