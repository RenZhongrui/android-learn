package com.learn.okhttp.core;

import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * create: Ren Zhongrui
 * date: 2020-03-23
 * description:
 */
public class HttpTask<T> implements Runnable, Delayed {

    private IHttpRequest iHttpRequest;

    public HttpTask(T requestData, String url, IHttpRequest httpRequest, CallbackListener callbackListener) {
        httpRequest.setUrl(url);
        String content = JSON.toJSONString(requestData);
        try {
            httpRequest.setData(content.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        httpRequest.setListener(callbackListener);
        this.iHttpRequest = httpRequest;
    }

    @Override
    public void run() {
        try {
            iHttpRequest.execute();
        } catch (Exception e) {
            // 捕获异常进行重试
            ThreadPoolManager.getInstance().addDelayTask(this);
        }
    }

    // 几秒执行一次
    private long delayTime;
    // 需要重试几次
    private int retryCount;

    // get set 方法
    public long getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(long delayTime) {
        // 设置延迟时间
        this.delayTime = System.currentTimeMillis() + delayTime;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    // 继承Delayed接口，实现方法
    @Override
    public long getDelay(TimeUnit timeUnit) {
        // 返回要延迟的时间,进行转化成毫秒
        return timeUnit.convert(this.delayTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed delayed) {
        return 0;
    }

}
