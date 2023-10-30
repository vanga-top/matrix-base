package org.abc.matrix.commons.tasks.queue.block;


import org.abc.matrix.commons.tasks.queue.DataQueue;
import org.abc.matrix.commons.tasks.queue.Task;
import org.abc.matrix.commons.tasks.queue.TaskProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * linked block queue
 * <p/>
 * Created by wanjia on 16/10/11.
 */
public class LinkedBlockDataQueue implements DataQueue, InitializingBean, DisposableBean {

    private int queueSize = 20000;//默认队列大小
    private int threadSize = 4;

    private static final Logger logger = LoggerFactory.getLogger(LinkedBlockDataQueue.class);

    private LinkedBlockingQueue<Task<?>> queue = null;
    private ExecutorService executorService = null;
    private List<TaskProcessor> taskProcessors;
    private volatile boolean breakLoop = false;

    @Override
    public void setQueueSize(int size) {
        size = size <= 0 ? queueSize : size;
        this.queueSize = size;
    }

    @Override
    public void setCoreThreadSize(int threadSize) {
        threadSize = threadSize > 30 ? 30 : threadSize;
        threadSize = threadSize <= 0 ? this.threadSize : threadSize;
        this.threadSize = threadSize;
    }

    @Override
    public boolean submitTask(Task task) throws Exception {
        return queue.offer(task, 100, TimeUnit.MILLISECONDS);
    }

    @Override
    public void setTaskProcessors(List<TaskProcessor> taskProcessors) {
        this.taskProcessors = taskProcessors;
    }

    /**
     * 开始执行
     */
    @SuppressWarnings("unchecked")
    private void start() {
        if (CollectionUtils.isEmpty(taskProcessors)) {
            logger.error("taskProcessors is null...", new IllegalArgumentException());
            return;
        }

        for (int i = 0; i < threadSize; i++) {
            executorService.submit(new MultiConsumer());
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        this.queue = new LinkedBlockingQueue<>(this.queueSize);
        this.executorService = Executors.newFixedThreadPool(threadSize);
        start();
    }

    @Override
    public void destroy() throws Exception {
        this.breakLoop = true;
    }

    /**
     * 消费线程
     */
    private class MultiConsumer implements Runnable {
        @Override
        public void run() {
            while (true) {
                Task task = null;//线程一直在wait
                try {
                    task = queue.take();
                } catch (InterruptedException e) {
                    continue;
                }
                for (TaskProcessor processor : taskProcessors) {
                    try {
                        processor.process(task);
                    } catch (Exception e) {
                        logger.error("error_in_process", e);
                    }
                }
                if (breakLoop) {
                    break;
                }
            }
        }
    }
}
