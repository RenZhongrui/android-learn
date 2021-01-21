package com.learn.uuid;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

/**
 * create: Ren Zhongrui
 * date: 2021-01-21
 * description: uuid生成工具
 */
public class UuidUtil {

    private static final String UUID_KEY = "uuid";

    private static final String uuidFileName = "agree-uuid";
    private static final String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + uuidFileName;

    public static String createUUID(Context context) throws Exception {
        // 读取顺序，sp db file
        String uuid = getFromSP(context);
        if (uuid != null) {
            return uuid;
        }
        // 如果系统数据库没有，说明权限不够没有写入，则从文件中读取
        uuid = getFromFile();
        if (uuid != null) {
            // 如果能够从文件中获取，表示应用卸载了，需要重新给SP赋值
            setToSP(context, uuid);
            return uuid;
        }
        // 前面三个都没有数据，表示第一次安装，需要生成一个UUID
        uuid = UUID.randomUUID().toString().replace("-", "");
        // 将UUID保存到SP、外部存储目录
        setToSP(context, uuid);
        setToFile(uuid);
        return uuid;
    }

    /**
     * 从SharedPreferences中读取UUID
     */
    private static String getFromSP(Context context) throws Exception {
        String uuid = SPUtil.getString(context, UUID_KEY, "");
        if (!TextUtils.isEmpty(uuid)) {
            // sp中有值，更新一下系统数据库和外置存储文件
            updateFile(uuid);
            return uuid;
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
     * 检测外部存储目录是否有该UUID，没有则更新，没有表示文件被用户清除了
     */
    private static void updateFile(String uuid) throws Exception {
        File file = new File(filePath);
        // 如果文件不存在，则表示被清理了，
        if (!file.exists()) {
            file.createNewFile();
            writeData(uuid);
        } else {
            // 如果文件存在，则需要对比一下sp中和文件中的是否一致
            String fileUUID = getFromFile();
            // 如果不相等，则表示被篡改了，需要更新
            if (!uuid.equals(fileUUID)) {
                setToFile(uuid);
            }
        }
    }

    /**
     * 保存到sp
     */
    private static void setToSP(Context context, String uuid) {
        SPUtil.putString(context, UUID_KEY, uuid);
    }

    /**
     * 保存到文件
     */
    private static void setToFile(String uuid) throws Exception {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        writeData(uuid);
    }

    /**
     * 将UUID写入文件
     */
    private static void writeData(String uuid) throws Exception {
        FileOutputStream fos = new FileOutputStream(filePath);
        fos.write(uuid.getBytes());
        fos.close();
    }

}
