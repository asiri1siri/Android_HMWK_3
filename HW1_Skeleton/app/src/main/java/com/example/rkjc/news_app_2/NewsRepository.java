package com.example.rkjc.news_app_2;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class NewsRepository {
    private static NewsItemDao mNewsItemDao;
    private LiveData<List<NewsItem>> mAllNewsItems;

    public NewsRepository(Application application) {
        NewsDatabase db = NewsDatabase.getDatabase(application.getApplicationContext());
        mNewsItemDao = db.newsItemDao();
        mAllNewsItems = mNewsItemDao.loadAllNewsItems();
    }

    private static class updateAsyncTask extends AsyncTask<URL, Void, Void>{
        private NewsItemDao mAsyncTaskDao;
        updateAsyncTask(NewsItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(URL... urls) {
            mAsyncTaskDao.clearAll();
            //put all news items inside repository
            URL url = urls[0];
            String newsApiResults = null;
            try {
                newsApiResults = NetworkUtils.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mAsyncTaskDao.insert(JsonUtils.parseNews(newsApiResults));
            return null;
        }
    }

    public static void syncDatabase() {
        new updateAsyncTask(mNewsItemDao).execute(NetworkUtils.buildUrl());
    }

    LiveData<List<NewsItem>> getAllNewsItems() {
        return mAllNewsItems;
    }
}
