package org.abc.matrix.commons.lang.templates;

import org.abc.matrix.commons.lang.result.BaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * 重试模版
 * <p/>
 * Created by chenhui on 15/10/20.
 */
public abstract class RetryTransactionTemplate<E> {
    private static final int EXCUTE_NUM_DEFAULT = 1;
    private int executeNum = EXCUTE_NUM_DEFAULT;

    private Logger logger = LoggerFactory.getLogger(RetryTransactionTemplate.class);

    public abstract BaseResult<E> doInTransaction() throws Exception;

    public BaseResult<E> execute() {
        BaseResult<E> obj = null;
        boolean success = false;
        Exception exception = null;
        for (int currentNum = executeNum; currentNum > 0 && !success; currentNum--) {
            try {
                obj = this.doInTransaction();
                if (obj.isSuccess())
                    success = true;
            } catch (Exception e1) {
                exception = e1;
                logger.error("重试执行对应的业业务出现异常，原因:" + exception.getCause() + exception.getMessage());
            }
        }
        return obj;
    }

    public Future<BaseResult<E>> submit(ExecutorService executorService) {
        if (executorService == null)
            throw new RuntimeException("抱歉，请确认执行的线程资源.");
        else
            return executorService.submit(new Callable<BaseResult<E>>() {
                @Override
                public BaseResult<E> call() throws Exception {
                    return execute();
                }
            });
    }

    public int getExcuteNum() {
        return executeNum;
    }

    public void setExcuteNum(int excuteNum) {
        this.executeNum = excuteNum;
    }

}
