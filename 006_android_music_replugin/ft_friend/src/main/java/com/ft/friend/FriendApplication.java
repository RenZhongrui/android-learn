package com.ft.friend;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.video.VideoHelper;

public class FriendApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.init(this);
        VideoHelper.init(this);
    }
}
