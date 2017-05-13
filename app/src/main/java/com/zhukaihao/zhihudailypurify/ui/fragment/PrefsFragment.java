package com.zhukaihao.zhihudailypurify.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.provider.SyncStateContract;

import com.zhukaihao.zhihudailypurify.R;
import com.zhukaihao.zhihudailypurify.ZhihuDailyPurifyApplication;
import com.zhukaihao.zhihudailypurify.support.Check;
import com.zhukaihao.zhihudailypurify.ui.activity.*;

/**
 * Created by zhukaihao on 17/5/13.
 */

public class PrefsFragment extends PreferenceFragment
        implements Preference.OnPreferenceClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);
        findPreference("about").setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (preference.getKey().equals("about")) {
            startActivity(new Intent(getActivity(), LicenseActivity.class));
            return true;
        }
        return false;
    }
}
