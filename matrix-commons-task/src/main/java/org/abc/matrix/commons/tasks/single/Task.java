package org.abc.matrix.commons.tasks.single;

/**
 * 定义异步任务
 * <p>
 * Created by chenhui on 15/8/19.
 */
public abstract class Task {

    /**
     * 一个任务两次处理的间隔，单位是毫秒
     */
    private long taskInterval;

    /**
     * 任务上次被处理的时间，用毫秒表示
     */
    private long lastProcessTime;

    /**
     * 设置重试次数,默认是不重试的,如果设置了重试次数,在结果返回为false的时候,任务会被扔到队列重新执行
     */
    private int retry = 0;

    public void setTaskInterval(long interval) {
        this.taskInterval = interval;
    }

    public long getTaskInterval() {
        return this.taskInterval;
    }

    public void setLastProcessTime(long lastProcessTime) {
        this.lastProcessTime = lastProcessTime;
    }

    public long getLastProcessTime() {
        return this.lastProcessTime;
    }

    public int getRetry() {
        return retry;
    }

    public void setRetry(int retry) {
        this.retry = retry;
    }

    /**
     * TaskManager 判断当前是否需要处理这个Task，子类可以Override这个函数实现自己的逻辑
     *
     * @return
     */
    public boolean shouldProcess() {
        return (System.currentTimeMillis() - this.lastProcessTime >= this.taskInterval);
    }


    public abstract void merge(Task curTask);
}
