package com.lib.audio.impl;

import android.app.Activity;
import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.audio.core.AudioController;
import com.lib.audio.utils.AudioHelper;
import com.lib.base.audio.AudioService;
import com.lib.base.audio.model.CommonAudioBean;

import java.util.ArrayList;

/**
 * AudioService实现类
 */
@Route(path = "/audio/audio_service")
public class AudioServiceImpl implements AudioService {

    @Override
    public void pauseAudio() {
        AudioController.getInstance().pause();
    }

    @Override
    public void resumeAudio() {
        AudioController.getInstance().resume();
    }

    @Override
    public void addAudio(Activity activity, CommonAudioBean audioBean) {
        AudioHelper.addAudio(activity, audioBean);
    }

    @Override
    public void startMusicService(ArrayList<CommonAudioBean> audioBeans) {
        AudioHelper.startMusicService(audioBeans);
    }

    @Override
    public void init(Context context) {

    }
}
