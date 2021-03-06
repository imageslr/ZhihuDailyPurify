package com.zhukaihao.zhihudailypurify.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import com.zhukaihao.zhihudailypurify.R;
import com.zhukaihao.zhihudailypurify.ZhihuDailyPurifyApplication;
import com.zhukaihao.zhihudailypurify.adapter.NewsAdapter;
import com.zhukaihao.zhihudailypurify.bean.DailyNews;
import com.zhukaihao.zhihudailypurify.observable.NewsListFromDatabaseObservable;
import com.zhukaihao.zhihudailypurify.observable.NewsListFromZhihuObservable;
import com.zhukaihao.zhihudailypurify.support.Constants;
import com.zhukaihao.zhihudailypurify.task.SaveNewsListTask;
import com.zhukaihao.zhihudailypurify.ui.activity.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NewsListFragment extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener, Observer<List<DailyNews>> {
    private List<DailyNews> newsList = new ArrayList<>();

    private String date;
    private NewsAdapter mAdapter;

    // Fragment is single in SingleDayNewsActivity
    private boolean isToday;
    private boolean isRefreshed = false;
    private boolean isFromDatePicker = false;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            Bundle bundle = getArguments();
            date = bundle.getString(Constants.BundleKeys.DATE);
            isToday = bundle.getBoolean(Constants.BundleKeys.IS_FIRST_PAGE);
            isFromDatePicker = bundle.getBoolean(Constants.BundleKeys.IS_FROM_DATE_PICKER);

            setRetainInstance(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);

        assert view != null;
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.news_list);
        mRecyclerView.setHasFixedSize(!isToday);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        mAdapter = new NewsAdapter(newsList);
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.color_primary);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // 从数据库获取，数据库不为空时返回一个List<DailyNews>的Observable
        NewsListFromDatabaseObservable.ofDate(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    // 是否在获得焦点时刷新：1.获取到焦点，2.开启自动刷新，3.还没有刷新过
    private boolean shouldRefreshOnVisibilityChange(boolean isVisibleToUser) {

        boolean shouldAutoRefresh = ZhihuDailyPurifyApplication.getSharedPreferences()
                .getBoolean(Constants.SharedPreferencesKeys.KEY_SHOULD_AUTO_REFRESH, true);
        return isVisibleToUser && shouldAutoRefresh && !isRefreshed;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        refreshIf(shouldRefreshOnVisibilityChange(isVisibleToUser));
    }

    private void refreshIf(boolean prerequisite) {
        if (prerequisite) {
            doRefresh();
        }
    }

    private void doRefresh() {
        getNewsListObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);

        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(true);
        }
    }

    private Observable<List<DailyNews>> getNewsListObservable() {
        return NewsListFromZhihuObservable.ofDate(date);
    }

    @Override
    public void onRefresh() {
        doRefresh();
    }

    @Override
    public void onNext(List<DailyNews> newsList) {
        this.newsList = newsList;
    }

    @Override
    public void onError(Throwable e) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (isAdded()) {
            ((BaseActivity) getActivity()).showSnackbar(R.string.network_error);
        }
    }

    @Override
    public void onCompleted() {
        if(!newsList.isEmpty()) {
            isRefreshed = true;
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if(newsList.isEmpty() && isFromDatePicker) {
            mSwipeRefreshLayout.setRefreshing(true);
            doRefresh();
        }

        mAdapter.updateNewsList(newsList);
        new SaveNewsListTask(newsList).execute();
    }
}