package com.learn.threadpool;

import android.util.Log;

/**
 * create: Ren Zhongrui
 * date: 2021-01-02
 * description:
 */
public class ThreadTask implements Runnable {

    private int i;

    public ThreadTask(int i) {
        this.i = i;
    }

    @Override
    public void run() {
        Log.e("ThreadTask", "run: " + i);
    }
}
