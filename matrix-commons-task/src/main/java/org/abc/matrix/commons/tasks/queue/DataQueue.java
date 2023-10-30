package org.abc.matrix.commons.tasks.queue;


import java.util.List;

/**
 * 数据生产端
 * <p/>
 * Created by wanjia on 16/9/29.
 */
public interface DataQueue {

    /**
     * 设置任务队列的大小,默认size:20000  max size:65535
     *
     * @param size
     */
    void setQueueSize(int size);

    /**
     * 设置核心处理线程的size
     *
     * @param threadSize
     */
    void setCoreThreadSize(int threadSize);

    /**
     * 往任务队列中提交任务
     *
     * @param task
     * @throws Exception
     */
    boolean submitTask(Task task) throws Exception;


    /**
     * 设置任务处理器
     *
     * @param taskProcessors
     */
    void setTaskProcessors(List<TaskProcessor> taskProcessors);
}
