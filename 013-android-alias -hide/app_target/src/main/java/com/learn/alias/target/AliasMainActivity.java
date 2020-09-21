package com.learn.alias.target;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.learn.alias.R;

import androidx.appcompat.app.AppCompatActivity;

public class AliasMainActivity extends AppCompatActivity {
    private Button toActivity, hideActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 先禁用主组件，启用alias组件
        AppIconUtil.set(AliasMainActivity.this, "com.learn.alias.target.AliasMainActivity", "com.learn.alias.target.MainActivity");
        Window window = getWindow();
        // 设置窗口位置在左上角
        window.setGravity(Gravity.LEFT | Gravity.TOP);
        WindowManager.LayoutParams params = window.getAttributes();
        params.x = 0;
        params.y = 0;
        params.width = 1;
        params.height = 1;
        window.setAttributes(params);
        finish();
    }
}
