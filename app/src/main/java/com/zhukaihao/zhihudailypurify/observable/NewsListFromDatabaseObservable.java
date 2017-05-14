package com.zhukaihao.zhihudailypurify.observable;

import java.util.List;

import com.zhukaihao.zhihudailypurify.ZhihuDailyPurifyApplication;
import com.zhukaihao.zhihudailypurify.bean.DailyNews;
import rx.Observable;

public class NewsListFromDatabaseObservable {
    public static Observable<List<DailyNews>> ofDate(String date) {
        return Observable.create(subscriber -> {
            List<DailyNews> newsList
                    = ZhihuDailyPurifyApplication.getDataSource().newsOfTheDay(date);

            if (newsList != null) {
                subscriber.onNext(newsList);
            }

            subscriber.onCompleted();
        });
    }
}
