package com.ft.home;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.audio.utils.AudioHelper;
import com.lib.update.UpdateHelper;
import com.lib.video.VideoHelper;

public class HomeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //音频SDK初始化
        AudioHelper.init(this);
        //更新组件下载
        UpdateHelper.init(this);
        //视频SDK初始化
        VideoHelper.init(this);
        ARouter.init(this);
    }
}
