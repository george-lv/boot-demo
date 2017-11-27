package com.greentown.learn.bootdemo.exception;

import com.greentown.learn.bootdemo.common.ExceptionConstants.ResultEnums;

public class BusinessLogicException extends RuntimeException {
    private static final long serialVersionUID = -1658132880061605029L;

    private ResultEnums       resultEnum;

    private Object            extra;

    public BusinessLogicException(ResultEnums resultEnum) {
        this.resultEnum = resultEnum;
    }

    public BusinessLogicException(ResultEnums resultEnum, Object extra) {
        this.resultEnum = resultEnum;
        this.extra = extra;
    }

    public ResultEnums getResultEnum() {
        return resultEnum;
    }

    public Object getExtra() {
        return extra;
    }
}