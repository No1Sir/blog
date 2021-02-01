package com.it.fa.exception;


import com.it.fa.utils.APIResponse;

/**
 * 统一异常类
 */
public class DefineException extends RuntimeException {

    protected String errorCode;
    protected APIResponse apiResponse;

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public static DefineException withErrorCode(String errorCode) {
        DefineException defineException = new DefineException();
        defineException.errorCode = errorCode;
        return defineException;
    }
}
