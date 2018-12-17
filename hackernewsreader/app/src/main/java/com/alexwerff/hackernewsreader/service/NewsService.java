package com.alexwerff.hackernewsreader.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import com.alexwerff.hackernewsreader.controller.NewsHttpController;
import com.alexwerff.hackernewsreader.model.NewsItem;

import java.util.List;

public class NewsService extends Service implements Runnable, NewsHttpController.NewsListener {
    private static final int POLLING_NEWS_INTERVAL_MS = 10000;
    private NewsHttpController newsHttpController;
    private Handler handler;

    public NewsService() {
        this.newsHttpController = new NewsHttpController();
        this.handler = new Handler();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.newsHttpController.setNewsListener(this);
        run();
        return START_STICKY;
    }

    @Override
    public void run() {
        newsHttpController.fetchNews(0);
        handler.postDelayed(this, POLLING_NEWS_INTERVAL_MS);
    }

    @Override
    public void onNewsFetched(List<NewsItem> news) {
        //UPDATE UI -> Send to Activity
    }
}
