package com.eLearnPlayer.exception;

import com.eLearnPlayer.common.ResultCode;

/**
 * @author
 * @create 2024-03-23 11:24
 * 用户自定义异常，用于前端返回错误信息
 */

public class CustomException extends RuntimeException{
    private String code;
    private String msg;

    public CustomException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public CustomException(ResultCode resultCode) {
        this.code = resultCode.code;
        this.msg = resultCode.msg;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
