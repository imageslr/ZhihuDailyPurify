package com.zhukaihao.zhihudailypurify.ui.activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.zhukaihao.zhihudailypurify.R;

/**
 * Created by zhukaihao on 17/5/13.
 */

public class LicenseActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 添加license内容
        ((FrameLayout) findViewById(R.id.fragment_frame))
                .addView((RelativeLayout) LayoutInflater.from(this)
                        .inflate(R.layout.apache_license, null, false)
                        .findViewById(R.id.apache_license_layout));
    }
}
