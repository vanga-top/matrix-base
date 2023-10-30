package org.abc.matrix.commons.tasks.single;

/**
 * 定义处理器，用于扩展来处理任务
 * <p/>
 * Created by wanjia on 15/8/19.
 */
public abstract class TaskProcessor {

    /**
     * 处理具体的任务
     *
     * @param task
     * @return
     */
    public abstract boolean process(String key, Task task);
}
