package com.biryanistudio.newsapp;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsItem>> {

    private final int BOOK_LOADER_ID = 100;
    private NewsAdapter newsAdapter;
    private LoaderManager loaderManager;
    private SwipeRefreshLayout refreshLayout;
    private TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        ListView newsList = (ListView) findViewById(R.id.news_list);
        emptyView = (TextView) findViewById(R.id.empty_view);
        emptyView.setText(R.string.loading);
        newsList.setEmptyView(emptyView);
        ArrayList<NewsItem> newsItems = new ArrayList<>();
        newsAdapter = new NewsAdapter(getApplicationContext(), newsItems);
        newsList.setAdapter(newsAdapter);
        loaderManager = getLoaderManager();
        loaderManager.initLoader(BOOK_LOADER_ID, null, MainActivity.this);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                refreshData();
            }
        });
        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(
                        newsAdapter.getItem(position).getUrl())));
            }
        });
        refreshData();
    }

    private void refreshData() {
        if (Utils.isConnected(getApplicationContext()))
            loaderManager.restartLoader(BOOK_LOADER_ID, null, MainActivity.this).forceLoad();
        else
            emptyView.setText(R.string.no_network);
    }

    @Override
    public Loader<List<NewsItem>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(getApplicationContext());
    }

    @Override
    public void onLoadFinished(Loader<List<NewsItem>> loader, List<NewsItem> data) {
        newsAdapter.clear();
        if (refreshLayout.isRefreshing())
            refreshLayout.setRefreshing(false);
        if (data != null && !data.isEmpty())
            newsAdapter.addAll(data);
        else
            emptyView.setText(R.string.no_data);
    }

    @Override
    public void onLoaderReset(Loader<List<NewsItem>> loader) {
        newsAdapter.clear();
    }
}
