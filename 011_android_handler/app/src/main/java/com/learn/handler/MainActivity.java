package com.learn.handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tv_message;
    private Button btn_thread,btn_quit;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            MainActivity.this.click();
        }
    };

    private void click() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_message = findViewById(R.id.tv_message);
        btn_thread = findViewById(R.id.btn_thread);
        btn_quit = findViewById(R.id.btn_quit);
        testReturn();
        useHandler();
        testThreadHandler();
        btn_thread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 主线程往子线程发送消息
                threadHandler.sendMessage(threadHandler.obtainMessage());
            }
        });
        btn_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                threadHandler.getLooper().quitSafely();
            }
        });
    }

    // Handler的使用
    private void useHandler() {
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

    Handler threadHandler;
    private void testThreadHandler() {
        new Thread(new Runnable() {
            @SuppressLint("HandlerLeak")
            @Override
            public void run() {
                Looper.prepare();
                threadHandler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        // 会收到消息
                        Log.e( "run: ", "接收到消息");
                    }
                };
                // 会打印
                Log.e( "run: ", "loop"+Thread.currentThread().getId());
                Looper.loop();

                // 不会打印，因为loop一直处于阻塞状态，所以不会向下执行，整个子线程都卡到这里
                Log.e( "run: ", "循环外Log不打印，需要释放Message");
            }
        }).start();
    }

    private void testReturn() {
        return;
    }

}
