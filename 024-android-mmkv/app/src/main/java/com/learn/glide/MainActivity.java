package com.learn.glide;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tencent.mmkv.MMKV;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Button btn_write, btn_read;
    private TextView tv_result, tv_dir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String rootDir = MMKV.initialize(this);
        Log.e(TAG, "rootDir: " + rootDir);
        tv_dir = findViewById(R.id.tv_dir);
        tv_dir.setText(rootDir);

        btn_write = findViewById(R.id.btn_write);
        btn_write.setOnClickListener(this);
        btn_read = findViewById(R.id.btn_read);
        btn_read.setOnClickListener(this);
        tv_result = findViewById(R.id.tv_result);
    }

    @Override
    public void onClick(View v) {
        MMKV mmkv = MMKV.defaultMMKV();
        switch (v.getId()) {
            case R.id.btn_write:
                mmkv.encode("hello", "hello mmkv");
                break;
            case R.id.btn_read:
                String hello = mmkv.decodeString("hello");
                tv_result.setText(hello);
                break;
        }
    }
}
