package com.lib.pullalive;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * 一个轻量的后台job service,利用空闲时间执行一些小事情，提高进程不被回收的概率
 */
@TargetApi(value = Build.VERSION_CODES.LOLLIPOP)
public class AliveJobService extends JobService {
    private static final String TAG = AliveJobService.class.getName();
    // 任务调度JobScheduler，该工具集成了常见的几种运行条件，开发者只需添加少数几行代码，即可完成原来要多种组件配合的工作
    private JobScheduler mJobScheduler;

    private Handler mJobHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            Log.d(TAG, "pull alive.");
            jobFinished((JobParameters) msg.obj, false);
            return true;
        }
    });

    public static void start(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Intent intent = new Intent(context, AliveJobService.class);
            context.startService(intent);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mJobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        JobInfo job = initJobInfo(startId);
        if (mJobScheduler.schedule(job) <= 0) {
            Log.d(TAG, "AliveJobService failed");
        } else {
            Log.d(TAG, "AliveJobService success");
        }
        return START_STICKY;
    }

    //执行条件被触发后，会到开始任务回调中，onStartJob
    @Override
    public boolean onStartJob(JobParameters params) {
        mJobHandler.sendMessage(Message.obtain(mJobHandler, 1, params));
        return true;
    }

    //结束任务
    @Override
    public boolean onStopJob(JobParameters params) {
        mJobHandler.sendEmptyMessage(1);
        return false;
    }

    /**
     任务信息的运行条件由JobInfo.Builder来构造，下面是Builder的函数说明：
     构造函数：指定该任务来源与目的，与Intent类似，第二个参数指定了开发者自定义的JobService。
     setRequiredNetworkType：设置需要的网络条件，有三个取值：JobInfo.NETWORK_TYPE_NONE（无网络时执行，默认）、JobInfo.NETWORK_TYPE_ANY（有网络时执行）、JobInfo.NETWORK_TYPE_UNMETERED（网络无需付费时执行）
     setPersisted：重启后是否还要继续执行，此时需要声明权限RECEIVE_BOOT_COMPLETED，否则会报错“java.lang.IllegalArgumentException: Error: requested job be persisted without holding RECEIVE_BOOT_COMPLETED permission.”而且RECEIVE_BOOT_COMPLETED需要在安装的时候就要声明，如果一开始没声明，而在升级时才声明，那么依然会报权限不足的错误。
     setRequiresCharging：是否在充电时执行
     setRequiresDeviceIdle：是否在空闲时执行
     setPeriodic：设置时间间隔，单位毫秒。该方法不能和setMinimumLatency、setOverrideDeadline这两个同时调用，否则会报错“java.lang.IllegalArgumentException: Can't call setMinimumLatency() on a periodic job”，或者报错“java.lang.IllegalArgumentException: Can't call setOverrideDeadline() on a periodic job”。
     setMinimumLatency：设置至少延迟多久后执行，单位毫秒。
     setOverrideDeadline：设置最多延迟多久后执行，单位毫秒。
     build：完成条件设置，返回构建好的JobInfo对象。
     */
    private JobInfo initJobInfo(int startId) {
        JobInfo.Builder builder = new JobInfo.Builder(startId,
                new ComponentName(getPackageName(), AliveJobService.class.getName()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setMinimumLatency(JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS); //执行的最小延迟时间
            builder.setOverrideDeadline(JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS);  //执行的最长延时时间
            builder.setBackoffCriteria(JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS,
                    JobInfo.BACKOFF_POLICY_LINEAR);//线性重试方案
        } else {
            builder.setPeriodic(JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS);
        }
        builder.setPersisted(false); // 设置应用销毁后继续执行此job
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE);
        builder.setRequiresCharging(false); //
        return builder.build();
    }
}
