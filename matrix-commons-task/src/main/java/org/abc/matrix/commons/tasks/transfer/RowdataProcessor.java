package org.abc.matrix.commons.tasks.transfer;


import org.abc.matrix.commons.lang.result.BaseResult;

/**
 * 每个线程对每行数据的处理
 * <p>
 * Created by wanjia on 16/11/18.
 */
public interface RowdataProcessor<T> {

    /**
     * 处理的方法
     *
     * @param line 文件中的一行纪录
     * @return
     */
    BaseResult<T> process(String line);
}
