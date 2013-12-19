package edu.upc.edu.dsa.dsaqt1314g3.android.libros.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

public class BookAPI {
	private final static String TAG = BookAPI.class.toString();
	private final static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public Book getBook(URL url) {
		Book book = new Book();

		HttpURLConnection urlConnection = null;
		try {
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection
					.setRequestProperty("Accept", MediaType.BOOKS_API_BOOK);
			urlConnection.setRequestMethod("GET");
			urlConnection.setDoInput(true);
			urlConnection.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}

			JSONObject jsonBook = new JSONObject(sb.toString());
			book = parseBook(jsonBook);
		} catch (IOException e) {
			Log.e(TAG, e.getMessage(), e);
			return null;
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage(), e);
			return null;
		} catch (ParseException e) {
			Log.e(TAG, e.getMessage(), e);
			return null;
		} finally {
			if (urlConnection != null)
				urlConnection.disconnect();
		}

		return book;
	}

	public BookCollection getBooks(URL url) {
		BookCollection books = new BookCollection();

		HttpURLConnection urlConnection = null;
		try {
			urlConnection = (HttpURLConnection) url.openConnection();

			urlConnection.setRequestProperty("Accept",
					MediaType.BOOKS_API_BOOK_COLLECTION);
			urlConnection.setRequestMethod("GET");
			urlConnection.setDoInput(true);
			urlConnection.connect();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}

			JSONObject jsonObject = new JSONObject(sb.toString());
			JSONArray jsonLinks = jsonObject.getJSONArray("links");
			parseLinks(jsonLinks, books.getLinks());

			JSONArray jsonStings = jsonObject.getJSONArray("books");
			for (int i = 0; i < jsonStings.length(); i++) {
				JSONObject jsonBook = jsonStings.getJSONObject(i);
				Book libro = parseBook(jsonBook);

				books.addBook(libro);
			}
		} catch (IOException e) {
			Log.e(TAG, e.getMessage(), e);
			return null;
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage(), e);
			return null;
		} catch (ParseException e) {
			Log.e(TAG, e.getMessage(), e);
			return null;
		} finally {
			if (urlConnection != null)
				urlConnection.disconnect();
		}

		return books;
	}

	public ReviewCollection getReviews(URL url) {
		ReviewCollection reviews = new ReviewCollection();

		HttpURLConnection urlConnection = null;
		try {
			urlConnection = (HttpURLConnection) url.openConnection();

			urlConnection.setRequestProperty("Accept",
					MediaType.BOOKS_API_REVIEW_COLLECTION);
			urlConnection.setRequestMethod("GET");
			urlConnection.setDoInput(true);
			urlConnection.connect();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}

			JSONObject jsonObject = new JSONObject(sb.toString());
			JSONArray jsonLinks = jsonObject.getJSONArray("links");
			parseLinks(jsonLinks, reviews.getLinks());

			JSONArray jsonReviews = jsonObject.getJSONArray("reviews");
			for (int i = 0; i < jsonReviews.length(); i++) {
				JSONObject jsonReview = jsonReviews.getJSONObject(i);
				Review review = parseReview(jsonReview);

				reviews.addReview(review);
			}
		} catch (IOException e) {
			Log.e(TAG, e.getMessage(), e);
			return null;
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage(), e);
			return null;
		} catch (ParseException e) {
			Log.e(TAG, e.getMessage(), e);
			return null;
		} finally {
			if (urlConnection != null)
				urlConnection.disconnect();
		}

		return reviews;
	}

	private void parseLinks(JSONArray source, List<Link> links)
			throws JSONException {
		for (int i = 0; i < source.length(); i++) {
			JSONObject jsonLink = source.getJSONObject(i);
			Link link = new Link();
			link.setRel(jsonLink.getString("rel"));
			link.setTitle(jsonLink.getString("title"));
			link.setType(jsonLink.getString("type"));
			link.setUri(jsonLink.getString("uri"));
			links.add(link);
		}
	}

	private Book parseBook(JSONObject source) throws JSONException,
			ParseException {
		Book libro = new Book();
		libro.setId(source.getString("id"));
		libro.setTitulo(source.getString("titulo"));
		libro.setAutor(source.getString("autor"));
		libro.setLengua(source.getString("lengua"));
		libro.setEdicion(source.getString("edicion"));
		libro.setFedicion(source.getString("fedicion"));
		libro.setFimpresion(source.getString("fimpresion"));
		libro.setEditorial(source.getString("editorial"));
		String tsLastModified = source.getString("lastModified").replace("T",
				" ");
		libro.setLastModified(sdf.parse(tsLastModified));

		JSONArray jsonStingLinks = source.getJSONArray("links");
		parseLinks(jsonStingLinks, libro.getLinks());
		return libro;
	}

	private Review parseReview(JSONObject source) throws JSONException,
			ParseException {
		Review review = new Review();
		review.setId(source.getString("id"));
		//review.setAuthor(source.getString("autor"));
		review.setContent(source.getString("content"));
		review.setUsername(source.getString("username"));
		review.setBookid(source.getString("bookid"));
		String tsLastModified = source.getString("lastModified").replace("T",
				" ");
		review.setLast_modified(sdf.parse(tsLastModified));
		
		JSONArray jsonReviewLinks = source.getJSONArray("links");
		parseLinks(jsonReviewLinks, review.getLinks());
		return review;
	}
}
