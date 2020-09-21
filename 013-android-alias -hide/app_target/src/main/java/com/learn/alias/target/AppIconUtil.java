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
     * @param main com.learn.alias.target.MainActivity
     * @param alias com.learn.alias.target.AliasActivity
     */
    public static void set(Context context, String main, String alias) {
        ComponentName indexActivityNormal = new ComponentName(context, main);
        ComponentName indexActivity11 = new ComponentName(context, alias);
        disableComponent(context, indexActivityNormal);
        enableComponent(context, indexActivity11);
    }

    /**
     * 启动组件
     *
     * @param componentName 组件名
     */
    public static void enableComponent(Context context, ComponentName componentName) {
        //此方法用以启用和禁用组件，会覆盖Androidmanifest文件下定义的属性
        PackageManager mPackageManager = context.getPackageManager();
        mPackageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }

    /**
     * 禁用组件
     *
     * @param componentName 组件名
     */
    public static void disableComponent(Context context, ComponentName componentName) {
        PackageManager mPackageManager = context.getPackageManager();
        mPackageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

}
