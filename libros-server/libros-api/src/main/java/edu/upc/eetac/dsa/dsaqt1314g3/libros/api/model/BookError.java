package edu.upc.eetac.dsa.dsaqt1314g3.libros.api.model;

public class BookError {
	private int status;
	private String message;

	public BookError() {
		super();
	}

	public BookError(int status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

