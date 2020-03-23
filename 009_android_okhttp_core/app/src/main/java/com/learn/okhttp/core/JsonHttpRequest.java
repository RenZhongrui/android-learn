package com.learn.okhttp.core;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * create: Ren Zhongrui
 * date: 2020-03-23
 * description:
 */
public class JsonHttpRequest implements IHttpRequest{

    private String url;
    private byte[] data;
    private CallbackListener callbackListener;
    private HttpURLConnection urlConnection;

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setData(byte[] code) {
        this.data = code;
    }

    @Override
    public void setListener(CallbackListener listener) {
        this.callbackListener = listener;
    }

    @Override
    public void execute() {
        URL url =null;
        try {
            url = new URL(this.url);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(6000);
            urlConnection.setUseCaches(false);
            urlConnection.setReadTimeout(3000);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-type","application/json;charset=UTF-8");
            urlConnection.connect();
            OutputStream outputStream = urlConnection.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(outputStream);
            bos.write(data);
            bos.flush();
            outputStream.close();
            bos.close();
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = urlConnection.getInputStream();
                this.callbackListener.onSuccess(inputStream);
            } else {
                throw new Exception("runtime exception");
            }

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            urlConnection.disconnect();
        }
    }
}
