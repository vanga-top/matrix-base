package org.abc.matrix.commons.tasks.queue;

/**
 * 任务处理器
 * <p/>
 * Created by chenhui on 16/10/11.
 */
public interface TaskProcessor<T> {

    /**
     * 处理任务
     *
     * @param task
     */
    void process(Task<T> task);

}
