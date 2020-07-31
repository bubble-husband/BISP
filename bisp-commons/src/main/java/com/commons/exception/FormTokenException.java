package com.commons.exception;



public class FormTokenException extends RuntimeException{
    private static final long serialVersionUID = 512936007428810210L;

    private String errorCode;

    private String errorMsg;

    public FormTokenException(String errorCode,String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
    }


    public FormTokenException(String errorCode,String errorMsg,Throwable cause) {
        super(errorMsg,cause);
        this.errorCode = errorCode;
    }

    public FormTokenException(FormTokenExceptionEnum formTokenExceptionEnum) {
        super(formTokenExceptionEnum.getErrorMsg());
        this.errorCode = formTokenExceptionEnum.getErrorCode();
    }

    public FormTokenException(FormTokenExceptionEnum formTokenExceptionEnum,Throwable cause, String errorCode) {
        super(formTokenExceptionEnum.getErrorMsg(),cause);
        this.errorCode = errorCode;
    }
}
