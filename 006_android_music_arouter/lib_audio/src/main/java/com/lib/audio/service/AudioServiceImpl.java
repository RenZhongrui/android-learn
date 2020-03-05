package com.lib.audio.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.audio.core.AudioController;
import com.lib.base.audio.AudioService;

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
    public void init(Context context) {

    }
}
