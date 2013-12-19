package edu.upc.eetac.dsa.dsaqt1314g3.android.libros;

import java.io.IOException;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;






import edu.upc.edu.dsa.dsaqt1314g3.android.libros.api.BookAPI;
import edu.upc.edu.dsa.dsaqt1314g3.android.libros.api.Review;
import edu.upc.edu.dsa.dsaqt1314g3.android.libros.api.ReviewAdapter;
import edu.upc.edu.dsa.dsaqt1314g3.android.libros.api.ReviewCollection;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class ReviewCollectionF extends ListActivity {
	private final static String TAG = ReviewCollectionF.class.toString();
	String serverAddress;
	String serverPort;
	private ArrayList<Review> reviewList = new ArrayList<Review>();
	private ReviewAdapter adapter;
	private BookAPI api;
	
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
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
		setContentView(R.layout.list_row_reviews);
	 
		reviewList = new ArrayList<Review>();
		adapter = new ReviewAdapter(this, reviewList);
		setListAdapter(adapter);
		api = new BookAPI();
		URL url = null;
		try {
			//url = new URL((String) getIntent().getExtras().get("url2"));
			url = new URL("http://" + serverAddress + ":" + serverPort
					+ "/libros-api/books/1/review");
		} catch (MalformedURLException e) {
		}
		(new FetchStingsTask()).execute(url);
    }
    
    private class FetchStingsTask extends AsyncTask<URL, Void, ReviewCollection> {
		private ProgressDialog pd;
	 
		@Override
		protected ReviewCollection doInBackground(URL... params) {
			ReviewCollection reviews = api.getReviews(params[0]);
			return reviews;
		}
		
		@Override
		protected void onPostExecute(ReviewCollection result) {
			addReviews(result);
			if (pd != null) {
				pd.dismiss();
			}
		}
	 
		@Override
		protected void onPreExecute() {
			pd = new ProgressDialog(ReviewCollectionF.this);
			pd.setTitle("Searching...");
			pd.setCancelable(false);
			pd.setIndeterminate(true);
			pd.show();
		}
	 
	}
    
    private void addReviews(ReviewCollection reviews){
		reviewList.addAll(reviews.getReviews());
		adapter.notifyDataSetChanged();
	}
    
//    @Override
//	protected void onListItemClick(ListView l, View v, int position, long id) {
//		Book book = bookList.get(position);
//	 
//		// HATEOAS version
//		URL url = null;
//		URL urlrev = null;
//		try {
//			url = new URL(book.getLinks().get(0).getUri());
//			urlrev = new URL(book.getLinks().get(1).getUri());
//		} catch (MalformedURLException e) {
//			return;
//		}
//	 
//		// No HATEOAS
//		// URL url = null;
//		// try {
//		// url = new URL("http://" + serverAddress + ":" + serverPort
//		// + "/beeter-api/stings/" + id);
//		// } catch (MalformedURLException e) {
//		// return;
//		// }
//		Log.d(TAG, url.toString());
//		
//		Intent intent = new Intent(this, BookDetail.class);
//		intent.putExtra("url", url.toString());
//		intent.putExtra("urlrev", urlrev);
//		startActivity(intent);
//	}

}
