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
                ComponentName componentName = new ComponentName("com.learn.alias.target", "com.learn.alias.target.MainActivity");
                //ComponentName componentName = new ComponentName("com.gzrc.ammt.sitym", "cn.com.agree.abc.sdk.app.AliasMainActivity");
                intent.setComponent(componentName);
                startActivity(intent);
            }
        });
    }
}
