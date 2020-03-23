package com.learn.okhttp;

import com.learn.okhttp.core.CallbackListener;
import com.learn.okhttp.core.HttpTask;
import com.learn.okhttp.core.IHttpRequest;
import com.learn.okhttp.core.IJsonDataListener;
import com.learn.okhttp.core.JsonCallbackListener;
import com.learn.okhttp.core.JsonHttpRequest;
import com.learn.okhttp.core.ThreadPoolManager;

/**
 * create: Ren Zhongrui
 * date: 2020-03-23
 * description:
 */
public class HttpUtil {

    public static <T, M> void sendJsonRequest(T requestData, String url, Class<M> response, IJsonDataListener iJsonDataListener) {
        IHttpRequest httpRequest = new JsonHttpRequest();
        CallbackListener callbackListener = new JsonCallbackListener<>(response, iJsonDataListener);
        HttpTask httpTask = new HttpTask(requestData, url, httpRequest, callbackListener);
        ThreadPoolManager.getInstance().addTask(httpTask);
    }
}
