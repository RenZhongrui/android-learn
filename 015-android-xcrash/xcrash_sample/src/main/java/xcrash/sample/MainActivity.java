package xcrash.sample;

import android.Manifest;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import xcrash.XCrash;

public class MainActivity extends AppCompatActivity {

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
    }

    public void testNativeCrashInMainThread_onClick(View view) {
        XCrash.testNativeCrash(false);
    }

    public void testNativeCrashInAnotherJavaThread_onClick(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                XCrash.testNativeCrash(false);
            }
        }, "java_thread_with_a_very_long_name").start();
    }

    public void testNativeCrashInAnotherNativeThread_onClick(View view) {
        XCrash.testNativeCrash(true);
    }

    public void testNativeCrashInAnotherActivity_onClick(View view) {
        startActivity(new Intent(this, SecondActivity.class).putExtra("type", "native"));
    }

    public void testNativeCrashInAnotherProcess_onClick(View view) {
        startService(new Intent(this, MyService.class).putExtra("type", "native"));
    }

    public void testJavaCrashInMainThread_onClick(View view) {
        XCrash.testJavaCrash(false);
    }

    public void testJavaCrashInAnotherThread_onClick(View view) {
        XCrash.testJavaCrash(true);
    }

    public void testJavaCrashInAnotherActivity_onClick(View view) {
        startActivity(new Intent(this, SecondActivity.class).putExtra("type", "java"));
    }

    public void testJavaCrashInAnotherProcess_onClick(View view) {
        startService(new Intent(this, MyService.class).putExtra("type", "java"));
    }
    public void testAnrInput_onClick(View view) {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (Exception ignored) {
            }
        }
    }
}
