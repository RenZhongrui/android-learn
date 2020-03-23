package com.learn.okhttp.core;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * create: Ren Zhongrui
 * date: 2020-03-23
 * description:
 */
public class JsonCallbackListener<T> implements CallbackListener {

    private Class<T> responseClass;
    // Looper.getMainLooper() 保证从当前子线程直达主线程
    private Handler handle = new Handler(Looper.getMainLooper());

    private IJsonDataListener jsonDataListener;

    public JsonCallbackListener(Class<T> responseClass, IJsonDataListener jsonDataListener) {
        this.responseClass = responseClass;
        this.jsonDataListener = jsonDataListener;
    }


    @Override
    public void onSuccess(InputStream inputStream) {
        // 先将流转化为字符串
        String response = getStringResponse(inputStream);
        // 再将字符串转化成实体类，如果是user实体类，则转化为user类
        final T clazz = JSON.parseObject(response, responseClass);
        // 最后将这个实体类，从子线程中发送到主线程中
        handle.post(new Runnable() {
            @Override
            public void run() {
                jsonDataListener.onSuccess(clazz);
            }
        });

    }

    @Override
    public void onFailure() {

    }

    // 将InputStream转化成String
    public String getStringResponse(InputStream inputStream) {
        try {
            StringBuilder sb = new StringBuilder();
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            inputStream.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
