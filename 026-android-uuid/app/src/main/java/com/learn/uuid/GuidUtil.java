package com.learn.uuid;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.UUID;

/**
 * create: Ren Zhongrui
 * date: 2021-01-21
 * description: guid生成工具
 */
public class GuidUtil {

    private static final String GUID_KEY = "guid";

    private static final String uuidFileName = ".agree-guid";
    private static final String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + uuidFileName;

    public static String createGUID(Context context) throws Exception {
        // 读取顺序，sp file
        String guid = getFromSP(context);
        if (guid != null) {
            return guid;
        }
        // 如果系统数据库没有，说明权限不够没有写入，则从文件中读取
        guid = getFromFile();
        if (guid != null) {
            // 如果能够从文件中获取，表示应用卸载了，需要重新给SP赋值
            setToSP(context, guid);
            return guid;
        }
        // 前面三个都没有数据，表示第一次安装，需要生成一个UUID
        guid = UUID.randomUUID().toString().replace("-", "");
        // 将UUID保存到SP、外部存储目录
        setToSP(context, guid);
        setToFile(guid);
        return guid;
    }

    /**
     * 从SharedPreferences中读取UUID
     */
    private static String getFromSP(Context context) throws Exception {
        String guid = SPUtil.getString(context, GUID_KEY, "");
        if (!TextUtils.isEmpty(guid)) {
            // sp中有值，更新一下系统数据库和外置存储文件
            updateFile(guid);
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
     * 检测外部存储目录是否有该UUID，没有则更新，没有表示文件被用户清除了
     */
    private static void updateFile(String guid) throws Exception {
        File file = new File(filePath);
        // 如果文件不存在，则表示被清理了，
        if (!file.exists()) {
            file.createNewFile();
            writeData(guid);
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
        writeData(guid);
    }

    /**
     * 将UUID写入文件
     */
    private static void writeData(String guid) throws Exception {
        FileOutputStream fos = new FileOutputStream(filePath);
        fos.write(guid.getBytes());
        fos.close();
    }

}
