package org.abc.matrix.commons.tasks.queue;

/**
 * 基础的task
 * <p/>
 * Created by wanjia on 16/10/11.
 */
public class Task<T> {

    private T data;
    private String key;//task的key
    private long submitTime;//提交时间
    private long processTime;//处理时长

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(long submitTime) {
        this.submitTime = submitTime;
    }

    public long getProcessTime() {
        return processTime;
    }

    public void setProcessTime(long processTime) {
        this.processTime = processTime;
    }
}
