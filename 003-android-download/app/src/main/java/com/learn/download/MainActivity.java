package com.learn.download;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.db.DownloadManager;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.download.DownloadTask;
import com.lzy.okserver.task.XExecutor;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG = "MainActivity";
    private OkDownload okDownload;
    private Button btn_start, btn_pause, btn_delete;
    private TextView folder;
    private List<DownloadTask> values;
    public static final int TYPE_ALL = 0;
    public static final int TYPE_FINISH = 1;
    public static final int TYPE_ING = 2;
    private static final int REQUEST_PERMISSION_STORAGE = 0x01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_start = findViewById(R.id.btn_start);
        btn_pause = findViewById(R.id.btn_pause);
        btn_delete = findViewById(R.id.btn_delete);
        folder = findViewById(R.id.folder);
        checkSDCardPermission();

        btn_start.setOnClickListener(this);
        //okDownload.addOnAllTaskEndListener(onAllTaskEndListener);

    }

    private XExecutor.OnAllTaskEndListener onAllTaskEndListener = new XExecutor.OnAllTaskEndListener() {
        @Override
        public void onAllTaskEnd() {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (okDownload != null) {
            okDownload.removeOnAllTaskEndListener(onAllTaskEndListener);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                List<Progress> downloading = DownloadManager.getInstance().getDownloading();
                Log.e(TAG, "btn_start: " + downloading.toString());

                ApkModel apkModel = new ApkModel();
                apkModel.name = "全民K歌";
                apkModel.iconUrl = "http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/0f1f653261ff8b3a64324097224e40eface432b99";
                apkModel.url = "http://60.28.123.129/f4.market.xiaomi.com/download/AppStore/04f515e21146022934085454a1121e11ae34396ae/com.tencent.karaoke.apk";

                //okDownload = OkDownload.getInstance();
                OkDownload.getInstance().setFolder(Environment.getExternalStorageDirectory().getAbsolutePath() + "/download/");
                OkDownload.getInstance().getThreadPool().setCorePoolSize(3);

                folder.setText(String.format("下载路径: %s", OkDownload.getInstance().getFolder()));
                //这里只是演示，表示请求可以传参，怎么传都行，和okgo使用方法一样
                GetRequest<File> request = OkGo.<File>get(apkModel.url)//
                        .headers("aaa", "111")//
                        .params("bbb", "222");
                List<DownloadTask> tasks = OkDownload.restore(DownloadManager.getInstance().getDownloading());

                Log.e(TAG, "tasks: " + tasks);
                //这里第一个参数是tag，代表下载任务的唯一标识，传任意字符串都行，需要保证唯一,我这里用url作为了tag
                OkDownload.request(apkModel.url, request)//
                        .priority(apkModel.priority)// 优先级
                        .extra1(apkModel)// 设置model
                        .save()
                        .register(new LogDownloadListener())//
                        .start();
                break;
            case R.id.btn_pause:


                break;
            case R.id.btn_delete:
                break;
        }
    }

    /**
     * 检查SD卡权限
     */
    protected void checkSDCardPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //获取权限
            } else {
                Toast.makeText(this, "权限被禁止，无法下载文件！", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
