package edu.upc.eetac.dsa.dsaqt1314g3.android.libros;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;

import edu.upc.edu.dsa.dsaqt1314g3.android.libros.api.Book;
import edu.upc.edu.dsa.dsaqt1314g3.android.libros.api.BookAPI;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BookDetail extends Activity {
	private final static String TAG = BookDetail.class.toString();
	private BookAPI api;

	private class FetchStingTask extends AsyncTask<URL, Void, Book> {
		private ProgressDialog pd;

		@Override
		protected Book doInBackground(URL... params) {
			Book sting = api.getBook(params[0]);
			return sting;
		}

		@Override
		protected void onPostExecute(Book result) {
			loadBook(result);
			if (pd != null) {
				pd.dismiss();
			}
		}

		@Override
		protected void onPreExecute() {
			pd = new ProgressDialog(BookDetail.this);
			pd.setTitle("Loading...");
			pd.setCancelable(false);
			pd.setIndeterminate(true);
			pd.show();
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.book_detail_layout);
		api = new BookAPI();
		URL url = null;
		try {
			url = new URL((String) getIntent().getExtras().get("url"));
		} catch (MalformedURLException e) {
		}
		(new FetchStingTask()).execute(url);
	}

	private void loadBook(Book book) {
		TextView textView1 = (TextView) findViewById(R.id.textView1);
		TextView textView2 = (TextView) findViewById(R.id.textView2);
		TextView textView3 = (TextView) findViewById(R.id.textView3);
		TextView textView4 = (TextView) findViewById(R.id.textView4);
		TextView textView5 = (TextView) findViewById(R.id.textView5);
		TextView textView6 = (TextView) findViewById(R.id.textView6);
		TextView textView7 = (TextView) findViewById(R.id.textView7);

		textView1.setText("Titulo: " + book.getTitulo());
		textView2.setText("Autor: " + book.getAutor());
		textView3.setText("Edición: " + book.getEdicion());
		textView4.setText("Editorial: " + book.getEditorial());
		textView5.setText("Fecha Edición: " + book.getFedicion());
		textView6.setText("Fecha Impresión: " + book.getFimpresion());
		textView7.setText("Lengua: " + book.getLengua());
	}
}
