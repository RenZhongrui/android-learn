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
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.UUID;

import androidx.annotation.RequiresApi;

/**
 * create: Ren Zhongrui
 * date: 2021-01-21
 * description: guid生成工具
 * <p>
 */
public class GuidUtil {

    private static final String TAG = GuidUtil.class.getSimpleName();
    private static final String GUID_KEY = "guid";
    private static final String TEMP_DIR = "agree";
    private static final String RELATIVE_PATH = Environment.DIRECTORY_PICTURES + File.separator + TEMP_DIR;
    private static final String GUID_FILE_NAME_EXT = "agree-guid.jpg";
    private static final String GUID_FILE_NAME = "agree-guid";
    // 10.0以下创建隐藏目录
    private static final String GUID_FILE_NAME_HIDE = ".agree-guid";
    //private static final String GUID_MIME_TYPE = "application/octet-stream";

    private static final String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + GUID_FILE_NAME_HIDE;

    public static String createGUID(Context context) throws Exception {
        // 读取顺序，sp file
        String guid = getFromSP(context);
        // guid = null;
        if (guid != null) {
            return guid;
        }
        // 如果SP中没有，但是文件中有，说明应用卸载了
        guid = getFromFile(context);
        if (guid != null) {
            // 如果能够从文件中获取，表示应用卸载了，需要重新给SP赋值
            setToSP(context, guid);
            return guid;
        }
        // 前面三个都没有数据，表示第一次安装，需要生成一个UUID
        guid = UUID.randomUUID().toString().replace("-", "");
        // 将UUID保存到SP、外部存储目录
        setToSP(context, guid);
        setToFile(context, guid);
        return guid;
    }

    /**
     * 存储到文件
     */
    private static void setToFile(Context context, String guid) throws Exception {
        // android 10.0以上，存储到媒体数据库
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            setToMediaStore(context, guid);
        } else {
            // 存储到外部存储目录
            setToFile(guid);
        }
    }

    /**
     * 从文件中获取
     */
    private static String getFromFile(Context context) throws Exception {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            return getFromMediaFile(context);
        } else {
            return getFromFile();
        }
    }

    /**
     * 查询Uri
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static Uri queryUri(Context context) {
        Uri external = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver resolver = context.getContentResolver();
        // 查询所有数据
        Cursor query = resolver.query(external, null, null, null, null);
        // 遍历获取，查询所有文件
        if (query != null) {
            while (query.moveToNext()) {
                long id = query.getLong(query.getColumnIndexOrThrow(MediaStore.MediaColumns._ID));
                String title = query.getString(query.getColumnIndexOrThrow(MediaStore.MediaColumns.TITLE));
                String relativePath = query.getString(query.getColumnIndexOrThrow(MediaStore.Images.Media.RELATIVE_PATH));
                // 相对路径和文件名一致时，才确定有数据
                if (title.equals(GUID_FILE_NAME) && (RELATIVE_PATH + File.separator).equals(relativePath)) {
                    Log.e(TAG, "查询成功，title路径：" + title);
                    Log.e(TAG, "查询成功，relativePath路径：" + relativePath);
                    Uri uri = ContentUris.withAppendedId(external, id);
                    query.close();
                    Log.e(TAG, "查询成功，Uri路径：" + uri.toString());
                    return uri;
                }
            }
        }
        return null;
    }

    /**
     * android 10.0以上，从媒体数据库获取
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static String getFromMediaFile(Context context) {
        String guid = null;
        // 如果存在则获取值，否则返回null
        Uri uri = queryUri(context);
        if (uri == null) {
            return null;
        }
        ContentResolver contentResolver = context.getContentResolver();
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        try {
            inputStream = contentResolver.openInputStream(uri);
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                guid = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return guid;
    }


    /**
     * android 10.0以上，写入到媒体数据库
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static void setToMediaStore(Context context, String guid) {
        Uri external = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver contentResolver = context.getContentResolver();

        ContentValues contentValues = new ContentValues();
        //contentValues.put(MediaStore.Images.Media.MIME_TYPE, GUID_MIME_TYPE);
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, GUID_FILE_NAME_EXT);
        contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, RELATIVE_PATH);
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
     * 从SharedPreferences中读取UUID
     */
    private static String getFromSP(Context context) throws Exception {
        String guid = SPUtil.getString(context, GUID_KEY, "");
        if (!TextUtils.isEmpty(guid)) {
            // sp中有值，更新一下媒体数据库和外置存储文件
            updateFile(context, guid);
            return guid;
        }
        return null;
    }


    /**
     * 从文件读取UUID
     */
    private static String getFromFile() throws Exception {
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        // 读取一行即可
        return bufferedReader.readLine();
    }

    /**
     * 更新文件
     */
    private static void updateFile(Context context, String guid) throws Exception {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            setToMediaStore(context, guid);
        } else {
            updateFile(guid);
        }
    }

    /**
     * 检测外部存储目录是否有该UUID，没有则更新，没有表示文件被用户清除了
     */
    private static void updateFile(String guid) throws Exception {
        File file = new File(filePath);
        // 如果文件不存在，则表示被清理了，
        if (!file.exists()) {
            file.createNewFile();
            writeData(filePath, guid);
        } else {
            // 如果文件存在，则需要对比一下sp中和文件中的是否一致
            String fileUUID = getFromFile();
            // 如果不相等，则表示被篡改了，需要更新
            if (!guid.equals(fileUUID)) {
                setToFile(guid);
            }
        }
    }

    /**
     * 保存到sp
     */
    private static void setToSP(Context context, String guid) {
        SPUtil.putString(context, GUID_KEY, guid);
    }

    /**
     * 保存到文件
     */
    private static void setToFile(String guid) throws Exception {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        writeData(filePath, guid);
    }

    /**
     * 将UUID写入文件
     */
    private static void writeData(String filePath, String guid) throws Exception {
        FileOutputStream fos = new FileOutputStream(filePath);
        fos.write(guid.getBytes());
        fos.close();
    }

}
