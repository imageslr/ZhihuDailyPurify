package com.zhukaihao.zhihudailypurify.ui.activity;

/**
 * Created by zhukaihao on 17/5/13.
 */

import android.os.Bundle;
import com.zhukaihao.zhihudailypurify.R;
import com.zhukaihao.zhihudailypurify.ui.fragment.PrefsFragment;

public class PrefsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_frame, new PrefsFragment())
                .commit();
    }
}