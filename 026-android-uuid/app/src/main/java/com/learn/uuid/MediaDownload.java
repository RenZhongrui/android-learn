package com.learn.uuid;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import androidx.annotation.RequiresApi;

/**
 * create: Ren Zhongrui
 * date: 2021-01-26
 * description:
 */
public class MediaDownload {

    private static final String TAG = MediaDownload.class.getSimpleName();
    private static final String TEMP_DIR = "guid";
    private static final String RELATIVE_PATH = Environment.DIRECTORY_DOWNLOADS + File.separator + TEMP_DIR;
    private static final String GUID_FILE_NAME_EXT = "download-guid";
    private static final String GUID_MIME_TYPE = "application/octet-stream";

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static Uri queryUri(Context context) {
        Uri external = MediaStore.Downloads.EXTERNAL_CONTENT_URI;
        ContentResolver resolver = context.getContentResolver();
        // 查询所有数据
        Cursor query = resolver.query(external, null, null, null, null);
        // 遍历获取，查询所有文件
        if (query != null) {
            while (query.moveToNext()) {
                long id = query.getLong(query.getColumnIndexOrThrow(MediaStore.MediaColumns._ID));
                String title = query.getString(query.getColumnIndexOrThrow(MediaStore.MediaColumns.TITLE));
                String relativePath = query.getString(query.getColumnIndexOrThrow(MediaStore.Images.Media.RELATIVE_PATH));
                Log.e(TAG, "查询成功，id路径：" + id);
                Log.e(TAG, "查询成功，title路径：" + title);
                Log.e(TAG, "查询成功，relativePath路径：" + relativePath);
                Uri uri = ContentUris.withAppendedId(external, id);
                return uri;
            }
        }
        return null;
    }

    /**
     * 创建文件
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static void insertFile(Context context, String guid) {
        Uri external = MediaStore.Downloads.EXTERNAL_CONTENT_URI;
        ContentResolver contentResolver = context.getContentResolver();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Downloads.MIME_TYPE, GUID_MIME_TYPE);
        contentValues.put(MediaStore.Downloads.DISPLAY_NAME,GUID_FILE_NAME_EXT);
        contentValues.put(MediaStore.Downloads.RELATIVE_PATH,RELATIVE_PATH);
        Uri uri = queryUri(context);
        // 表示有值，直接返回
        if (uri != null) {
            // 如果文件存在，需要删除，会查到别的应用创建的文件，无权限删除会报错
            //contentResolver.delete(uri, null, null);
            return;
        }
        // 没值直接创建文件
        Uri insert = contentResolver.insert(external, contentValues);
        if (insert != null) {
            OutputStream outputStream = null;
            try {
                // 读取数据
                outputStream = contentResolver.openOutputStream(insert);
                if (outputStream == null) {
                    return;
                }
                outputStream.write(guid.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 删除文件
     */
    public static void deleteFile() {

    }
}
