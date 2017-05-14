package com.zhukaihao.zhihudailypurify.observable;

import java.util.List;

import com.zhukaihao.zhihudailypurify.bean.DailyNews;
import com.zhukaihao.zhihudailypurify.support.Constants;
import rx.Observable;

import static com.zhukaihao.zhihudailypurify.observable.Helper.getHtml;
import static com.zhukaihao.zhihudailypurify.observable.Helper.toNewsListObservable;

public class NewsListFromAccelerateServerObservable {
    public static Observable<List<DailyNews>> ofDate(String date) {
        return toNewsListObservable(getHtml(Constants.Urls.ZHIHU_DAILY_PURIFY_BEFORE, date));
    }
}
