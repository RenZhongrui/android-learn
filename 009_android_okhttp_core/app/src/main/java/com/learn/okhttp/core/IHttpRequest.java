package com.learn.okhttp.core;


/**
 * create: Ren Zhongrui
 * date: 2020-03-23
 * description:
 */
public interface IHttpRequest {

    // 封装请求接口
    void setUrl(String url);
    void setData(byte[] code);
    void setListener(CallbackListener listener);
    void execute();
}
