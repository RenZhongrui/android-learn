package com.learn.music.application;


import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.audio.utils.AudioHelper;
import com.lib.share.ShareManager;
import com.lib.video.VideoHelper;
import com.qihoo360.replugin.RePluginApplication;
import com.qihoo360.replugin.RePluginConfig;

public class MusicApplication extends RePluginApplication {

    private static MusicApplication mApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        //音频SDK初始化
        AudioHelper.init(this);
        //分享SDK初始化
        ShareManager.initSDK(this);
        //视频SDK初始化
        VideoHelper.init(this);
        //ARouter初始化
        ARouter.init(this);
        ARouter.openLog();
        ARouter.openDebug();
    }

    public static MusicApplication getInstance() {
        return mApplication;
    }

    @Override
    protected RePluginConfig createConfig() {
        RePluginConfig config = super.createConfig();
        //使插件可以使用主工程中的类
        config.setUseHostClassIfNotFound(true);
        return config;
    }
}
