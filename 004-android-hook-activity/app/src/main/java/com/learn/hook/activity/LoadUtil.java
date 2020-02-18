package com.learn.hook.activity;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

import dalvik.system.DexClassLoader;

public class LoadUtil {

    private static final String pluginApkPath = Environment.getExternalStorageDirectory() + "/download/plugin.apk";

    public static void loadClass(Context context) {
        try {
            // 获取DexPathList类
            Class<?> dexPathListClazz = Class.forName("dalvik.system.DexPathList");
            // 获取dexElements变量
            Field dexElementsField = dexPathListClazz.getDeclaredField("dexElements");// Element[]
            // 将private设置为可访问
            dexElementsField.setAccessible(true);


            Class<?> baseDexClassLoaderClazz = Class.forName("dalvik.system.BaseDexClassLoader");
            Field pathListField = baseDexClassLoaderClazz.getDeclaredField("pathList"); // DexPathList
            pathListField.setAccessible(true);

            // 获取pathClassLoader
            ClassLoader pathClassLoader = context.getClassLoader();
            // 获取宿主dex
            Object hostPathList = pathListField.get(pathClassLoader);
            // 获取宿主dex
            Object[] hostDexElement = (Object[]) dexElementsField.get(hostPathList);
            File file = new File(pluginApkPath);
            if (!file.exists()) {
                Log.e("pluginApkPath: ", pluginApkPath);
                return;
            }

            // 插件类加载
            ClassLoader pluginClassLoader = new DexClassLoader(pluginApkPath, context.getCacheDir().getAbsolutePath(), null, pathClassLoader);
            // 获取宿主dex
            Object pluginPathList = pathListField.get(pluginClassLoader);
            // 获取宿主dex
            Object[] pluginDexElement = (Object[]) dexElementsField.get(pluginPathList);

            // 创建数组，入参是数组类型和长度
            Object[] newDexElements = (Object[]) Array.newInstance(pluginDexElement.getClass().getComponentType(), hostDexElement.length + pluginDexElement.length);
            System.arraycopy(hostDexElement, 0, newDexElements, 0, hostDexElement.length);
            System.arraycopy(pluginDexElement, 0, newDexElements, hostDexElement.length, pluginDexElement.length);
            // 重新赋值
            dexElementsField.set(hostPathList, newDexElements);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
