package edu.upc.eetac.dsa.dsaqt1314g3.libros.api;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataSourceSPA2 {
	private DataSource dataSource;
	private static DataSourceSPA2 instance;

	private DataSourceSPA2() {
		super();
		Context envContext = null;
		try {
			envContext = new InitialContext();
			Context initContext = (Context) envContext.lookup("java:/comp/env");
			dataSource = (DataSource) initContext.lookup("jdbc/rolesdb");
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
	}

	public final static DataSourceSPA2 getInstance() {
		if (instance == null)
			instance = new DataSourceSPA2();
		return instance;
	}

	public DataSource getDataSource() {
		return dataSource;
	}
}