package org.abc.matrix.commons.lang.result;

import org.jetbrains.annotations.Nullable;

public interface Result <R>{
    boolean isSuccess();

    int getCode();

    @Nullable
    String getResultMessage();

    void setSuccess(boolean success);

    void setCode(int code);

    void setResultMessage(String resultMessage);

    R getResultData();

    void setResultData(R data);
}
