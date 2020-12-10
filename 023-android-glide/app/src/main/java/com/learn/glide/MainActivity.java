package com.learn.glide;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.ContentObserver;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView iv_glide = findViewById(R.id.iv_glide);
        Button btn_open = findViewById(R.id.btn_open);
        final boolean open = GpsUtil.isOpen(this);
        Log.e(TAG, "onCreate: " + open);
        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GpsUtil.openGPS(MainActivity.this);
            }
        });
        tv_result = findViewById(R.id.tv_result);
        tv_result.setText(open + "");
        Glide.with(this).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1607577387022&di=9cbaf5c98d0623cb1dcc1fc957807be3&imgtype=0&src=http%3A%2F%2Fpics7.baidu.com%2Ffeed%2Fd53f8794a4c27d1e4dfdcc92d41f0f68dfc438da.jpeg%3Ftoken%3Da99dd503d74f84fd6f1bdf83b47f83b3").into(iv_glide);

        final LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        getContentResolver().registerContentObserver(Settings.Secure.getUriFor(Settings.System.LOCATION_PROVIDERS_ALLOWED),
                false, new ContentObserver(null) {
                    @Override
                    public void onChange(boolean selfChange) {
                        super.onChange(selfChange);
                        boolean enabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                        Log.e(TAG, "gps enabled? " + enabled);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GpsUtil.GPS_REQUEST_CODE) {
            boolean result = GpsUtil.isOpen(this);
            // 再次判断是否打开了Gps
            tv_result.setText(result + "");
            if (!result) {
                GpsUtil.openGPS(MainActivity.this);
            }
        }
    }


}
