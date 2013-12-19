package edu.upc.edu.dsa.dsaqt1314g3.android.libros.api;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import edu.upc.eetac.dsa.dsaqt1314g3.android.libros.R;

public class ReviewAdapter extends BaseAdapter {
	
	private ArrayList<Review> data;
	private LayoutInflater inflater;
	
	private static class ViewHolder {
		TextView tvUsuario;
		TextView tvContent;
		TextView tvDate;
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
		return Long.parseLong(((Review)getItem(position)).getId());
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_row_reviews, null);
			viewHolder = new ViewHolder();
			viewHolder.tvUsuario = (TextView) convertView
					.findViewById(R.id.tvUsuario);
			viewHolder.tvContent = (TextView) convertView
					.findViewById(R.id.tvContent);
			viewHolder.tvDate = (TextView) convertView
					.findViewById(R.id.tvDate);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String usuario = data.get(position).getAuthor();
		String content = data.get(position).getContent();
		String date = SimpleDateFormat.getInstance().format(
				data.get(position).getLast_modified());
		viewHolder.tvUsuario.setText(usuario);
		viewHolder.tvContent.setText(content);
		viewHolder.tvDate.setText(date);
		return convertView;
	}

	public ReviewAdapter(Context context, ArrayList<Review> data) {
		super();
		inflater = LayoutInflater.from(context);
		this.data = data;
	}

}
