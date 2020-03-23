package com.learn.okhttp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.learn.okhttp.core.IJsonDataListener;
import com.learn.okhttp.core.R;

public class MainActivity extends AppCompatActivity {

    private String url = "http://v.juhe.cn/historyWeather/citys?province_id=2&key=bb52107206585ab074f5e59a8c73875b";
    private String url1 = "875b";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HttpUtil.sendJsonRequest(null, url1, Weather.class, new IJsonDataListener<Weather>() {
            @Override
            public void onSuccess(Weather bean) {
                Log.e("onSuccess: ", bean.toString());
            }

            @Override
            public void onFailure() {

            }
        });
    }
}
