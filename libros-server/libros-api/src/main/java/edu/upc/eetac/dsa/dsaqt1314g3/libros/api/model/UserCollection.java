package edu.upc.eetac.dsa.dsaqt1314g3.libros.api.model;

import java.util.ArrayList;
import java.util.List;

public class UserCollection {
	private List<User> users;

	public UserCollection() {
		super();
		users = new ArrayList<User>();
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public void addUser(User user) {
		users.add(user);
	}

}
