package com.learn.bsdiff;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import java.io.File;
import java.security.MessageDigest;

import static android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION;

public class AppUtil {
    public static final int REQ_CODE_INSTALL_APP = 99;

    /**
     * 获取当前应用信息
     *
     * @param context
     * @return
     */
    public static PackageInfo getPackageInfo(Context context) {
        return getPackageInfo(context,context.getPackageName());
    }

    /**
     * 获取目标包名应用的信息
     */
    public static PackageInfo getPackageInfo(Context context,String pkgName){
        try {
            return context.getPackageManager().getPackageInfo(pkgName,0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前应用版本名
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        if (packageInfo != null) {
            return packageInfo.versionName;
        }
        return "";
    }

    /**
     * 获取当前应用版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        if (packageInfo != null) {
            return packageInfo.versionCode;
        }
        return 0;
    }

    /**
     * 系统安装方法
     *
     * @param apkPath
     * @param context
     */
    public static void install(Activity context, String apkPath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(FileUtil.getUriForFile(context, apkPath), "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(intent);
    }

    /**
     * 安装应用
     *
     * @param activity
     * @param appFile
     * @return
     */
    public static boolean install(Activity activity, File appFile) {
        try {
            Intent intent = getInstallAppIntent(activity, appFile);
            //activity.startActivityForResult(intent, REQ_CODE_INSTALL_APP);
            if (activity.getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
                activity.startActivityForResult(intent, REQ_CODE_INSTALL_APP);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Intent getInstallAppIntent(Context context, File appFile) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //区别于 FLAG_GRANT_READ_URI_PERMISSION 跟 FLAG_GRANT_WRITE_URI_PERMISSION， URI权限会持久存在即使重启，直到明确的用 revokeUriPermission(Uri, int) 撤销。 这个flag只提供可能持久授权。但是接收的应用必须调用ContentResolver的takePersistableUriPermission(Uri, int)方法实现
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            }
            intent.setDataAndType(FileUtil.getUriForFile(context, appFile), "application/vnd.android.package-archive");
            return intent;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static boolean isAppOnForeground(Context context) {
        if (context != null) {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
            String currentPackageName = cn.getPackageName();
            if (!TextUtils.isEmpty(currentPackageName) && currentPackageName.equals(context.getPackageName())) {
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * 获取应用名
     *
     * @param context
     * @return
     */
    public static String getAppName(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        if (packageInfo != null) {
            return packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
        }
        return "";
    }

    /**
     * 获取应用图标
     *
     * @param context
     * @return
     */
    public static Drawable getAppIcon(Context context) {
        try {
            return context.getPackageManager().getApplicationIcon(context.getPackageName());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取apk文件中的版本信息
     *
     * @param context
     * @param apkPath
     * @return
     */
    public static int getVersionFromApk(Context context, String apkPath) {
        PackageManager pm = context.getPackageManager();
        PackageInfo packInfo = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
        int version = packInfo.versionCode;
        return version;
    }

    /**
     * 获取当前app的签名信息
     *
     * @param context
     * @return
     */
    public static String getSign(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            Signature[] signs = packageInfo.signatures;
            Signature sign = signs[0];
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(sign.toByteArray());
            char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
            byte[] bs = md.digest();
            int j = bs.length;
            char[] buf = new char[j * 3 - 1];
            int k = 0;
            for (int i = 0; i < j; ++i) {
                byte byte0 = bs[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 15];
                buf[k++] = hexDigits[byte0 & 15];
                if (i != j - 1) {
                    buf[k++] = 58;
                }
            }
            return new String(buf);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param context
     * @param type
     * @param fileName
     */
    public static void open(Activity context, String type, String fileName) {
        type = type.toLowerCase();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION | FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        File file = new File(fileName);
        Uri uri = FileUtil.getUriForFile(context, file);
        switch (type) {
            case "word":
                intent.setDataAndType(uri, "application/msword");
                break;
            case "excel":
                intent.setDataAndType(uri, "application/vnd.ms-excel");
                break;
            case "ppt":
                intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
                break;
            case "txt":
                intent.setDataAndType(uri, "text/plain");
                break;
            case "pdf":
                intent.setDataAndType(uri, "application/pdf");
                break;
            case "image":
                intent.setDataAndType(uri, "image/*");
                break;
        }
        context.startActivity(intent);
    }

    /**
     * 判断当前应用是否为调试模式
     * @param context
     * @return
     */
    public static boolean isDebugMode(Context context) {
        try {
            ApplicationInfo info= context.getApplicationInfo();
            return (info.flags&ApplicationInfo.FLAG_DEBUGGABLE)!=0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
