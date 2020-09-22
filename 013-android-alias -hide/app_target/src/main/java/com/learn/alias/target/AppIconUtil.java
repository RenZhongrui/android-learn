package com.learn.alias.target;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;

/**
 * create: Ren Zhongrui
 * date: 2020-09-18
 * description: 隐藏图标工具类
 */
public class AppIconUtil {

    /**
     *
     * @param context
     * @param main com.learn.alias.MainActivity
     * @param alias com.learn.alias.AliasActivity
     */
    public static void set(Context context, String main, String alias) {
        disableComponent(context, main);
        enableComponent(context, alias);
    }

    /**
     *  //此方法用以启用和禁用组件，会覆盖Androidmanifest文件下定义的属性
     * 启动组件
     */
    public static void enableComponent(Context context, String clazzName) {
        ComponentName componentName =  new ComponentName(context, clazzName);
        PackageManager mPackageManager = context.getPackageManager();
        mPackageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }

    /**
     * 禁用组件
     */
    public static void disableComponent(Context context, String clazzName) {
        ComponentName componentName =  new ComponentName(context, clazzName);
        PackageManager mPackageManager = context.getPackageManager();
        mPackageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

}
