package com.hello.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池添加task时的执行过程： 1. 如果池中线程数小于核心线程数，直接创建一个线程入池并执行此task 2.
 * 如果池中线程数大于或等于核心线程数，则判断队列中task数量：
 * <1>如果队列中task数量未达到队列容量，则task直接进队列（不创建新线程，因为线程是很重的资源，能不新建就不新建，例外情况是
 * 如果核心线程数是0，则新建一个线程） <2>如果队列中task数量达到队列最大容量，且池中线程数量未达到最大线程数，则创建一个线程入池，并用此线程执行
 * 此task（注意，是直接执行本次要添加的task，而不是从队列里拿task）
 * <3>如果队列中task数量达到队列最大容量，且池中线程数量达到最大线程数，则根据指定的RejectPolicy来决定：
 * CallerRunsPolicy: 用提交task的线程来执行此task，不扔RejectedExecutionException
 * AbortPolicy: 直接丢弃此task，扔RejectedExecutionException;
 * 此策略为未指定RejectedExecutionHandler时的默认策略 DiscardPolicy:
 * 直接丢弃此task，不扔RejectedExecutionException DiscardOldestPolicy:
 * 丢弃队列中最老的task，并将此task放入队尾，不扔RejectedExecutionException 线程池构造函数： int
 * corePoolSize, 核心线程数 int maximumPoolSize, 最大线程数 long keepAliveTime,
 * 线程闲置时保持存活的时间数量 TimeUnit unit, 线程闲置时保持存活的时间单位 BlockingQueue
 * <Runnable> workQueue, 线程任务队列 ThreadFactory threadFactory, 线程池用于创建线程的工厂类
 * RejectedExecutionHandler handler 线程池满且队列满时的任务提交策略
 *
 * @author jiangwei
 */

public class ThreadPools {
    private static final AtomicInteger unPooledThreadNumber = new AtomicInteger(1);
    private ExecutorService smsPool;
    private ExecutorService pushPool;
    private Thread listenMqMsgThread;
    private ExecutorService tcpLogicPool;
    private ExecutorService bikeServiceLogicPool;
    private ExecutorService bikeServiceRemoveAlertPool;
    private ScheduledExecutorService tcpNotifyPool;
    private ScheduledExecutorService checkLocationPool;
    private ExecutorService bikePosRpLogicPool;
    private ExecutorService bikeLogicDbPool;
    private ExecutorService logPool;
    private ScheduledExecutorService checkVerisonPool;
    private ExecutorService bikeManageInfoPool;
    private Thread redisListenThread;
    private ExecutorService bosBikePositionMsgPool;
    private ExecutorService bikeSmsOverPool;
    private ExecutorService bosBikePositionPool;
    private ScheduledExecutorService gpsSendPool;
    private ExecutorService sessionPool;
    private ExecutorService faultAutoHandlePool;
    private ExecutorService bosElectricCloseLockPool;
    private ExecutorService compenPool;
    private ExecutorService addBikeMaintainPool;
    private ExecutorService faultCloseLockMsgPool;
    private ExecutorService bosUpdateBikePositionPool;
    private ExecutorService submitBatteryUnlockPool;
    private ThreadPools() {
        smsPool = new ThreadPoolExecutor(2, 16, 15, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(1024),
                new NamedThreadFactory("SMS"));
        pushPool = new ThreadPoolExecutor(4, 64, 15, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(1024),
                new NamedThreadFactory("PUSH"));
        tcpLogicPool = new ThreadPoolExecutor(64, 300, 15, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(2048),
                new NamedThreadFactory("TCP_LOGIC"));
        sessionPool = new ThreadPoolExecutor(32, 256, 15, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(1024),
                new NamedThreadFactory("TCP_LOGIC"));
        tcpNotifyPool = Executors.newScheduledThreadPool(4, new NamedThreadFactory("TCP-NOTIFY"));
        checkLocationPool = Executors.newScheduledThreadPool(8, new NamedThreadFactory("CHECK-LOCATION"));
        bikeServiceLogicPool =
                new ThreadPoolExecutor(4, 16, 15, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(1024),
                        new NamedThreadFactory("BIKE_SERVICE_LOGIC"), new CallerRunsPolicy());
        bikeServiceRemoveAlertPool = new ThreadPoolExecutor(4, 16, 15, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(1024),
                new NamedThreadFactory("BIKE_SERVICE_REMOVE_ALERT_TYPE"), new CallerRunsPolicy());
        bikePosRpLogicPool = new ThreadPoolExecutor(8, 128, 15, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(1024),
                new NamedThreadFactory("BIKE_POS_RP_LOGIC"));
        bikeLogicDbPool = new ThreadPoolExecutor(4, 16, 5, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(1024),
                new NamedThreadFactory("BIKE_SIM_LOGIC"));
        logPool = new ThreadPoolExecutor(1, 8, 15, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(1024), new NamedThreadFactory("LOGGER"));
        checkVerisonPool = Executors.newScheduledThreadPool(1, new NamedThreadFactory("check_oss_conf_version"));
        bikeManageInfoPool = new ThreadPoolExecutor(4, 16, 15, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(1024),
                new NamedThreadFactory("BIKE_SERVICE_ADD_BIKE_MANAGE_INFO"), new CallerRunsPolicy());
        bosBikePositionPool = new ThreadPoolExecutor(4, 16, 15, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(1024),
                new NamedThreadFactory("BOS_BIKE_POSITION_POOL"), new CallerRunsPolicy());
        bosBikePositionMsgPool = new ThreadPoolExecutor(1, 2, 15, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(16),
                new NamedThreadFactory("BOS_BIKE_POSITION_MSG_POOL"));
        bikeSmsOverPool = new ThreadPoolExecutor(1, 2, 15, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(16),
                new NamedThreadFactory("BIKE_SMS_OVER_POOL"));
        gpsSendPool = Executors.newScheduledThreadPool(1, new NamedThreadFactory("check_oss_conf_version"));
        faultAutoHandlePool = new ThreadPoolExecutor(2, 4, 15, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(1024), new NamedThreadFactory("FAULT_AUTO_HANDLE"));
        bosElectricCloseLockPool = new ThreadPoolExecutor(2, 16, 15, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(1024), new NamedThreadFactory("BOS_ELECTRIC_BIKE_CLOSE_BIKE"), new CallerRunsPolicy());
        compenPool = new ThreadPoolExecutor(2, 16, 15, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(1024), new NamedThreadFactory("BOS_ELECTRIC_BIKE_CLOSE_BIKE"), new CallerRunsPolicy());
        addBikeMaintainPool = new ThreadPoolExecutor(2, 16, 15, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(1024), new NamedThreadFactory("BOS_ELECTRIC_BIKE_CLOSE_BIKE"), new CallerRunsPolicy());
        faultCloseLockMsgPool = new ThreadPoolExecutor(1, 2, 15, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(1024), new NamedThreadFactory("BIKE_FAULT_SERVICE"));
        bosUpdateBikePositionPool = new ThreadPoolExecutor(4, 16, 15, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(1024),
                new NamedThreadFactory("BOS_UPDATE_BIKE_POSITION_POOL"), new CallerRunsPolicy());
        submitBatteryUnlockPool = new ThreadPoolExecutor(2, 16, 15, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(1024), new NamedThreadFactory("SUBMIT_BATTERY_UNLOCK"), new CallerRunsPolicy());
    }

