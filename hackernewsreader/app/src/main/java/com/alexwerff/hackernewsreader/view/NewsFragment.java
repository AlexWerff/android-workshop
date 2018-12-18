package com.alexwerff.hackernewsreader.view;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alexwerff.hackernewsreader.R;
import com.alexwerff.hackernewsreader.controller.NewsHttpController;
import com.alexwerff.hackernewsreader.model.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment implements AdapterView.OnItemClickListener, NewsHttpController.NewsListener {
    private ListView listViewNews;
    private ListViewNewsAdapter listViewNewsAdapter;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news, container, false);
        this.listViewNews = v.findViewById(R.id.list_view_news);
        List<Article> articles = new ArrayList<>();
        Article article = new Article();
        article.setTitle("New Hacker News Reader");
        article.setMessage("There is a new Hacker News Reader available. Check it out now.");
        articles.add(article);
        this.listViewNewsAdapter = new ListViewNewsAdapter(getContext(), R.layout.list_view_news_item, articles);
        this.listViewNews.setAdapter(this.listViewNewsAdapter);
        this.setHasOptionsMenu(true);
        this.setMenuVisibility(true);
        this.listViewNews.setOnItemClickListener(this);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        NewsHttpController.getInstance().setNewsListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        NewsHttpController.getInstance().setNewsListener(null);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.news_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        new AlertDialog.Builder(getContext())
                .setTitle("Item selected")
                .setMessage("Open the news or do something else in here.")
                .setPositiveButton("OK", null)
                .create()
                .show();
    }

    @Override
    public void onNewsFetched(final List<Article> news) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listViewNewsAdapter.clear();
                listViewNewsAdapter.addAll(news);
                listViewNewsAdapter.notifyDataSetChanged();
            }
        });
    }

    private class ListViewNewsAdapter extends ArrayAdapter<Article> {

        public ListViewNewsAdapter( Context context, int resource, List<Article> objects) {
            super(context, resource, objects);
        }


        @Override
        public View getView(int position, View convertView,ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            Article article = this.getItem(position);
            View v = inflater.inflate(R.layout.list_view_news_item, parent, false);
            TextView textViewTitle = v.findViewById(R.id.text_view_title);
            TextView textViewDetail = v.findViewById(R.id.text_view_detail);
            textViewTitle.setText(article.getTitle());
            textViewDetail.setText(article.getMessage());
            return v;
        }
    }

}
