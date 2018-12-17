package com.alexwerff.hackernewsreader.controller;

import com.alexwerff.hackernewsreader.model.NewsItem;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public final class NewsHttpController implements Callback {
    private final OkHttpClient httpClient;
    private NewsListener newsListener;

    public NewsHttpController() {
        this.httpClient = new OkHttpClient();
    }


    public void fetchNews(int page) {
        Request request = new Request
                .Builder()
                .url(HttpUtils.HACKER_NEWS_URL)
                .get()
                .build();
        httpClient.newCall(request).enqueue(this);
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {

    }

    public void setNewsListener(NewsListener newsListener) {
        this.newsListener = newsListener;
    }


    public interface NewsListener {
        void onNewsFetched(List<NewsItem> news);
    }
}
