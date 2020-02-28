package com.lib.ui.base;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.lib.ui.R;
import com.lib.ui.utils.StatusBarUtil;

public class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        StatusBarUtil.statusBarLightMode(this);
    }
}
