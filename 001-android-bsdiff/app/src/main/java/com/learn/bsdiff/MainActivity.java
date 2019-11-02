package com.learn.bsdiff;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private TextView tv_version;
    private Button btn_update;
    private ImageView image;
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String permission[] = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (checkSelfPermission(permission[0]) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(permission,200);
            }
        }
        tv_version = findViewById(R.id.tv_version);
        tv_version.setText(BuildConfig.VERSION_NAME);
        btn_update = findViewById(R.id.btn_update);
        image =findViewById(R.id.image);
        image.setImageResource(R.drawable.g);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });
    }

    public native String stringFromJNI();

    public native void bsPatch(String oldApk, String patch, String output);

    // 更新应用
    @SuppressLint("StaticFieldLeak")
    public void update() {
        new AsyncTask<Void, Void, File>() {
            @Override
            protected File doInBackground(Void... voids) {
                // 获取当前安装包路径 /data/app/com.learn.bsdiff-vkZp27c33gznzKVqB5pztg==/base.apk
                // String path = getApplicationContext().getPackageResourcePath(); 或
                String oldApk = getApplicationInfo().sourceDir;
                Log.e("oldApk", "doInBackground: " + oldApk );
                // 下载获取差分包路径
                String patch = new File(Environment.getExternalStorageDirectory() + File.separator + "/download/patch").getAbsolutePath();
                // 创建新版本的安装包
                String output = createNewApk().getAbsolutePath();
                // 调用native方法
                bsPatch(oldApk, patch, output);
                return new File(output);
            }

            @Override
            protected void onPostExecute(File file) {
                super.onPostExecute(file);
                // 安装应用
                AppUtil.install(MainActivity.this,file.getAbsolutePath());
            }
        }.execute();
    }

    // 合成新的apk
    private File createNewApk() {
        File newApk = new File(Environment.getExternalStorageDirectory() + File.separator + "/download/bspatch.apk");
        if (!newApk.exists()) {
            try {
                newApk.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  newApk;
    }
}
