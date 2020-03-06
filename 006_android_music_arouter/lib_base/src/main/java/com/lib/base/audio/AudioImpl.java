package com.lib.base.audio;

import android.app.Activity;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.base.audio.model.CommonAudioBean;

import java.util.ArrayList;

/**
 * 对AudioService包装，业务方直接调用，无需再自己初始化service类
 */
public class AudioImpl {
    private static AudioImpl mAudioImpl = null;

    @Autowired(name = "/audio/audio_service")
    protected AudioService mAudioService;

    public static AudioImpl getInstance() {
        if (mAudioImpl == null) {
            synchronized (AudioImpl.class) {
                if (mAudioImpl == null) {
                    mAudioImpl = new AudioImpl();
                }
            }
        }
        return mAudioImpl;
    }

    private AudioImpl() {
        ARouter.getInstance().inject(this);
    }

    /**
     * 暂停音乐播放器
     */
    public void pauseAudio() {
        mAudioService.pauseAudio();
    }

    /**
     * 恢复音乐播放器
     */
    public void resumeAudio() {
        mAudioService.resumeAudio();
    }

    /**
     * 添加并播放歌曲
     */
    public void addAudio(Activity activity, CommonAudioBean bean) {
        mAudioService.addAudio(activity, bean);
    }

    /**
     * 启动音乐播放服务
     */
    public void startMusicService(ArrayList<CommonAudioBean> audioBeans) {
        mAudioService.startMusicService(audioBeans);
    }
}
