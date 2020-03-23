package com.learn.okhttp;

/**
 * create: Ren Zhongrui
 * date: 2020-03-23
 * description:
 */
public class Weather {

    private String reason;
    private String errorCode;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "reason='" + reason + '\'' +
                ", errorCode='" + errorCode + '\'' +
                '}';
    }


}
