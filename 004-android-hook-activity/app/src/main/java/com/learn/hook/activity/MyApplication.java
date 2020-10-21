package com.learn.hook.activity;

import android.app.Application;
import android.util.Log;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("MyApplication","MyApplication");
        //LoadUtil.loadClass(this);
    }
}
