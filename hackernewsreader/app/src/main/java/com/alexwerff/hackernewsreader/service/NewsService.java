package com.alexwerff.hackernewsreader.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

import com.alexwerff.hackernewsreader.controller.NewsHttpController;

public class NewsService extends Service implements Runnable {
    private static final int POLLING_NEWS_INTERVAL_MS = 10000;
    private Handler handler;

    public NewsService() {
        this.handler = new Handler();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        run();
        return START_STICKY;
    }

    @Override
    public void run() {
        NewsHttpController.getInstance().fetchNews(0);
        handler.postDelayed(this, POLLING_NEWS_INTERVAL_MS);
    }


    public class NewsServiceBinder extends Binder {
        NewsService getService() {
            return NewsService.this;
        }
    }
}
