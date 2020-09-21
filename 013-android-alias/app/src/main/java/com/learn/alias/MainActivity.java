package com.learn.alias;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button toActivity, hideActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toActivity = findViewById(R.id.toActivity);
        toActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                ComponentName componentName = new ComponentName("com.learn.alias.target", "com.learn.alias.MainActivity");
                intent.setComponent(componentName);
                startActivity(intent);
            }
        });
        hideActivity = findViewById(R.id.hideActivity);
        hideActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 禁用主组件，启用alias组件
                AppIconUtil.set(MainActivity.this, "com.learn.alias.MainActivity", "com.learn.alias.Alias1Activity");
            }
        });
    }
}
