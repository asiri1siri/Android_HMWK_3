package com.example.rkjc.news_app_2;

public class FirebaseSync
{
    public static void syncDatabaseAutomatically() {
        NewsRepository.syncDatabase();
    }
}
