package com.zhukaihao.zhihudailypurify.observable;

import java.util.List;

import com.zhukaihao.zhihudailypurify.ZhihuDailyPurifyApplication;
import com.zhukaihao.zhihudailypurify.bean.DailyNews;
import rx.Observable;
import rx.Subscriber;

public class NewsListFromDatabaseObservable {
    public static Observable<List<DailyNews>> ofDate(String date) {
        return Observable.create(new Observable.OnSubscribe<List<DailyNews>>(){
            @Override
            public void call(Subscriber<? super List<DailyNews>> subscriber){
            List<DailyNews> newsList
                    = ZhihuDailyPurifyApplication.getDataSource().newsOfTheDay(date);

            if (newsList != null) {
                subscriber.onNext(newsList);
            }

            subscriber.onCompleted();
        }
        });
    }
}
