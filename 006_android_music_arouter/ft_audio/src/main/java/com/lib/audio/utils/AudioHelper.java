package com.lib.audio.utils;

import android.app.Activity;
import android.content.Context;

import com.lib.audio.core.AudioController;
import com.lib.audio.core.MusicService;
import com.lib.audio.db.GreenDaoHelper;
import com.lib.audio.model.AudioBean;
import com.lib.audio.view.MusicPlayerActivity;
import com.lib.base.audio.model.CommonAudioBean;

import java.util.ArrayList;

/**
 * Created by qndroid on 19/5/20.
 *
 * @function 唯一与外界通信的帮助类
 */
public final class AudioHelper {

    //SDK全局Context, 供子模块用
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
        //初始化本地数据库
        GreenDaoHelper.initDatabase();
    }

    //外部启动MusicService方法
    public static void startMusicService(ArrayList<CommonAudioBean> audios) {
        MusicService.startMusicService(Utils.convertFrom(audios));
    }

    public static void addAudio(Activity activity, CommonAudioBean bean) {
        AudioController.getInstance().addAudio(Utils.convertFrom(bean));
        MusicPlayerActivity.start(activity);
    }

    public static void pauseAudio() {
        AudioController.getInstance().pause();
    }

    public static void resumeAudio() {
        AudioController.getInstance().resume();
    }

    public static Context getContext() {
        return mContext;
    }
}
