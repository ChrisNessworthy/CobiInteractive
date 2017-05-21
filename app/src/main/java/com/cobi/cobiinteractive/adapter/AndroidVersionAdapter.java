package com.cobi.cobiinteractive.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cobi.cobiinteractive.R;
import com.cobi.cobiinteractive.classes.AndroidVersionObject;
import com.cobi.cobiinteractive.network.URLConstants;
import com.cobi.cobiinteractive.utility.HelperMethods;
import com.cobi.cobiinteractive.utility.ImageDownloader;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.List;

public class AndroidVersionAdapter extends ArrayAdapter<AndroidVersionObject> {

    Activity mAct;
    public LayoutInflater mInflater;

    public class ViewHolder {
        View view;
        ImageView contentImage;
        TextView name;
    }

    public AndroidVersionAdapter(List<AndroidVersionObject> androidVersionObjects, Context context) {
        super(context, 0, androidVersionObjects);
        mInflater = (LayoutInflater)getContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        AndroidVersionObject item = getItem(position);

        ViewHolder holder;

        if(convertView == null){
            holder = getViewHolder();

            convertView = holder.view;

            convertView.setTag(R.string.ANDROID_VERSION_VIEW_TAG, holder);
        } else {
            holder = (ViewHolder) convertView.getTag(R.string.ANDROID_VERSION_VIEW_TAG);
        }

        if (holder == null) {
            holder = getViewHolder();
        }

        convertView = holder.view;

        if (item.getName() != null) {
            holder.name.setText(item.getName());
        }

        if (item.getImage() != null) {
            loadImage(URLConstants.URLS.URL + "/" + item.getImage(), holder.contentImage);
        }

        return convertView;
    }

    public void loadImage(String url, ImageView imageView){
        DisplayImageOptions options = HelperMethods.getDisplayOption();

        ImageDownloader.getImage(options, url, imageView);
    }

    private ViewHolder getViewHolder(){
        ViewHolder holder = new ViewHolder();

        holder.view = mInflater.inflate(R.layout.android_version_item, null);
        holder.contentImage = (ImageView) holder.view.findViewById(R.id.content_image);
        holder.name = (TextView) holder.view.findViewById(R.id.name);

        return holder;
    }

}