package com.zhukaihao.zhihudailypurify.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.zhukaihao.zhihudailypurify.R;

/**
 * Created by zhukaihao on 17/5/13.
 */

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

        // 添加license内容
//        ((FrameLayout) findViewById(R.id.fragment_frame))
//                .addView((RelativeLayout) LayoutInflater.from(this)
//                        .inflate(R.layout.apache_license, null, false)
//                        .findViewById(R.id.apache_license_layout));
    }
}
