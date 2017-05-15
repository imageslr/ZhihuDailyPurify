package com.zhukaihao.zhihudailypurify.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.zhukaihao.zhihudailypurify.R;

public class LicenseActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        layoutResID = R.layout.activity_apache_license;
        super.onCreate(savedInstanceState);

        TextView textView = (TextView) findViewById(R.id.license_text);

        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.licences_header)).append("\n");

        String[] basedOnProjects = getResources().getStringArray(R.array.apache_licensed_projects);

        for (String str : basedOnProjects) {
            sb.append("• ").append(str).append("\n");
        }

        sb.append("\n").append(getString(R.string.licenses_subheader));
        sb.append("\n\n").append(getString(R.string.apache_license));
        textView.setText(sb.toString());
    }
}
