package edu.upc.eetac.dsa.dsaqt1314g3.libros.auth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import edu.upc.eetac.dsa.dsaqt1314g3.libros.auth.DataSourceSPA;

@SuppressWarnings("deprecation")
public class ServletRegister extends HttpServlet {

	DataSource ds = null;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String email = request.getParameter("email");

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String update = "insert into users values('" + username + "', MD5('"
				+ password + "'), '" + name + "', '" + email + "');";
		try {
			stmt.executeUpdate(update);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update = "insert into user_roles values ('" + username
				+ "', 'registered')";
		try {
			stmt.executeUpdate(update);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HttpHost targetHost = new HttpHost("localhost", 8080, "http");
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(new AuthScope(targetHost.getHostName(),
				targetHost.getPort()), new UsernamePasswordCredentials("admin",
				"admin"));

		// Create AuthCache instance
		AuthCache authCache = new BasicAuthCache();
		// Generate BASIC scheme object and add it to the local auth cache
		BasicScheme basicAuth = new BasicScheme();
		authCache.put(targetHost, basicAuth);

		// Add AuthCache to the execution context
		HttpClientContext context = HttpClientContext.create();
		context.setCredentialsProvider(credsProvider);

		HttpPost httpPost = new HttpPost(
				"http://localhost:8080/libros-api/users");
		httpPost.addHeader("Content-Type",
				"application/vnd.books.api.user+json");
		httpPost.addHeader("Accept", "application/vnd.books.api.user+json");

		String user2 = "{\"username\": \"" + username + "\", \"password\": \""
				+ password + "\", \"name\": \"" + name + "\", \"email\": \""
				+ email + "\" }";
		httpPost.setEntity(new StringEntity(user2));
		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();

		CloseableHttpResponse httpResponse = closeableHttpClient.execute(
				targetHost, httpPost, context);
		HttpEntity entity = httpResponse.getEntity();

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				entity.getContent()));
		String line = null;
		while ((line = reader.readLine()) != null)
			System.out.println(line);

		httpResponse.close();

//	request.setAttribute("username",username);
//		request.setAttribute("name",name);
//		request.setAttribute("email",email);
//		String url = "/Registrook2.jsp";
//		ServletContext sc = getServletContext();
//		RequestDispatcher rd = sc.getRequestDispatcher(url);
//		rd.forward(request, response);
		
	 response.setContentType("text/html");
	 PrintWriter out = response.getWriter();
	 out.write("succes!");
	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		ds = DataSourceSPA.getInstance().getDataSource();

	}

}