    public static ThreadPools getInstance() {
        return ThreadPoolManagerHolder.instance;
    }

    /**
     * 强依赖执行task，如果task提交失败，则以本线程执行此task
     *
     * @param task
     */
    public void executeSMSTask(Runnable task) {
        try {
            smsPool.submit(task);
        } catch (RejectedExecutionException e) {
            task.run();
        }
    }

    /**
     * 强依赖执行task，如果task提交失败，则以本线程执行此task
     *
     * @param task
     */
    public void executeListenMqMsg(Runnable task) {
        synchronized (this) {
            if (listenMqMsgThread == null) {
                listenMqMsgThread = new Thread(task,
                        "Thread-" + unPooledThreadNumber.getAndIncrement() + "-" + "LISTEN_MQ_MSG");
                listenMqMsgThread.start();
            } else {
                throw new java.lang.IllegalThreadStateException(
                        "The thread " + listenMqMsgThread.getName() + " has task and started.");
            }
        }
    }

    public void scheduleTcpNotifyTask(Runnable task, long delay) {
        tcpNotifyPool.schedule(task, delay, TimeUnit.MILLISECONDS);
    }

    public void scheduleCheckLocationTask(Runnable task, long delay) {
        checkLocationPool.schedule(task, delay, TimeUnit.MILLISECONDS);
    }

    /**
     * 强依赖执行task，如果task提交失败，则以本线程执行此task
     *
     * @param task
     */
    public void executePushTask(Runnable task) {
        try {
            pushPool.submit(task);
        } catch (RejectedExecutionException e) {
            task.run();
        }
    }

    /**
     * 弱依赖提交task，如果task提交失败，则在本线程中扔出异常，由本线程相机处理
     *
     * @param task
     */
    public void submitPushTask(Runnable task) throws RejectedExecutionException {
        pushPool.submit(task);
    }

    public void scheduleCheckTask(Runnable task, long initialDelay, long delay) {
        checkVerisonPool.scheduleAtFixedRate(task, initialDelay, delay, TimeUnit.MINUTES);
    }

    public void scheduleGpsSendTask(Runnable task, long initialDelay, long delay) {
        gpsSendPool.scheduleAtFixedRate(task, initialDelay, delay, TimeUnit.MINUTES);
    }


