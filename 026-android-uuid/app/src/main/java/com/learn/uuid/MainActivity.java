package com.learn.uuid;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Button btn_write, btn_read;
    private TextView tv_result, tv_dir;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_dir = findViewById(R.id.tv_dir);
        btn_write = findViewById(R.id.btn_write);
        btn_write.setOnClickListener(this);
        btn_read = findViewById(R.id.btn_read);
        btn_read.setOnClickListener(this);
        tv_result = findViewById(R.id.tv_result);
        PermissionX.init(this)
                .permissions(Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS,
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .request(new RequestCallback() {
                    @Override
                    public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                        if (allGranted) {
                            String guid = null;
                            try {
                                guid = GuidUtil.createGUID(MainActivity.this);
                                //GuidUtil.setToMediaStore(MainActivity.this, "654321");
                                //String guid = GuidUtil.getFromMediaFile(MainActivity.this);
                              /*  UuidUtil.setToDB(MainActivity.this, "1234567890");
                                uuid = UuidUtil.getFromSystemDB(MainActivity.this);*/

                       /*         UuidUtil.setToDB(MainActivity.this, "1234567890");
                                uuid = UuidUtil.getFromSystemDB(MainActivity.this);
                                UuidUtil.updateDB(MainActivity.this, "11111111111");
                                uuid = UuidUtil.getFromSystemDB(MainActivity.this);*/
                                tv_result.setText(guid);
                                Log.e(TAG, "onCreate: " + guid);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_write:
                break;
            case R.id.btn_read:
                break;
        }
    }


}
