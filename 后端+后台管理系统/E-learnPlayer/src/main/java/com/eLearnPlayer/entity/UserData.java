package com.eLearnPlayer.entity;

import cn.hutool.crypto.SecureUtil;

/**
 * @author
 * @create 2024-04-05 1:17
 */
public class UserData {
    private LevelInfo levelInfo;
    private MedalInfo medalInfo;
    private UserInfo userInfo;

    public LevelInfo getLevelInfo() {
        return levelInfo;
    }

    public void setLevelInfo(LevelInfo levelInfo) {
        this.levelInfo = levelInfo;
    }

    public MedalInfo getMedalInfo() {
        return medalInfo;
    }

    public void setMedalInfo(MedalInfo medalInfo) {
        this.medalInfo = medalInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    /**
     * 设置由phone构造的默认值
     * @param phone
     */
    public UserInfo setDefaultValues(String phone){
        if (this.userInfo == null){
            this.userInfo = new UserInfo();
        }
        if (this.userInfo.getUserName() == null || this.userInfo.getUserName().isEmpty()){

            this.userInfo.setUserName("user"+getLastFourDigits(phone));
        }
        if (this.userInfo.getUserPassword() == null || this.userInfo.getUserPassword().isEmpty()){
            this.userInfo.setUserPassword(SecureUtil.md5("123456"));
        }else {
            //对非空的密码进行md5加密
            this.userInfo.setUserPassword(SecureUtil.md5(this.userInfo.getUserPassword()));
        }
        if (this.userInfo.getNickName() == null || this.userInfo.getNickName().isEmpty()){
            this.userInfo.setNickName("user");
        }
        if (this.userInfo.getGold() == null){
            this.userInfo.setGold(5);
        }
        if (this.userInfo.getJewel() == null) {
            this.userInfo.setJewel(5);
        }
        if (this.userInfo.getLevel() == null) {
            this.userInfo.setLevel(15);
        }
        if (this.userInfo.getRank() == null || this.userInfo.getRank().isEmpty()) {
            this.userInfo.setRank("1-0-0");
        }
        if (this.userInfo.getMedalId() == null) {
            this.userInfo.setMedalId(5);
        }
        this.userInfo.setPhone(phone);
        return userInfo;
    }
    // 获取手机号后四位的方法
    private String getLastFourDigits(String phone) {
        if (phone != null && phone.length() >= 4) {
            return phone.substring(phone.length() - 4);
        } else {
            // 如果手机号不足四位，则返回空字符串
            return "";
        }
    }
}
