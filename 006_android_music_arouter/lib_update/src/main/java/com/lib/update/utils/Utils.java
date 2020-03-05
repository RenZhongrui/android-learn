package com.lib.update.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class Utils {

  public static int getVersionCode(Context context) {
    int versionCode = 1;
    try {
      PackageManager pm = context.getPackageManager();
      PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
      versionCode = pi.versionCode;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return versionCode;
  }
}


