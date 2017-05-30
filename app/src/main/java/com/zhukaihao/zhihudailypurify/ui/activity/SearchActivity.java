package com.zhukaihao.zhihudailypurify.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import com.zhukaihao.zhihudailypurify.R;
import com.zhukaihao.zhihudailypurify.bean.DailyNews;
import com.zhukaihao.zhihudailypurify.observable.NewsListFromSearchObservable;
import com.zhukaihao.zhihudailypurify.ui.fragment.SearchNewsFragment;
import com.zhukaihao.zhihudailypurify.ui.widget.SearchTextView;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchActivity extends BaseActivity implements Observer<List<DailyNews>> {
    private SearchTextView searchView;
    private SearchNewsFragment searchNewsFragment;
    private ProgressDialog dialog;

    private Subscription searchSubscription;
    private List<DailyNews> newsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initDialog();

        searchNewsFragment = new SearchNewsFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_frame, searchNewsFragment)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 搜索出结果时，不再自动聚焦
        if(!newsList.isEmpty()) {
            searchView.setmClearingFocus(true);
        }
    }

    @Override
    public void onDestroy() {
        searchNewsFragment = null;

        super.onDestroy();
    }

    @Override   // 返回按钮的自定义事件
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initView() {
        searchView = new SearchTextView(this);
        searchView.setQueryHint(getResources().getString(R.string.search_hint));
        searchView.setOnQueryTextListener(query -> {
            dialog.show();
            searchView.clearFocus();
            searchSubscription = NewsListFromSearchObservable.withKeyword(query)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())           // 在io线程获取数据，在主线程显示数据
                    .doOnSubscribe(this::onSubscribe)       // 被观察者被订阅时执行的动作
                    .doOnUnsubscribe(this::onUnsubscribe)   // 被观察者被取消订阅时执行的动作
                    .subscribe(this);
            return true;
        });

        RelativeLayout relative = new RelativeLayout(this);
        relative.addView(searchView);

        mToolBar.addView(relative);

        setSupportActionBar(mToolBar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // 加上返回按钮
    }

    private void initDialog() {
        dialog = new ProgressDialog(SearchActivity.this);
        dialog.setMessage(getString(R.string.searching));
        dialog.setCancelable(true);
        dialog.setOnCancelListener(dialog -> {
            if (searchSubscription != null && !searchSubscription.isUnsubscribed()) {
                searchSubscription.unsubscribe();
            }
        });
    }

    private void onSubscribe() {
        dialog.show();
    }

    private void onUnsubscribe() {
        dialog.dismiss();
    }

    @Override
    public void onNext(List<DailyNews> newsList) {
        this.newsList = newsList;
    }

    @Override
    public void onError(Throwable e) {
        dialog.dismiss();
        showSnackbar(R.string.no_result_found);
    }

    @Override
    public void onCompleted() {
        dialog.dismiss();
        searchNewsFragment.updateContent(newsList);
    }
}
