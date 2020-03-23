package com.learn.okhttp.core;

import java.io.InputStream;

/**
 * create: Ren Zhongrui
 * date: 2020-03-23
 * description:
 */
public interface CallbackListener {

    void onSuccess(InputStream inputStream);

    void onFailure();

}
