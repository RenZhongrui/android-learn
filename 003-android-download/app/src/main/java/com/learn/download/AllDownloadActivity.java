package com.learn.download;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.download.DownloadListener;
import com.lzy.okserver.task.XExecutor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AllDownloadActivity extends AppCompatActivity implements View.OnClickListener, XExecutor.OnAllTaskEndListener{

    private Button btn_all_download, btn_all_pause, btn_all_continue, btn_all_remove;
    private List<FileModel> fileModels;
    private NumberProgressBar progress_bar1, progress_bar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_download);
        initData();
        initView();
        initListener();
    }

    private void initData() {
        fileModels = new ArrayList<>();
        FileModel fileModel1 = new FileModel();
        fileModel1.name = "全民K歌";
        fileModel1.iconUrl = "http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/0f1f653261ff8b3a64324097224e40eface432b99";
        fileModel1.url = "http://60.28.123.129/f4.market.xiaomi.com/download/AppStore/04f515e21146022934085454a1121e11ae34396ae/com.tencent.karaoke.apk";
        fileModels.add(fileModel1);
        FileModel fileModel2 = new FileModel();
        fileModel2.name = "新浪微博";
        fileModel2.iconUrl = "http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/01db44d7f809430661da4fff4d42e703007430f38";
        fileModel2.url = "http://60.28.125.129/f1.market.xiaomi.com/download/AppStore/0ff41344f280f40c83a1bbf7f14279fb6542ebd2a/com.sina.weibo.apk";
        fileModels.add(fileModel2);
    }

    private void initView() {
        btn_all_download = findViewById(R.id.btn_all_download);
        btn_all_pause = findViewById(R.id.btn_all_pause);
        btn_all_continue = findViewById(R.id.btn_all_continue);
        btn_all_remove = findViewById(R.id.btn_all_remove);
        progress_bar1 = findViewById(R.id.progress_bar1);
        progress_bar2 = findViewById(R.id.progress_bar2);
    }

    private void initListener() {
        btn_all_download.setOnClickListener(this);
        btn_all_pause.setOnClickListener(this);
        btn_all_continue.setOnClickListener(this);
        btn_all_remove.setOnClickListener(this);
        OkDownload.getInstance().addOnAllTaskEndListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_all_download:
                FileModel fileModel = fileModels.get(0);
                GetRequest<File> request = OkGo.<File>get(fileModel.url)//
                        .headers("aaa", "111")//
                        .params("bbb", "222");
                OkDownload.request(fileModel.url, request)//
                        .priority(fileModel.priority)// 优先级
                        .extra1(fileModel)// 设置model
                        .save()
                        .register(new AllDownloadListener0(fileModel.url))
                        .start();
                FileModel fileModel1 = fileModels.get(1);
                GetRequest<File> request1 = OkGo.<File>get(fileModel.url)//
                        .headers("aaa", "111")//
                        .params("bbb", "222");
                OkDownload.request(fileModel1.url, request1)//
                        .priority(fileModel1.priority)// 优先级
                        .extra1(fileModel1)// 设置model
                        .save()
                        .register(new AllDownloadListener1(fileModel.url))
                        .start();
                break;
            case R.id.btn_all_pause:
                OkDownload.getInstance().pauseAll();
                break;
            case R.id.btn_all_continue:
                OkDownload.getInstance().startAll();
                break;
            case R.id.btn_all_remove:
                OkDownload.getInstance().removeAll();
                break;
        }
    }

    @Override
    public void onAllTaskEnd() {

    }

    private class AllDownloadListener0 extends DownloadListener {

        AllDownloadListener0(Object tag) {
            super(tag);
        }

        @Override
        public void onStart(Progress progress) {
        }

        @Override
        public void onProgress(Progress progress) {
            float fraction = progress.fraction;
            progress_bar1.setMax(10000);
            progress_bar1.setProgress((int) (fraction * 10000));
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

    private class AllDownloadListener1 extends DownloadListener {

        AllDownloadListener1(Object tag) {
            super(tag);
        }

        @Override
        public void onStart(Progress progress) {
        }

        @Override
        public void onProgress(Progress progress) {
            float fraction = progress.fraction;
            progress_bar2.setMax(10000);
            progress_bar2.setProgress((int) (fraction * 10000));
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
