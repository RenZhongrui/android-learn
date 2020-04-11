package com.learn.gray;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public class DialogUtil {

    public static void showSingleDialog(Context context, String title, String msg, boolean cancelable, String positive,
                                        DialogInterface.OnClickListener listener) {
        createSingleDialog(context, title, msg, cancelable, positive, listener).show();
    }

    public static void showDoubleDialog(Context context, String title, String msg, String positive,
                                        String negative, DialogInterface.OnClickListener listener) {

        createDoubleDialog(context, title, msg, positive, negative, listener).show();
    }

    public static AlertDialog createSingleDialog(Context context, String title, String msg, boolean cancelable, String positive,
                                                 DialogInterface.OnClickListener listener) {

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(positive, listener)
                .setCancelable(cancelable)
                .create();
        return dialog;
    }

    public static AlertDialog createDoubleDialog(Context context, String title, String msg, String positive,
                                                 String negative, DialogInterface.OnClickListener listener) {

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(positive, listener)
                .setNegativeButton(negative, listener)
                .create();
        return dialog;
    }

    public static ProgressDialog createProgressDialog(Context context, String title, int style, boolean cancelable) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(style);// 设置水平进度条
        progressDialog.setCancelable(cancelable);// 设置是否可以通过点击Back键取消
        progressDialog.setCanceledOnTouchOutside(cancelable);// 设置在点击Dialog外是否取消Dialog进度条
        progressDialog.setTitle(title);
        return progressDialog;
    }
}

