package com.eLearnPlayer.entity;

/**
 * 数据库里没有 - 电话信息+code
 * 为了解析从前端（小程序端）传回来的phone
 * @author
 * @create 2024-04-14 13:15
 */
public class PhoneInfo {
    private String code;
    private String phoneNumber;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
