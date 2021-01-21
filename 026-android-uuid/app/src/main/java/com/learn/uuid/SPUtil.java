package com.learn.uuid;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences:共享偏好，用来做数据存储，通过xml，存放标记性数据和设置信息
 */
public class SPUtil {
    private static SharedPreferences sharedPreferences;

    /**
     * 写入Boolean变量至sharedPreferences中
     *
     * @param context 上下文环境
     * @param key     存储节点名称
     * @param value   存储节点的值
     */
    public static void putBoolean(Context context, String key, Boolean value) {
        //(存储节点文件名称，读写方式)
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putBoolean(key, value).commit();
    }

    /**
     * 读取boolean标识从sharedPreferences中
     *
     * @param context 上下文环境
     * @param key     存储节点名称
     * @param value   没有此节点的默认值
     * @return 默认值或者此节点读取到的结果
     */
    public static boolean getBoolean(Context context, String key, Boolean value) {
        //(存储节点文件名称,读写方式)
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sharedPreferences.getBoolean(key, value);
    }

    /**
     * 写入String变量至sharedPreferences中
     *
     * @param context 上下文环境
     * @param key     存储节点名称
     * @param value   存储节点的值String
     */
    public static void putString(Context context, String key, String value) {
        //存储节点文件的名称，读写方式
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putString(key, value).commit();
    }


    /**
     * 读取String标识从sharedPreferences中
     *
     * @param context  上下文环境
     * @param key      存储节点名称
     * @param defValue 没有此节点的默认值
     * @return 返回默认值或者此节点读取到的结果
     */
    public static String getString(Context context, String key, String defValue) {
        //存储节点文件的名称，读写方式
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sharedPreferences.getString(key, defValue);
    }

    /**
     * 写入int变量至sharedPreferences中
     *
     * @param context 上下文环境
     * @param key     存储节点名称
     * @param value   存储节点的值String
     */
    public static void putInt(Context context, String key, int value) {
        //存储节点文件的名称，读写方式
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putInt(key, value).commit();
    }


    /**
     * 读取int标识从sharedPreferences中
     *
     * @param context  上下文环境
     * @param key      存储节点名称
     * @param defValue 没有此节点的默认值
     * @return 返回默认值或者此节点读取到的结果
     */
    public static int getInt(Context context, String key, int defValue) {
        //存储节点文件的名称，读写方式
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sharedPreferences.getInt(key, defValue);
    }

    /**
     * 从sharedPreferences中移除指定节点
     *
     * @param context 上下文环境
     * @param key     需要移除节点的名称
     */
    public static void remove(Context context, String key) {
        //存储节点文件的名称，读写方式
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().remove(key).commit();
    }

}