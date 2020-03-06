package com.lib.base.audio;

import android.app.Activity;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.lib.base.audio.model.CommonAudioBean;

import java.util.ArrayList;

/**
 * 音频基本础对外接口
 */
public interface AudioService extends IProvider {
    void pauseAudio();

    void resumeAudio();

    void addAudio(Activity activity, CommonAudioBean audioBean);

    void startMusicService(ArrayList<CommonAudioBean> audioBeans);
}
