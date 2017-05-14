package com.zhukaihao.zhihudailypurify.task;

import android.os.AsyncTask;

import com.google.gson.GsonBuilder;

import java.util.List;

import com.zhukaihao.zhihudailypurify.ZhihuDailyPurifyApplication;
import com.zhukaihao.zhihudailypurify.bean.DailyNews;
import com.zhukaihao.zhihudailypurify.db.DailyNewsDataSource;

public class SaveNewsListTask extends AsyncTask<Void, Void, Void> {
    private List<DailyNews> newsList;

    public SaveNewsListTask(List<DailyNews> newsList) {
        this.newsList = newsList;
    }

    @Override
    protected Void doInBackground(Void... params) {
        if (newsList != null && newsList.size() > 0) {
            saveNewsList(newsList);
        }

        return null;
    }

    private void saveNewsList(List<DailyNews> newsList) {
        DailyNewsDataSource dataSource = ZhihuDailyPurifyApplication.getDataSource();
        String date = newsList.get(0).getDate();

        List<DailyNews> originalData = dataSource.newsOfTheDay(date);

        if (originalData == null || !originalData.equals(newsList)) {
            dataSource.insertOrUpdateNewsList(date, new GsonBuilder().create().toJson(newsList));
        }
    }
}