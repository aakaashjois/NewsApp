package com.biryanistudio.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Aakaash on 24/03/17 at 5:21 PM at 11:34 AM.
 */

class NewsAdapter extends ArrayAdapter<NewsItem> {

    NewsAdapter(@NonNull Context context, @NonNull List<NewsItem> newsItems) {
        super(context, 0, newsItems);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null)
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.list_item, parent, false);
        NewsItem item = getItem(position);
        ((TextView) convertView.findViewById(R.id.item_title))
                .setText(item != null ? item.getTitle() : "No Title");
        ((TextView) convertView.findViewById(R.id.item_category))
                .setText(item != null ? item.getCategory() : "No Category");
        return convertView;
    }
}
