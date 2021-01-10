package com.exp.shuadan.config;

public class DataCheckException extends Exception {

    private int code;
    private String message;

    public DataCheckException(String message) {
        this.code = 500;
        this.message = message;
    }

    public DataCheckException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode( ) {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage( ) {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString( ) {
        return "DataCheckException{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}