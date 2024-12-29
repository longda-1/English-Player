package com.eLearnPlayer.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table ad_info
 */
@Table(name = "ad_info")
public class AdInfo {
    /**
     * Database Column Remarks:
     *   管理员用户id
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ad_info.Ad_id
     *
     * @mbg.generated
     */
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer adId;

    /**
     * Database Column Remarks:
     *    登录用户名
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ad_info.Ad_name
     *
     * @mbg.generated
     */
    private String adName;

    /**
     * Database Column Remarks:
     *    登录密码（md5加密）
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ad_info.Ad_password
     *
     * @mbg.generated
     */
    private String adPassword;

    /**
     * Database Column Remarks:
     *    联系方式
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ad_info.Ad_phone
     *
     * @mbg.generated
     */
    private String adPhone;

    /**
     * Database Column Remarks:
     *    姓名（昵称）
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ad_info.Nick_name
     *
     * @mbg.generated
     */
    private String nickName;

    /**
     * Database Column Remarks:
     *    邮箱
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ad_info.Email
     *
     * @mbg.generated
     */
    private String email;

    /**
     * Database Column Remarks:
     *    头像
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ad_info.Head
     *
     * @mbg.generated
     */
    private String head;

    /**
     * Database Column Remarks:
     *    性别
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ad_info.Sex
     *
     * @mbg.generated
     */
    private String sex;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad_info.Ad_id
     *
     * @return the value of ad_info.Ad_id
     *
     * @mbg.generated
     */
    public Integer getAdId() {
        return adId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad_info.Ad_id
     *
     * @param adId the value for ad_info.Ad_id
     *
     * @mbg.generated
     */
    public void setAdId(Integer adId) {
        this.adId = adId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad_info.Ad_name
     *
     * @return the value of ad_info.Ad_name
     *
     * @mbg.generated
     */
    public String getAdName() {
        return adName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad_info.Ad_name
     *
     * @param adName the value for ad_info.Ad_name
     *
     * @mbg.generated
     */
    public void setAdName(String adName) {
        this.adName = adName == null ? null : adName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad_info.Ad_password
     *
     * @return the value of ad_info.Ad_password
     *
     * @mbg.generated
     */
    public String getAdPassword() {
        return adPassword;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad_info.Ad_password
     *
     * @param adPassword the value for ad_info.Ad_password
     *
     * @mbg.generated
     */
    public void setAdPassword(String adPassword) {
        this.adPassword = adPassword == null ? null : adPassword.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad_info.Ad_phone
     *
     * @return the value of ad_info.Ad_phone
     *
     * @mbg.generated
     */
    public String getAdPhone() {
        return adPhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad_info.Ad_phone
     *
     * @param adPhone the value for ad_info.Ad_phone
     *
     * @mbg.generated
     */
    public void setAdPhone(String adPhone) {
        this.adPhone = adPhone == null ? null : adPhone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad_info.Nick_name
     *
     * @return the value of ad_info.Nick_name
     *
     * @mbg.generated
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad_info.Nick_name
     *
     * @param nickName the value for ad_info.Nick_name
     *
     * @mbg.generated
     */
    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad_info.Email
     *
     * @return the value of ad_info.Email
     *
     * @mbg.generated
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad_info.Email
     *
     * @param email the value for ad_info.Email
     *
     * @mbg.generated
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad_info.Head
     *
     * @return the value of ad_info.Head
     *
     * @mbg.generated
     */
    public String getHead() {
        return head;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad_info.Head
     *
     * @param head the value for ad_info.Head
     *
     * @mbg.generated
     */
    public void setHead(String head) {
        this.head = head == null ? null : head.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad_info.Sex
     *
     * @return the value of ad_info.Sex
     *
     * @mbg.generated
     */
    public String getSex() {
        return sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad_info.Sex
     *
     * @param sex the value for ad_info.Sex
     *
     * @mbg.generated
     */
    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }
}