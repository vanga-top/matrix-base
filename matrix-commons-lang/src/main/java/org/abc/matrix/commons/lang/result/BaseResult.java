package org.abc.matrix.commons.lang.result;

import java.io.Serializable;

/**
 * 基本的返回结果类型,用于平台数据类型返回
 * <p/>
 * <p/>
 * Created by wanjia on 16/9/27.
 */
public class BaseResult<T> implements Serializable {

    /**
     * 返回的数据结果
     */
    private T resultData;//返回结果

    /**
     * 返回是否成功,注意这里的成功包括了业务和框架同时成功,如果是查不到结果,或者系统异常都会返回false
     * 只有一种情况会返回成功,就是有数据,且业务执行正常
     */
    private boolean success;

    /**
     * 返回处理的code
     */
    private String code;

    /**
     * 返回内容的信息
     */
    private String resultMessage;


    public BaseResult() {

    }

    /**
     * 存放所有信息
     *
     * @param resultData
     * @param success
     * @param resultMessage
     */
    public BaseResult(T resultData, boolean success, String code, String resultMessage) {
        this.resultData = resultData;
        this.success = success;
        this.code = code;
        this.resultMessage = resultMessage;
    }

    public T getResultData() {
        return resultData;
    }

    public BaseResult setResultData(T resultData) {
        this.resultData = resultData;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public BaseResult setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public BaseResult setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
        return this;
    }

    public String getCode() {
        return code;
    }

    public BaseResult setCode(String code) {
        this.code = code;
        return this;
    }

    /**
     * 设置错误的返回
     *
     * @param resultMessage
     * @return
     */
    public BaseResult setErrorReturn(String code, String resultMessage) {
        this.resultMessage = resultMessage;
        this.code = code;
        this.success = false;
        return this;
    }

    /**
     * 设置正确的返回
     *
     * @param resultData
     * @return
     */
    public BaseResult setSuccessfulReturn(T resultData, String code, String resultMessage) {
        this.resultData = resultData;
        this.code = code;
        this.success = true;
        this.resultMessage = resultMessage;
        return this;
    }

    @Override
    public String toString() {
        return "BaseResult{" +
                "resultData=" + resultData +
                ", success=" + success +
                ", resultMessage='" + resultMessage + '\'' +
                '}';
    }
}
