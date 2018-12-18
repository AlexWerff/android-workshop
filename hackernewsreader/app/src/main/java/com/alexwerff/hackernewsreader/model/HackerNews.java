package com.alexwerff.hackernewsreader.model;

import java.util.List;

public class HackerNews {
    private List<Article> articles;

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
