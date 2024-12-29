package com.eLearnPlayer.service;

import cn.hutool.core.collection.CollectionUtil;

import com.eLearnPlayer.common.Result;

import com.eLearnPlayer.entity.UserData;
import com.eLearnPlayer.entity.UserInfo;

import com.eLearnPlayer.mapper.UserInfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 查询方法
 * @author
 * @create 2024-03-25 11:18
 */
@Service
public class UserInfoService {
    @Resource
    private UserInfoMapper userInfoMapper;

    /**
     * 分页查询用户列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<UserData> findPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<UserData> list = userInfoMapper.getAllUserData();
//        System.out.println(list.get(0));
//        System.out.println(list);
        return PageInfo.of(list);
    }


//    /**
//     * 新增用户信息(小程序-账号密码)
//     * @param userInfo
//     */
//    public UserInfo addUserInfo(UserInfo userInfo){
//        //判断数据库里是否有该用户
//        List<UserInfo> list = userInfoMapper.findByUserName(userInfo.getUserName());
//        //判断数据库是否有该用户
//        if (CollectionUtil.isEmpty(list)){
//            throw new CustomException(ResultCode.AD_NOT_EXIST_ERROR);
//        }
//        if (StrUtil.isBlank(userInfo.getUserPassword())){
//            //若密码为空（未输入密码），则默认密码为123456
//            userInfo.setUserPassword(SecureUtil.md5("123456"));
//        }else {
//            userInfo.setUserPassword(SecureUtil.md5(userInfo.getUserPassword()));
//        }
//        userInfoMapper.insert(userInfo);
//        return userInfo;
//    }

    /**
     * 根据手机号查询用户信息
     * @param userData
     * @return
     */
    public UserData wxLoginByPhone(UserData userData){

        System.out.println(userData.getUserInfo().getPhone());
        List<UserData> existingUser = userInfoMapper.findUserDataByPhone(userData.getUserInfo().getPhone());
        //System.out.println(existingUser);
        //System.out.println(existingUser.size());
        //System.out.println(existingUser.get(0));
        //如果用户不存在，则新增用户信息
        if (CollectionUtil.isEmpty(existingUser)){
            UserData userDataNew = new UserData();
            //System.out.println(userData.getUserInfo().getPhone());
            userDataNew.setDefaultValues(userData.getUserInfo().getPhone());
            //System.out.println("userDataNew:"+userDataNew);
            UserInfo newUserInfo = userDataNew.getUserInfo();
            userInfoMapper.insertUserInfo(newUserInfo);
            List<UserData> insertedUserData = userInfoMapper.findUserDataByPhone(newUserInfo.getPhone());
            //System.out.println("insertedUserData:"+insertedUserData);
            if (CollectionUtil.isNotEmpty(insertedUserData)) {
                return insertedUserData.get(0); // 返回插入后的 UserData
            } else {
                throw new RuntimeException("User was inserted but not found when re-querying.");
            }
        }
        // 如果用户已存在，则直接返回用户信息
        return existingUser.get(0);
    }

    /**
     * 更新userInfo数据库
     * @param userInfo
     */
    public Result updateUser(UserInfo userInfo){
        try {
            //System.out.println(userInfo);
            userInfoMapper.updateUserInfo(userInfo);
            System.out.println("User info updated successfully.");
            return Result.success();
        } catch (Exception e){
            return Result.error();
        }
    }

    public Result insertUser(UserInfo userInfo){
        try {
            userInfoMapper.insertUserInfo(userInfo);
            System.out.println("User info updated successfully.");
            return Result.success();
        } catch (Exception e){
            return Result.error();
        }
    }
}
