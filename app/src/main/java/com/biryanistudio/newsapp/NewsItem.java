package com.biryanistudio.newsapp;

/**
 * Created by Aakaash on 24/03/17 at 5:20 PM.
 */

class NewsItem {

    private final String title;
    private final String category;
    private final String url;

    NewsItem(String title, String category, String url) {
        this.title = title;
        this.category = category;
        this.url = url;
    }

    String getTitle() {
        return title;
    }

    String getCategory() {
        return category;
    }

    String getUrl() {
        return url;
    }
}
