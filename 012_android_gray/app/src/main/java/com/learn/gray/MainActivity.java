package com.learn.gray;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.VideoView;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebView webview = findViewById(R.id.webview);
        webview.loadUrl("https://blog.csdn.net/joshua1830/article/details/51726290?utm_source=blogxgwz5");

        VideoView videoView = findViewById(R.id.video);
        //将路径转换成uri
        Uri uri = Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4");
        //为视频播放器设置视频路径
        videoView.setVideoURI(uri);
        //开始播放视频
        videoView.start();

        // 弹框
        Button dialog = findViewById(R.id.dialog);
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    private void showDialog() {
        DialogUtil.showSingleDialog(this, "提示", "测试dialog", false, "确定", null);
    }

}
