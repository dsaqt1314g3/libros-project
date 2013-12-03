package edu.upc.eetac.dsa.dsaqt1314g3.libros.api.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import edu.upc.eetac.dsa.dsaqt1314g3.libros.api.links.Link;

public class Book {
	private String id;
	private String titulo;
	private String autor;
	private String lengua;
	private String edicion;
	private String fedicion;
	private String fimpresion;
	private String editorial;
	private Date lastModified;
	private List<Link> links = new ArrayList<Link>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public String getLengua() {
		return lengua;
	}
	public void setLengua(String lengua) {
		this.lengua = lengua;
	}
	public String getEdicion() {
		return edicion;
	}
	public void setEdicion(String edicion) {
		this.edicion = edicion;
	}
	public String getFedicion() {
		return fedicion;
	}
	public void setFedicion(String fedicion) {
		this.fedicion = fedicion;
	}
	public String getFimpresion() {
		return fimpresion;
	}
	public void setFimpresion(String fimpresion) {
		this.fimpresion = fimpresion;
	}
	public String getEditorial() {
		return editorial;
	}
	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}
	public Date getLastModified() {
		return lastModified;
	}
	public void setLastModified(Date creationTimestamp) {
		this.lastModified = creationTimestamp;
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

