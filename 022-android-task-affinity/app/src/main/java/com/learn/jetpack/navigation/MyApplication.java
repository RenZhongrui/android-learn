package com.learn.jetpack.navigation;

import android.app.Application;
import android.util.Log;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * create: Ren Zhongrui
 * date: 2020-11-25
 * description:
 */
public class MyApplication extends Application {
    private String TAG = MyApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "Application onCreate: " + getCurrentProcessName());
    }

    /**
     * 返回当前的进程名
     */
    public String getCurrentProcessName() {
        FileInputStream in = null;
        try {
            String fn = "/proc/self/cmdline";
            in = new FileInputStream(fn);
            byte[] buffer = new byte[256];
            int len = 0;
            int b;
            while ((b = in.read()) > 0 && len < buffer.length) {
                buffer[len++] = (byte) b;
            }
            if (len > 0) {
                String s = new String(buffer, 0, len, "UTF-8");
                return s;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
