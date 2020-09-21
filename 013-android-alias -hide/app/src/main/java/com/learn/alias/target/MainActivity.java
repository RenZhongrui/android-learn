package com.learn.alias.target;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.learn.alias.R;

public class MainActivity extends AppCompatActivity {
    private Button toActivity, hideActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 先禁用主组件，启用alias组件
        setContentView(R.layout.activity_main);
        AppIconUtil.set(MainActivity.this, "com.learn.alias.target.MainActivity", "com.learn.alias.target.Alias1Activity");
    }
}
