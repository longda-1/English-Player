package com.eLearnPlayer.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.crypto.SecureUtil;
import com.eLearnPlayer.common.Result;
import com.eLearnPlayer.common.ResultCode;
import com.eLearnPlayer.entity.AdInfo;
import com.eLearnPlayer.exception.CustomException;
import com.eLearnPlayer.mapper.AdInfoMapper;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
//测试
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author
 * @create 2024-03-22 21:43
 * 管理员用户相关的service
 */
@Service
public class AdInfoService {
    @Resource
    private AdInfoMapper adInfoMapper;
   //测试
//    private static final Logger logger = LoggerFactory.getLogger(AdInfoService.class);

    /**
     * 登录
     * @param adName
     * @param adPassword
     * @return
     */
    public AdInfo login(String adName,String adPassword){
        List<AdInfo> list = adInfoMapper.findByAdName(adName);
        //判断数据库是否有该用户
//        System.out.println(list);
        if (CollectionUtil.isEmpty(list)){
            throw new CustomException(ResultCode.AD_NOT_EXIST_ERROR);
        }

        //测试
//        String text = SecureUtil.md5(adPassword);
//        System.out.println(text);
//        System.out.println(list.get(0).getAdPassword());
//        System.out.println(SecureUtil.md5("123456"));

        //判断密码是否正确
        //md5加密
        if (!SecureUtil.md5(adPassword).equals(list.get(0).getAdPassword())){
            throw new CustomException(ResultCode.AD_ACCOUNT_ERROR);
        }
        //测试
//        AdInfo adInfo = list.get(0);
//        logger.info("Retrieved user info: {}", adInfo); // 添加这行日志打印

        return list.get(0);
            }

    /**
     * 重置密码（忘记密码）
     * @param adName
     * @return
     */
    public AdInfo resetPassword(String adName){
        List<AdInfo> list = adInfoMapper.findByAdName(adName);
        //判断数据库是否有该用户
        if (CollectionUtil.isEmpty(list)){
            throw new CustomException(ResultCode.AD_NOT_EXIST_ERROR);
        }
        list.get(0).setAdPassword(SecureUtil.md5("123456"));    //改！！！使用验证码等
        adInfoMapper.updateByPrimaryKeySelective(list.get(0));
        return list.get(0);
    }

    /**
     * 修改密码
     * @param adName
     * @param newPassword
     * @return
     */
    public AdInfo changePassword(String adName,String newPassword){
        adInfoMapper.updateAdPassword(adName,newPassword);
        AdInfo adInfo = (AdInfo) adInfoMapper.findByAdName(adName);
        return adInfo;
    }

    /**
     * 注册——web
     * @param adInfo
     * @return
     */
    public Result register(AdInfo adInfo){
        try {
            adInfoMapper.insertAll(adInfo);
            System.out.println("User info updated successfully.");
            return Result.success();
        } catch (Exception e){
            return Result.error();
        }
    }
}
