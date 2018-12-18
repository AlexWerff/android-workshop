package com.alexwerff.hackernewsreader.controller;

import com.alexwerff.hackernewsreader.model.Article;
import com.alexwerff.hackernewsreader.model.HackerNews;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Singleton for simplicity
 */
public final class NewsHttpController implements Callback {
    private static NewsHttpController instance;

    private final OkHttpClient httpClient;
    private NewsListener newsListener;
    private Gson gson;

    private NewsHttpController() {
        this.httpClient = new OkHttpClient();
        this.gson = new Gson();
    }

    public static NewsHttpController getInstance() {
        if(instance == null) {
            instance = new NewsHttpController();
        }
        return instance;
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
        if(response.isSuccessful()) {
            String jsonContent = response.body().string();
            Type newsType = new TypeToken<HackerNews>(){}.getType();
            HackerNews hackerNews = gson.fromJson(jsonContent, newsType);
            if(this.newsListener != null) {
                this.newsListener.onNewsFetched(hackerNews.getArticles());
            }
        }
    }

    public void setNewsListener(NewsListener newsListener) {
        this.newsListener = newsListener;
    }


    public interface NewsListener {
        void onNewsFetched(List<Article> news);
    }
}
