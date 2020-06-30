package com.aopcloud.palmproject.utils;

import android.os.Handler;
import android.os.Looper;

import com.aopcloud.palmproject.common.AppContext;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @PackageName : com.aopcloud.basic.utils
 * @File : ThreadUtil.java
 * @Date : 2020/4/10 2020/4/10
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class ThreadUtil {

    /**
     * 在主线程中运行的线程
     */
    public static void runOnUiThread(Runnable action) {
        AppContext.mHandler.post(action);
    }

    public static void runOnChildThread(Runnable action) {
        new Thread(action).start();
    }

    /**
     *  @SuppressWarnings("AlibabaThreadPoolCreation")
     *  private static Executor executor_d = Executors.newSingleThreadExecutor();
     **/

    /**
     * int corePoolSize 线程池核心池的大小。
     * int maximumPoolSize, - 线程池的最大线程数。
     * long keepAliveTime, - 当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间。
     * TimeUnit unit, - keepAliveTime 的时间单位
     * BlockingQueue<Runnable> workQueue, - 用来储存等待执行任务的队列。
     * ThreadFactory threadFactory, - 线程工厂。
     * RejectedExecutionHandler handler)  - 拒绝策略。
     */
    private static Executor executor = new ThreadPoolExecutor(
            1,
            1,
            1,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(1));

    private static Executor executors = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());
    private static Handler handler = new Handler(Looper.getMainLooper());

    public static void executeSubThread(Runnable runnable) {
        executor.execute(runnable);
    }

    public static void executeMainThread(Runnable runnable) {
        handler.post(runnable);
    }


}

