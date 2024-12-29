package com.eLearnPlayer.common;

/**
 * @author
 * @create 2024-03-22 11:05
 * 统一返回码
 */
public enum ResultCode {
    SUCCESS("0","成功"),
    ERROR("-1","系统异常"),
    PARAM_ERROR("1001","参数异常"),
    AD_ACCOUNT_ERROR("2002","账户或密码错误"),
    AD_NOT_EXIST_ERROR("2003","未找到用户"),
    AD_EXIST_ERROR("2004","未找到用户"),
    BOOK_EXIST_ERROR("2005","未找到书籍");
    public String code;
    public String msg;
    ResultCode(String code,String msg){
        this.code = code;
        this.msg = msg;
    }
}
