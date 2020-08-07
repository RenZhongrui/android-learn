package com.learn.thread.handler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int MSG_CPU = 1;
    /**
     * 信息采集时间 内存和cpu
     */
    private static final int NORMAL_SAMPLING_TIME = 500;

    private HandlerThread mHandlerThread;
    /**
     * 默认的采集时间 通常为1s
     */
    private Handler workHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (mHandlerThread == null) {
            mHandlerThread = new HandlerThread("handler-thread");
            mHandlerThread.start();
        }

        if (workHandler == null) {
            //loop handler
            workHandler = new Handler(mHandlerThread.getLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if (msg.what == MSG_CPU) {
                        Log.e(TAG, "handleMessage: MSG_CPU");
                        workHandler.sendEmptyMessageDelayed(MSG_CPU, NORMAL_SAMPLING_TIME);
                    }
                }
            };
        }
        // 将数据放到队列中
        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // a. 定义要发送的消息
                Message msg = Message.obtain();
                msg.what = MSG_CPU; //消息的标识
                msg.obj = "B"; // 消息的存放
                // b. 通过Handler发送消息到其绑定的消息队列
                workHandler.sendMessage(msg);
            }
        });

        // 结束队列中的数据
        findViewById(R.id.btn_quit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandlerThread.quitSafely();
            }
        });

    }
}
