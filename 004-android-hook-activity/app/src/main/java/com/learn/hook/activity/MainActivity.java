package com.learn.hook.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //printClassLoader();
        invokePluginTest();
    }

    // 打印classloader
    private void printClassLoader() {
        ClassLoader classLoader = getClassLoader();
        while (classLoader != null) {
            Log.e(TAG, "printClassLoader: " + classLoader);
            classLoader = classLoader.getParent();
        }
    }

    private void invokePluginTest() {
        try {
            Class<?> clazz = Class.forName("com.learn.hook.plugin.Test");
            Method test = clazz.getMethod("test");
            test.invoke(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
