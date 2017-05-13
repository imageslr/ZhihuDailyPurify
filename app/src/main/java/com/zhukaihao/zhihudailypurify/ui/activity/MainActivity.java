package com.zhukaihao.zhihudailypurify.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.text.DateFormat;
import java.util.Calendar;

import com.zhukaihao.zhihudailypurify.R;

public class MainActivity extends BaseActivity {
    private static final int PAGE_COUNT = 7;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        layoutResID = R.layout.activity_main;

        super.onCreate(savedInstanceState);

        TabLayout tabs = (TabLayout) findViewById(R.id.main_pager_tabs);
        ViewPager viewPager = (ViewPager) findViewById(R.id.main_pager);
        assert tabs != null;
        assert viewPager != null;
        viewPager.setOffscreenPageLimit(PAGE_COUNT);

        // 界面测试
        ((FloatingActionButton) findViewById(R.id.fab_pick_date)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSnackbar("Hello, count " + ++count);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}