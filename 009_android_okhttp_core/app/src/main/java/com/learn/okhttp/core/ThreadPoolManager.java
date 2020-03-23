package com.learn.okhttp.core;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * create: Ren Zhongrui
 * date: 2020-03-23
 * description: 线程池管理类
 * 原理：
 * 1、将请求放到队列中
 * 2、创建线程池和使用线程池执行叫号线程和延迟线程
 * 3、创建获取队列的线程，叫号线程，在此线程中获取队列中的请求任务，然后由线程池来执行队列中的每一个异步请求任务
 * 4、重试机制：由延迟队列和延迟线程来维护，线程池来执行，执行失败则添加到延迟队列中重新排队
 *
 */
public class ThreadPoolManager {

    // 1、创建请求队列，用来保存异步请求任务（每一个网络请求就是一个异步线程任务），先进先出
    // LinkedBlockingDeque 维护的是一个双向链表。
    private LinkedBlockingDeque<Runnable> mQueue = new LinkedBlockingDeque<>();

    private static ThreadPoolManager threadPoolManager;

    private ThreadPoolExecutor threadPoolExecutor;

    private ThreadPoolManager() {
        // 3、创建线程池对象
        threadPoolExecutor = new ThreadPoolExecutor(3, 10, 15, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(4), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
                // 处理线程池中报错任务，出错了再添加进队列中重新排序处理
                addTask(runnable);
            }
        });
        ///5、再初始化的时候也要初始化执行交互线程
        threadPoolExecutor.execute(communicateThread);
        // 执行延迟线程
        threadPoolExecutor.execute(delayRunnable);
    }

    public static ThreadPoolManager getInstance() {
        if (threadPoolManager == null) {
            synchronized (ThreadPoolManager.class) {
                if (threadPoolManager == null) {
                    threadPoolManager = new ThreadPoolManager();
                }
            }
        }
        return threadPoolManager;
    }

    // 2、添加异步任务到队列中
    public void addTask(Runnable runnable) {
        if (runnable != null) {
            try {
                mQueue.put(runnable); // 入队
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // 4、创建队列与线程池的交互线程，即叫号线程
    public Runnable communicateThread = new Runnable() {
        @Override
        public void run() {
            //在交互线程中获取队列中的每一个HttpTask，然后由线程池进行执行
            Runnable runn = null;
            while (true) {
                try {
                    runn = mQueue.take(); // 出队
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                threadPoolExecutor.execute(runn);
            }
        }
    };

    // 重试机制，创建延迟队列
    private DelayQueue<HttpTask> delayQueue = new DelayQueue<>();

    // 添加延迟任务
    public void addDelayTask(HttpTask ht) {
        if (ht != null) {
            ht.setDelayTime(3000);
            delayQueue.put(ht); // 入队
        }
    }

    // 创建延迟异步任务
    private Runnable delayRunnable = new Runnable() {
        @Override
        public void run() {
            HttpTask ht = null;
            while (true) {
                try {
                    ht = delayQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (ht.getRetryCount() < 3) {
                    threadPoolExecutor.execute(ht);
                    ht.setRetryCount(ht.getRetryCount() + 1);
                    Log.e("重试机制：: ", ht.getRetryCount() + "");
                } else {
                    Log.e("重试机制超限：: ", "");
                }
            }
        }
    };

}
