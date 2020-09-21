package com.learn.alias.target;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.learn.alias.R;

import androidx.appcompat.app.AppCompatActivity;

public class AliasMainActivity extends AppCompatActivity {
    private Button toActivity, hideActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 禁用主组件，启用alias组件
        toActivity = findViewById(R.id.toActivity);
        toActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                ComponentName componentName = new ComponentName("com.gzrc.ammt.sitym", "cn.com.agree.abc.sdk.app.AliasMainActivity");
                intent.setComponent(componentName);
                startActivity(intent);
            }
        });
        hideActivity = findViewById(R.id.hideActivity);
        hideActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 禁用主组件，启用alias组件
                AppIconUtil.set(AliasMainActivity.this, "com.learn.alias.target.MainActivity", "com.learn.alias.Alias1Activity");
            }
        });
    }
}
