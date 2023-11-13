package org.abc.matrix.commons.lang.result;

import java.io.Serializable;

/**
 * 基本的返回结果类型,用于平台数据类型返回
 * <p/>
 * <p/>
 * Created by wanjia on 16/9/27.
 */
public class BaseResult<T> implements Result<T>, Serializable {

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
    private int code;

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
    public BaseResult(T resultData, boolean success, int code, String resultMessage) {
        this.resultData = resultData;
        this.success = success;
        this.code = code;
        this.resultMessage = resultMessage;
    }

    public T getResultData() {
        return resultData;
    }

    public void setResultData(T resultData) {
        this.resultData = resultData;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    /**
     * 设置错误的返回
     *
     * @param resultMessage
     * @return
     */
    public BaseResult setErrorReturn(int code, String resultMessage) {
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
    public static <T> BaseResult<T> success(int code, T resultData, String resultMessage) {
        BaseResult<T> result = new BaseResult<>();
        result.resultData = resultData;
        result.code = code;
        result.success = true;
        result.resultMessage = resultMessage;
        return result;
    }

    public static <T> BaseResult<T> error(int code, T resultData, String resultMessage) {
        BaseResult<T> result = new BaseResult<>();
        result.success = false;
        result.code = code;
        result.resultData = resultData;
        result.resultMessage = resultMessage;
        return result;
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
