package com.biryanistudio.newsapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Aakaash on 24/03/17 at 5:30 PM.
 */

class Utils {

    static List<NewsItem> fetchData() {
        return extractData(makeHttpResponse(createUrl()));
    }

    private static URL createUrl() {
        try {
            return new URL("https://content.guardianapis.com/search?api-key=test");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String makeHttpResponse(URL url) {
        if (url != null)
            try {
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK)
                    return readFromStream(connection.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        return null;
    }

    private static String readFromStream(InputStream stream) {
        if (stream != null) {
            StringBuilder data = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            try {
                String line = reader.readLine();
                while (line != null) {
                    data.append(line);
                    line = reader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data.toString();
        }
        return null;
    }

    private static List<NewsItem> extractData(String data) {
        if (!data.isEmpty()) {
            List<NewsItem> newsItems = new ArrayList<>();
            try {
                JSONObject base = new JSONObject(data);
                JSONObject response = base.getJSONObject("response");
                if (response.has("results")) {
                    JSONArray results = response.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject result = results.getJSONObject(i);
                        if (result.has("webTitle") && result.has("webUrl") && result.has("sectionName"))
                            newsItems.add(new NewsItem(result.getString("webTitle"), result.getString("sectionName"), result.getString("webUrl")));
                    }
                }
                return newsItems;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    static Boolean isConnected(Context context) {
        NetworkInfo activeNetwork = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return (activeNetwork != null && activeNetwork.isConnectedOrConnecting());
    }
}
