package com.gustgamer29.scheduler;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

public class FutureScheduler implements SchedulerAdapter {

    private final ScheduledThreadPoolExecutor scheduler;
    private final ErrorReportingExecutor schedulerWorkerPool;
    private final ForkJoinPool worker;

    public FutureScheduler() {
        this.scheduler = new ScheduledThreadPoolExecutor(1, new ThreadFactoryBuilder()
                .setDaemon(true)
                .setNameFormat("quests-scheduler")
                .build()
        );

        this.scheduler.setRemoveOnCancelPolicy(true);

        this.schedulerWorkerPool = new ErrorReportingExecutor(Executors.newCachedThreadPool(new ThreadFactoryBuilder()
                .setDaemon(true)
                .setNameFormat("quests-futureWorker-%d")
                .build()
        ));

        this.worker = new ForkJoinPool(32, ForkJoinPool.defaultForkJoinWorkerThreadFactory, (t, e) -> e.printStackTrace(), false);
    }

    @Override
    public Executor async() {
        return this.worker;
    }

    @Override
    public Executor sync() {
        return null;
    }

    @Override
    public SchedulerInvalidator asyncLater(Runnable task, long delay, TimeUnit unit) {
        ScheduledFuture<?> future = this.scheduler.schedule(() -> this.schedulerWorkerPool.execute(task), delay, unit);
        return () -> future.cancel(false);
    }

    @Override
    public SchedulerInvalidator asyncRepeating(Runnable task, long interval, TimeUnit unit) {
        ScheduledFuture<?> future = this.scheduler.scheduleAtFixedRate(() -> this.schedulerWorkerPool.execute(task), interval, interval, unit);
        return () -> future.cancel(false);
    }

    @Override
    public void shutdownScheduler() {
        this.scheduler.shutdown();
        try {
            this.scheduler.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void shutdownExecutor() {
        this.schedulerWorkerPool.delegate.shutdown();
        try {
            this.schedulerWorkerPool.delegate.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static final class ErrorReportingExecutor implements Executor {
        private final ExecutorService delegate;

        private ErrorReportingExecutor(ExecutorService delegate) {
            this.delegate = delegate;
        }

        @Override
        public void execute(Runnable command) {
            this.delegate.execute(new ErrorReportingRunnable(command));
        }
    }

    private static final class ErrorReportingRunnable implements Runnable {
        private final Runnable delegate;

        private ErrorReportingRunnable(Runnable delegate) {
            this.delegate = delegate;
        }

        @Override
        public void run() {
            try {
                this.delegate.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
