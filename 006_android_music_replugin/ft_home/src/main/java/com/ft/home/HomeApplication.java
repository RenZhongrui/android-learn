package com.ft.home;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.update.UpdateHelper;

public class HomeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //更新组件下载
        UpdateHelper.init(this);
        ARouter.init(this);
    }
}
