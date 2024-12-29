package com.eLearnPlayer.mapper;

import com.eLearnPlayer.entity.UserData;
import com.eLearnPlayer.entity.UserInfo;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

@Repository
public interface UserInfoMapper extends Mapper<UserInfo> {
    /**
     * 查询user_info表
     * @return
     */
    List<UserInfo> findAll();

    /**
     * 通过名字查询
     */
    List<UserInfo> findByUserName(@Param("userName")String userName);
    /**
     * 通过手机号查询
     */
    List<UserInfo> findByPhone(@Param("phone")String phone);





    //新
    /**
     * 用户唯一性判断
     */
    int checkRepeat(@Param("column")String column,@Param("value")String value);
    /**
     * 获得所有的UserData
     */
    List<UserData> getAllUserData();
    /**
     * 通过手机号查询UserData(with Grade)
     */
    List<UserData> findUserDataByPhone(@Param("phone")String phone);
    /**
     * 增加UserInfo
     */
    void insertUserInfo(@Param("userInfo") UserInfo userInfo);

    /**
     * 更新userInfo
     */
    void updateUserInfo(@Param("userInfo") UserInfo userInfo);
}