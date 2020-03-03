package com.learn.music.application;

import android.app.Application;

import com.lib.audio.utils.AudioHelper;
import com.lib.share.ShareManager;

public class MusicApplication extends Application {

    private static MusicApplication mApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        //音频SDK初始化
        AudioHelper.init(this);
        //分享SDK初始化
        ShareManager.initSDK(this);
    }

    public static MusicApplication getInstance() {
        return mApplication;
    }
}
