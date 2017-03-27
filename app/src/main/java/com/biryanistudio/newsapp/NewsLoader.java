package com.biryanistudio.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Aakaash on 24/03/17 at 5:28 PM.
 */

class NewsLoader extends AsyncTaskLoader<List<NewsItem>> {

    NewsLoader(Context context) {
        super(context);
    }

    @Override
    public List<NewsItem> loadInBackground() {
        return Utils.fetchData();
    }
}