    public void shutdown() {
        smsPool.shutdownNow();
        pushPool.shutdownNow();
        tcpLogicPool.shutdownNow();
        listenMqMsgThread.interrupt();
        tcpNotifyPool.shutdownNow();
        checkLocationPool.shutdown();
        bikeServiceLogicPool.shutdownNow();
        bikePosRpLogicPool.shutdownNow();
        bikeLogicDbPool.shutdownNow();
        checkVerisonPool.shutdownNow();
        bikeManageInfoPool.shutdownNow();
        bosBikePositionPool.shutdownNow();
        bosBikePositionMsgPool.shutdownNow();
        gpsSendPool.shutdown();
        bosElectricCloseLockPool.shutdownNow();
        faultAutoHandlePool.shutdownNow();
        compenPool.shutdownNow();
        addBikeMaintainPool.shutdownNow();
        faultCloseLockMsgPool.shutdown();
        bosUpdateBikePositionPool.shutdownNow();
        submitBatteryUnlockPool.shutdownNow();
    }

    /**
     * 弱依赖提交task，如果task提交失败，则在本线程中扔出异常，由本线程相机处理
     *
     * @param task
     */
    public void submitTcpLogicTask(Runnable task) throws RejectedExecutionException {
        tcpLogicPool.execute(task);
    }

    public void submitSessionTask(Runnable task) throws RejectedExecutionException {
        sessionPool.execute(task);
    }

    public void submitBikeServiceRemoveAlertTypeTask(Runnable task) throws RejectedExecutionException {
        bikeServiceRemoveAlertPool.submit(task);
    }

    public void submitBikeServiceLogicTask(Runnable task) throws RejectedExecutionException {
        bikeServiceLogicPool.submit(task);
    }

    public void submitBikeDbTask(Runnable task) throws RejectedExecutionException {
        bikeLogicDbPool.submit(task);
    }

    public void submitBikePosLogicTask(Runnable task) throws RejectedExecutionException {
        bikePosRpLogicPool.submit(task);
    }

    public void submitLoggerTask(Runnable task) throws RejectedExecutionException {
        logPool.submit(task);
    }

    public void submitBikeManageInfoTask(Runnable task) throws RejectedExecutionException {
        bikeManageInfoPool.submit(task);
    }

    public void executeBosBikePositionMqTask(Runnable task) {
        try {
            bosBikePositionMsgPool.submit(task);
        } catch (RejectedExecutionException e) {
        }
    }

    public void executeBikeSmsOverListener(Runnable task) {
        try {
            bikeSmsOverPool.submit(task);
        } catch (RejectedExecutionException e) {
        }
    }

    public void submitBosBikePositionTask(Runnable task) throws RejectedExecutionException {
        try {
            bosBikePositionPool.submit(task);
        } catch (RejectedExecutionException e) {
        }
    }

    public void executeFaultCloseLockMqTask(Runnable task) {
        try {
            faultCloseLockMsgPool.submit(task);
        } catch (RejectedExecutionException e) {
        }
    }

    public void submitFaultCloseLockTask(Runnable task) throws RejectedExecutionException {
        try {
            faultCloseLockMsgPool.submit(task);
        } catch (RejectedExecutionException e) {
        }
    }

    public void faultAutoHandleTask(Runnable task) throws RejectedExecutionException {
        try {
            faultAutoHandlePool.submit(task);
        } catch (RejectedExecutionException e) {
        }
    }

    public void submitBosElectricCloseLock(Runnable task) throws RejectedExecutionException {
        try {
            bosElectricCloseLockPool.submit(task);
        } catch (RejectedExecutionException e) {
        }
    }

    public void compenBikeInfoTask(Runnable task) throws RejectedExecutionException {
        try {
            compenPool.submit(task);
        } catch (RejectedExecutionException e) {
        }
    }

    public void addBikeMaintainTask(Runnable task) throws RejectedExecutionException {
        try {
            addBikeMaintainPool.submit(task);
        } catch (RejectedExecutionException e) {
        }
    }

    public void submitBosUpdateBikePositionPool(Runnable task) throws RejectedExecutionException {
        try {
            bosUpdateBikePositionPool.submit(task);
        } catch (RejectedExecutionException e) {
        }
    }

    public void handleBatteryUnlockStateTask(Runnable task) throws RejectedExecutionException {
        try {
        	submitBatteryUnlockPool.submit(task);
        } catch (RejectedExecutionException e) {
        }
    }
    
    /**
     * 弱依赖提交task，如果task提交失败，则在本线程中扔出异常，由本线程相机处理
     *
     * @param task
     */
    public void submitSMSTask(Runnable task) throws RejectedExecutionException {
        smsPool.submit(task);
    }

    public void executeRedisSubscribeTask(Runnable task) {
        synchronized (this) {
            if (redisListenThread == null) {
                redisListenThread = new Thread(task, "Thread-" + unPooledThreadNumber.getAndIncrement() + "-" + "REDIS_LISTENER");
                redisListenThread.start();
            } else {
                throw new java.lang.IllegalThreadStateException("The thread " + redisListenThread.getName() + " has task and started.");
            }
        }
    }

    private static class ThreadPoolManagerHolder {
        public static ThreadPools instance = new ThreadPools();
    }

    private static class NamedThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        public NamedThreadFactory(String name) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = "pool-" + poolNumber.getAndIncrement() + "-" + name + "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }

    }
}
