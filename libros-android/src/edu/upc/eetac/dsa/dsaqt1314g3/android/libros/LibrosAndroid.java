package edu.upc.eetac.dsa.dsaqt1314g3.android.libros;

import java.io.IOException;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import edu.upc.edu.dsa.dsaqt1314g3.android.libros.api.Book;
import edu.upc.edu.dsa.dsaqt1314g3.android.libros.api.BookAPI;
import edu.upc.edu.dsa.dsaqt1314g3.android.libros.api.BookAdapter;
import edu.upc.edu.dsa.dsaqt1314g3.android.libros.api.BookCollection;

public class LibrosAndroid extends ListActivity
{
	private final static String TAG = LibrosAndroid.class.toString();
	String serverAddress;
	String serverPort;
	private BookAPI api;
	private ArrayList<Book> bookList = new ArrayList<Book>();
	private BookAdapter adapter;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate()");
		 
		AssetManager assetManager = getAssets();
		Properties config = new Properties();
		try {
			config.load(assetManager.open("config.properties"));
			serverAddress = config.getProperty("server.address");
			serverPort = config.getProperty("server.port");
	 
			Log.d(TAG, "Configured server " + serverAddress + ":" + serverPort);
		} catch (IOException e) {
			Log.e(TAG, e.getMessage(), e);
			finish();
		}
		setContentView(R.layout.main);
	 
		bookList = new ArrayList<Book>();
		adapter = new BookAdapter(this, bookList);
		setListAdapter(adapter);
	 
//		SharedPreferences prefs = getSharedPreferences("beeter-profile", Context.MODE_PRIVATE);
//		final String username = prefs.getString("username", null);
//		final String password = prefs.getString("password", null);
//	 
//		Authenticator.setDefault(new Authenticator() {
//			protected PasswordAuthentication getPasswordAuthentication() {
//				return new PasswordAuthentication(username, password
//						.toCharArray());
//			}
//		});
//		Log.d(TAG, "authenticated with " + username + ":" + password);
	 
		api = new BookAPI();
		URL url = null;
		try {
			url = new URL("http://" + serverAddress + ":" + serverPort
					+ "/libros-api/books?&offset=0&length=20");
		} catch (MalformedURLException e) {
			Log.d(TAG, e.getMessage(), e);
			finish();
		}
		(new FetchStingsTask()).execute(url);
    }
    
    private class FetchStingsTask extends AsyncTask<URL, Void, BookCollection> {
		private ProgressDialog pd;
	 
		@Override
		protected BookCollection doInBackground(URL... params) {
			BookCollection stings = api.getBooks(params[0]);
			return stings;
		}
		
		@Override
		protected void onPostExecute(BookCollection result) {
			addBooks(result);
			if (pd != null) {
				pd.dismiss();
			}
		}
	 
		@Override
		protected void onPreExecute() {
			pd = new ProgressDialog(LibrosAndroid.this);
			pd.setTitle("Searching...");
			pd.setCancelable(false);
			pd.setIndeterminate(true);
			pd.show();
		}
	 
	}
    
    private void addBooks(BookCollection books){
		bookList.addAll(books.getBooks());
		adapter.notifyDataSetChanged();
	}
}
