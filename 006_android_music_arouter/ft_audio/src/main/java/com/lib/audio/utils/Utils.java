package com.lib.audio.utils;

import com.lib.audio.model.AudioBean;
import com.lib.base.audio.model.CommonAudioBean;

import java.util.ArrayList;

public class Utils {

    /**
     * 毫秒转分秒
     */
    public static String formatTime(long time) {
        String min = (time / (1000 * 60)) + "";
        String second = (time % (1000 * 60) / 1000) + "";
        if (min.length() < 2) {
            min = 0 + min;
        }
        if (second.length() < 2) {
            second = 0 + second;
        }
        return min + ":" + second;
    }

    public static AudioBean convertFrom(CommonAudioBean audioBean) {
        AudioBean audio = new AudioBean(audioBean.id, audioBean.mUrl, audioBean.name, audioBean.author,
                audioBean.album, audioBean.albumInfo, audioBean.albumPic, audioBean.totalTime);
        return audio;
    }

    public static ArrayList<AudioBean> convertFrom(ArrayList<CommonAudioBean> audioBeans) {
        ArrayList<AudioBean> audios = new ArrayList<>();
        for (CommonAudioBean audioBean : audioBeans) {
            if (audioBean != null) {
                audios.add(convertFrom(audioBean));
            }
        }
        return audios;
    }
}
