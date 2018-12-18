package com.alexwerff.hackernewsreader.controller;

import android.content.Context;

import com.alexwerff.hackernewsreader.model.Article;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class NewsDataController {
    private static final String PREFERENCES_NEWS = "NEWS_PREFERENCES";
    private static final String PREFERENCES_KEY_NEWS = "NEWS";

    private Gson gson;

    public NewsDataController() {
        this.gson = new Gson(); //Instatiate before because it might take some time
    }

    public void saveNews(Context context, List<Article> news) {
        Type newsType = new TypeToken<Article>(){}.getType();
        String json = gson.toJson(news, newsType);
        context.getSharedPreferences(PREFERENCES_NEWS, Context.MODE_PRIVATE)
                .edit()
                .putString(PREFERENCES_KEY_NEWS, json)
                .apply();
    }

    public List<Article> getNews(Context context) {
        Type newsType = new TypeToken<Article>(){}.getType();
        String json = context.getSharedPreferences(PREFERENCES_NEWS, Context.MODE_PRIVATE)
                .getString(PREFERENCES_KEY_NEWS, "[]");
        return gson.fromJson(json, newsType);
    }
}
