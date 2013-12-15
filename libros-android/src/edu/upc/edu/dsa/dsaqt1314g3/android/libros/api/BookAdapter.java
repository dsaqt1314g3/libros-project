package edu.upc.edu.dsa.dsaqt1314g3.android.libros.api;

import java.util.ArrayList;

import edu.upc.eetac.dsa.dsaqt1314g3.android.libros.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BookAdapter extends BaseAdapter {
	
	private ArrayList<Book> data;
	private LayoutInflater inflater;
	
	private static class ViewHolder {
		TextView tvTitulo;
		TextView tvAutor;
		TextView tvEditorial;
	}
	@Override
	public int getCount() {
		return data.size();
	}
	 
	@Override
	public Object getItem(int position) {
		return data.get(position);
	}
	 
	@Override
	public long getItemId(int position) {
		return Long.parseLong(((Book)getItem(position)).getId());
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_row_books, null);
			viewHolder = new ViewHolder();
			viewHolder.tvTitulo = (TextView) convertView
					.findViewById(R.id.tvTitulo);
			viewHolder.tvAutor = (TextView) convertView
					.findViewById(R.id.tvAutor);
			viewHolder.tvEditorial = (TextView) convertView
					.findViewById(R.id.tvEditorial);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String titulo = data.get(position).getTitulo();
		String autor = data.get(position).getAutor();
		String editorial = data.get(position).getEditorial();
		viewHolder.tvAutor.setText(autor);
		viewHolder.tvEditorial.setText(editorial);
		viewHolder.tvTitulo.setText(titulo);
		return convertView;
	}

	public BookAdapter(Context context, ArrayList<Book> data) {
		super();
		inflater = LayoutInflater.from(context);
		this.data = data;
	}

}
