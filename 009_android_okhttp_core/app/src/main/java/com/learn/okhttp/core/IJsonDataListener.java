package com.learn.okhttp.core;

/**
 * create: Ren Zhongrui
 * date: 2020-03-23
 * description:
 */
public interface IJsonDataListener<T> {

    void onSuccess(T bean);

    void onFailure();
}
