package com.learn.download;

import android.os.Bundle;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.db.DownloadManager;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.download.DownloadListener;
import com.lzy.okserver.download.DownloadTask;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BaseDownloadActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "MainActivity";
    private Button btn_start, btn_pause, btn_continue, btn_delete;
    private TextView folder;
    private List<DownloadTask> values;
    public static final int type = 0;
    public static final int TYPE_ALL = 0;
    public static final int TYPE_FINISH = 1;
    public static final int TYPE_ING = 2;
    private NumberProgressBar pbProgress;
    private List<FileModel> fileModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_download);
        initData();
        initView();
        initListener();
    }

    // 初始化数据
    private void initData() {
        fileModels = new ArrayList<>();
        FileModel fileModel = new FileModel();
        fileModel.name = "全民K歌";
        fileModel.iconUrl = "http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/0f1f653261ff8b3a64324097224e40eface432b99";
        fileModel.url = "http://60.28.123.129/f4.market.xiaomi.com/download/AppStore/04f515e21146022934085454a1121e11ae34396ae/com.tencent.karaoke.apk";
        fileModels.add(fileModel);
    }

    // 初始化视图
    private void initView() {
        btn_start = findViewById(R.id.btn_start);
        btn_pause = findViewById(R.id.btn_pause);
        btn_continue = findViewById(R.id.btn_continue);
        btn_delete = findViewById(R.id.btn_delete);
        pbProgress = findViewById(R.id.pbProgress);
        folder = findViewById(R.id.folder);
    }

    // 初始化监听
    private void initListener() {
        btn_start.setOnClickListener(this);
        btn_pause.setOnClickListener(this);
        btn_continue.setOnClickListener(this);
        //okDownload.addOnAllTaskEndListener(onAllTaskEndListener);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_start:
                // 设置下载参数
                OkDownload.getInstance().setFolder(Environment.getExternalStorageDirectory().getAbsolutePath() + "/download/");
                OkDownload.getInstance().getThreadPool().setCorePoolSize(3);

                FileModel fileModel = fileModels.get(0);
                folder.setText(String.format("下载路径: %s", OkDownload.getInstance().getFolder()));
                //这里只是演示，表示请求可以传参，怎么传都行，和okgo使用方法一样
                // 1、先创建一个request
                GetRequest<File> request = OkGo.<File>get(fileModel.url)//
                        .headers("aaa", "111")//
                        .params("bbb", "222");
                //这里第一个参数是tag，代表下载任务的唯一标识，传任意字符串都行，需要保证唯一,我这里用url作为了tag
                // 2、发起下载请求，传入tag，request
                OkDownload.request(fileModel.url, request)//
                        .priority(fileModel.priority)// 优先级
                        .extra1(fileModel)// 设置model
                        .save()
                        .register(new BaseDownloadListener(fileModel.url))
                        .start();
                //从数据库中恢复数据
      /*          List<Progress> progressList = DownloadManager.getInstance().getAll();
                OkDownload.restore(progressList);*/
                break;
            case R.id.btn_pause: // 暂停下载
                List<DownloadTask> tasks = OkDownload.restore(DownloadManager.getInstance().getDownloading());
                DownloadTask task = tasks.get(0);
                Progress progress = task.progress;
                int status = progress.status;
                // 如果当前是下载状态，则暂停下载
                if (Progress.LOADING == status) {
                    task.pause();
                }
                break;
            case R.id.btn_continue: // 继续下载
                // 如果当前是下载状态，则暂停下载
            /*    if (Progress.PAUSE == status || Progress.NONE == status || Progress.ERROR == status) {
                    task.start();
                }*/
                break;
            case R.id.btn_delete:
                //task.remove();
                break;
        }
    }

    private class BaseDownloadListener extends DownloadListener {

        BaseDownloadListener(Object tag) {
            super(tag);
        }

        @Override
        public void onStart(Progress progress) {
        }

        @Override
        public void onProgress(Progress progress) {
            float fraction = progress.fraction;
            pbProgress.setMax(10000);
            pbProgress.setProgress((int) (fraction * 10000));
        }

        @Override
        public void onError(Progress progress) {
            Throwable throwable = progress.exception;
            if (throwable != null) throwable.printStackTrace();
        }

        @Override
        public void onFinish(File file, Progress progress) {

        }

        @Override
        public void onRemove(Progress progress) {
        }
    }
}
