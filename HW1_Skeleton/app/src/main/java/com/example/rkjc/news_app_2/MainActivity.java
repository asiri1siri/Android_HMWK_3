package com.example.rkjc.news_app_2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.List;

//
//  OLD news from cache/room + NEW news from refresh/sync
//

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView mRecyclerView;
    private ArrayList<NewsItem> articles = new ArrayList<>();
    private NewsAdapter mAdapter;
    private NewsItemViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewModel = ViewModelProviders.of(this).get(NewsItemViewModel.class);
        mRecyclerView = (RecyclerView) findViewById(R.id.news_recyclerview);
        mAdapter = new NewsAdapter(this, articles);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mViewModel.getAllNews().observe(this, new Observer<List<NewsItem>>() {
            @Override
            public void onChanged(@Nullable List<NewsItem> newsItems) {
                populateRecyclerView(newsItems);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
            mViewModel.sync();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void populateRecyclerView(List<NewsItem> newsItems) {
        Log.d(TAG, "parse news");
        mAdapter.mArticles.addAll(newsItems);
        mAdapter.notifyDataSetChanged();
        Log.d(TAG, "adapter size = " + mAdapter.getItemCount());
    }

}