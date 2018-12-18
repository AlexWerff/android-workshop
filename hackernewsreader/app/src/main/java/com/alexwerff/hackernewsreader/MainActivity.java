package com.alexwerff.hackernewsreader;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.alexwerff.hackernewsreader.service.NewsService;
import com.alexwerff.hackernewsreader.view.NewsFragment;
import com.alexwerff.hackernewsreader.view.SettingsFragment;

public class MainActivity extends AppCompatActivity{


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_news:
                    getSupportActionBar().setTitle(R.string.title_news);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new NewsFragment())
                            .addToBackStack("NEWS") //To tap the back button without exiting the app
                            .commit();
                    return true;
                case R.id.navigation_settings:
                    getSupportActionBar().setTitle(R.string.title_settings);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new SettingsFragment())
                            .addToBackStack("SETTINGS") //To tap the back button without exiting the app
                            .commit();
                    return true;
            }
            return false;
        }
    };


    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //TODO Make DRY
        getSupportActionBar().setTitle(R.string.title_news);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new NewsFragment())
                .addToBackStack("NEWS") //To tap the back button without exiting the app
                .commit();
        startService(new Intent(this, NewsService.class));
    }


}
