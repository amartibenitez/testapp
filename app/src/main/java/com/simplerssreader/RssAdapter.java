package com.simplerssreader;

import java.net.URL;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ImageView;
import android.net.Uri;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.simplerssreader.imageutils.ImageLoader;
public class RssAdapter extends BaseAdapter {

	private final List<RssItem> items;
	private final Context context;
    private Bitmap loadedImage;
    private ImageLoader imgLoader;
	public RssAdapter(Context context, List<RssItem> items) {
		this.items = items;
		this.context = context;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int id) {
		return id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.rss_item, null);
			holder = new ViewHolder();
            holder.itemTitle = (TextView) convertView.findViewById(R.id.itemTitle);
            holder.itemImageUrl = (TextView) convertView.findViewById(R.id.itemImageUrl);
            holder.itemDescription = (TextView) convertView.findViewById(R.id.itemDescription);
            holder.itemImageThumb = (ImageView)convertView.findViewById(R.id.imageView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
        holder.itemTitle.setText(items.get(position).getTitle());
        holder.itemImageUrl.setText(items.get(position).getImage_url());
        holder.itemDescription.setText(items.get(position).getDescription());
        URL imageUrl = null;
        String strURL = items.get(position).getImage_url().trim().replace("/hdnea={param:hdnea}", "");
        try {

            imageUrl = new URL(items.get(position).getImage_url().trim());
            //HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            //conn.connect();
           // loadedImage = BitmapFactory.decodeStream(conn.getInputStream());

           // holder.itemImageThumb.setImageBitmap(loadedImage);

           // Uri imgUri=Uri.parse(items.get(position).getImage_url());
            //holder.itemImageThumb.setImageURI(imgUri);


            //Bitmap bmp = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());
           // holder.itemImageThumb.setImageBitmap(bmp);



            imgLoader = new ImageLoader(context.getApplicationContext());

            imgLoader.DisplayImage(strURL, holder.itemImageThumb);

        }
        catch (Exception ex){
            holder.itemImageUrl.setText(items.get(position).getImage_url() + " -* error: " + ex.getMessage());
        }

        return convertView;
	}

	static class ViewHolder {
		TextView itemTitle;
        TextView itemImageUrl;
        TextView itemDescription;
        ImageView itemImageThumb;
	}
}
