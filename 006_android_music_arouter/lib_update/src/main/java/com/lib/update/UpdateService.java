package com.lib.update;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;


import java.io.File;

import static com.lib.update.UpdateHelper.UPDATE_FILE_KEY;


public class UpdateService extends Service {

  public static final String CHANNEL_ID = "channel_id_update";
  public static final String CHANNEL_NAME = "channel_name_update";
  /**
   * 服务器固定地址
   */
  private static final String APK_URL_TITLE = "http://www.imooc.com/mobile/mukewang.apk";
  /**
   * 文件存放路经
   */
  private String filePath;
  /**
   * 文件下载地址
   */
  private String apkUrl;

  private NotificationManager notificationManager;
  private Notification mNotification;
  NotificationCompat.Builder notification;

  public static void startService(Context context) {
    Intent intent = new Intent(context, UpdateService.class);
    context.startService(intent);
  }

  @Override public void onCreate() {
    filePath = Environment.getExternalStorageDirectory() + "/imooc/imooc.apk";

    notificationManager =
        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    //适配安卓8.0的消息渠道
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      NotificationChannel channel =
          new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
      channel.enableLights(false);
      channel.enableVibration(false);
      notificationManager.createNotificationChannel(channel);
    }
    notification = new NotificationCompat.Builder(this, CHANNEL_ID);
    notification.setSmallIcon(R.drawable.icon_imooc);
    notification.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon_imooc));
    notification.setContentTitle(getString(R.string.app_name));
    notification.setAutoCancel(true);
    notification.setWhen(System.currentTimeMillis());
  }

  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    apkUrl = APK_URL_TITLE;
    notifyUser(getString(R.string.update_download_start), getString(R.string.update_download_start),
        0);
    startDownload();
    return super.onStartCommand(intent, flags, startId);
  }

  private void startDownload() {
    UpdateManager.getInstance().startDownload(apkUrl, filePath, new UpdateDownloadListener() {
      @Override public void onStarted() {
      }

      @Override public void onProgressChanged(int progress, String downloadUrl) {
        notifyUser(getString(R.string.update_download_processing),
            getString(R.string.update_download_processing), progress);
      }

      @Override public void onPrepared(long contentLength, String downloadUrl) {
      }

      @Override public void onPaused(int progress, int completeSize, String downloadUrl) {
        notifyUser(getString(R.string.update_download_failed),
            getString(R.string.update_download_failed_msg), 0);
        deleteApkFile();
        stopSelf();// 停掉服务自身
      }

      @Override public void onFinished(int completeSize, String downloadUrl) {
        notifyUser(getString(R.string.update_download_finish),
            getString(R.string.update_download_finish), 100);
        stopSelf();// 停掉服务自身
      }

      @Override public void onFailure() {
        notifyUser(getString(R.string.update_download_failed),
            getString(R.string.update_download_failed_msg), 0);
        deleteApkFile();
        stopSelf();// 停掉服务自身
      }
    });
  }

  @Override public IBinder onBind(Intent intent) {
    return null;
  }

  public void notifyUser(String tickerMsg, String message, int progress) {
    notification.setTicker(tickerMsg);
    if (progress >= 0 && progress < 100) {
      notification.setProgress(100, progress, false);
    } else {
      /**
       * 0,0,false,可以将进度条影藏,下载完成
       */
      notification.setProgress(0, 0, false);
      notification.setContentText(message);
      sendInstallBroadcast();
    }
    mNotification = notification.build();
    notificationManager.notify(0, mNotification);
  }

  private void sendInstallBroadcast() {
    Intent intent = new Intent(UpdateHelper.UPDATE_ACTION);
    intent.putExtra(UPDATE_FILE_KEY, filePath);
    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
  }

  /**
   * 删除无用apk文件
   */
  private boolean deleteApkFile() {
    File apkFile = new File(filePath);
    if (apkFile.exists() && apkFile.isFile()) {
      return apkFile.delete();
    }
    return false;
  }
}