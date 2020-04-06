package com.learn.handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tv_message;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            tv_message.setText(msg.arg1);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_message = findViewById(R.id.tv_message);
        new Thread(new Runnable() {
            @Override
            public void run() {
                int num = 10;
                while (num-- > 0) {
                    // 子线程将消息发送到主线程
                    Message message = new Message();
                    message.arg1 = num;
                    handler.sendMessage(message);
                }
            }
        }).start();
    }


}
