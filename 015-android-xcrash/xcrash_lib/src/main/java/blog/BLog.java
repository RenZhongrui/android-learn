package blog;

import android.content.Context;
import android.os.Looper;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import cn.com.agree.abc.sdk.log.clog.CLog;

/**
 * 崩溃日志收集处理
 */
public class BLog implements Thread.UncaughtExceptionHandler {

    private static BLog bLog;
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
    private Context context;

    private BLog(Context context) {
        this.context = context;
    }

    public static BLog getInstance(Context context) {
        if (bLog == null) {
            synchronized (BLog.class) {
                if (bLog == null) {
                    bLog = new BLog(context);
                }
            }
        }
        return bLog;
    }

    /**
     * 初始化信息
     */
    public void init() {
        uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        throwable.printStackTrace();
        if (!handleException(throwable)) {
            if (uncaughtExceptionHandler != null) {
                uncaughtExceptionHandler.uncaughtException(thread, throwable);
            }
        } else {
            try {
                Thread.sleep(1000);
                Process.killProcess(Process.myPid());
                System.exit(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理异常
     * 在主线程中弹出提示
     * @param throwable
     */
    private boolean handleException(final Throwable throwable) {
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(context, "应用程序异常,即将退出!", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
        String crashInfo = getCrashInfo(throwable);
        Log.e("crashInfo: ", crashInfo);
        CLog.write(crashInfo, BLogLevel.CRASH);
        CLog.flush();
        return true;
    }

    /**
     * 获取崩溃日志信息
     *
     * @param throwable
     * @return
     */
    private String getCrashInfo(Throwable throwable) {
        String lineSeparator = ";";
        StringBuilder sb = new StringBuilder();
        String exception = "Exception:" + throwable.toString();
        Writer info = new StringWriter();
        PrintWriter printWriter = new PrintWriter(info);
        throwable.printStackTrace(printWriter);
        String dump = info.toString();
        String crashDump = "Crash Dump:" + "{" + dump + "}";
        printWriter.close();
        sb.append(exception).append(lineSeparator);
        sb.append(crashDump).append(lineSeparator);
        return sb.toString();
    }
}
