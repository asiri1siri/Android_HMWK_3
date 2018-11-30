package com.example.rkjc.news_app_2;


import org.junit.Test;

import java.net.URL;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class NetworkUnitTest {
    public static final String TAG = "NetworkUnitTest";

    @Test
    public void networkCallReturnsUsableJSON() throws Exception {
        URL url = new URL("https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=a9f6a1e8f38448bd8a8c0dbdb4b784a9"); // my key for testing api
        String result = NetworkUtils.getResponseFromHttpUrl(url);
        System.out.println(result);
        assert(result.contains("\"status\":\"ok\""));
    }
}