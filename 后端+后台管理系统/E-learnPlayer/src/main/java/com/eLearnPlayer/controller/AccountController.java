package com.eLearnPlayer.controller;

import cn.hutool.core.util.StrUtil;

import cn.hutool.crypto.SecureUtil;
import com.eLearnPlayer.common.Common;
import com.eLearnPlayer.common.Result;
import com.eLearnPlayer.common.ResultCode;
import com.eLearnPlayer.entity.*;
import com.eLearnPlayer.exception.CustomException;
import com.eLearnPlayer.service.AdInfoService;
import com.eLearnPlayer.service.UserInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


/**
 * @author
 * @create 2024-03-22 11:21
 * 登录、退出相关的控制器
 */
@RestController
//返回JSON的数据（对象、类）
public class AccountController {
    @Resource
    private AdInfoService adInfoService;
    @Resource
    private UserInfoService userInfoService;

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    /**
     * 登录
     */
    @PostMapping("/login")
    public Result<AdInfo> login(@RequestBody AdInfo adInfo, HttpServletRequest request){
        if (StrUtil.isBlank(adInfo.getAdName())||StrUtil.isBlank(adInfo.getAdPassword())){
            throw new CustomException(ResultCode.AD_ACCOUNT_ERROR);
        }
        //todo 从数据库查询账号密码是否正确，放到session
        AdInfo login = adInfoService.login(adInfo.getAdName(),adInfo.getAdPassword());
        HttpSession session = request.getSession();
        session.setAttribute(Common.AD_INFO,login);
        session.setMaxInactiveInterval(60*60*24);
        return Result.success(login);
    }

    /**
     * 重置密码     目前直接定义为1123456！！！
     */
    @PostMapping("/resetPassword")
    public Result<AdInfo> resetPassword(@RequestBody AdInfo adInfo){
        return Result.success(adInfoService.resetPassword(adInfo.getAdName()));
    }
    /**
     * 修改密码
     */
    @PostMapping("/changePassword")
    public Result changePassword(@RequestBody Map<String, String> request){
        String adName = request.get("adName");
        String oldPassword = request.get("oldPassword");
        String newPassword = request.get("newPassword");
        if (oldPassword==newPassword){
            return Result.error();
        }else {
            return Result.success(adInfoService.changePassword(adName,newPassword));
        }

    }

    /**
     * 注册  /register ——web
     */
    @PostMapping("/register")
    public Result register(@RequestBody AdInfo adInfo){
//        System.out.println(adInfo);
//        System.out.println("________________");
        if (adInfo.getAdPassword()!=null && !adInfo.getAdPassword().isEmpty()){
            adInfo.setAdPassword(SecureUtil.md5(adInfo.getAdPassword()));
        }
        if (adInfo.getHead()!=null && !adInfo.getHead().isEmpty()){
            adInfo.setHead("admin\\"+adInfo.getHead());
//            System.out.println(adInfo.getHead());
        }
//        System.out.println("adInfo:"+adInfo);
        return adInfoService.register(adInfo);
    }
    /**
     * 登出
     */
    @GetMapping("/logout")
    public Result logout(HttpServletRequest request){
        request.getSession().setAttribute(Common.AD_INFO,null);
        return Result.success();
    }

//    /**
//     * 后台新增小程序用户(web端)
//     */
//    @PostMapping("/addUser")
//    public Result<UserInfo> addUser(@RequestBody UserInfo userInfo){
//        userInfoService.addUserInfo(userInfo);
//        return Result.success(userInfo);
//    }

//    /**
//     * 小程序端用户注册(利用的是-账号密码)
//     */
//    @PostMapping("/register")
//    public Result<UserInfo> register(@RequestBody UserInfo userInfo,HttpServletRequest request){
//        // 记录请求到达日志
//        logger.info("Received a POST request to /register");
//
//        if (StrUtil.isBlank(userInfo.getUserName()) || StrUtil.isBlank(userInfo.getUserPassword())){
//            throw new CustomException((ResultCode.PARAM_ERROR));
//        }
//
//        UserInfo register = userInfoService.addUserInfo(userInfo);
//        HttpSession session = request.getSession();
//        session.setAttribute(Common.USER_INFO,register);
//        session.setMaxInactiveInterval(60*60*24);
//
//        // 打印接收到的用户信息
//        logger.info("Received user info: {}", userInfo);
//        // TODO: 处理注册逻辑
//
//
//        return Result.success(register);
//    }

    /**
     * 小程序端用户登录(利用的是-手机号)
     * @param phoneInfo
     * @return
     */
    @PostMapping("/wxLoginByPhone")
    public Result<UserData> wxLoginByPhone(@RequestBody PhoneInfo phoneInfo) {
        System.out.println(phoneInfo.getPhoneNumber());
        // 判断手机号是否为空
        if (StrUtil.isBlank(phoneInfo.getPhoneNumber())) {
            throw new CustomException(ResultCode.PARAM_ERROR);
        }
        UserData userData = new UserData();
        UserInfo userInfo = new UserInfo();
        userInfo.setPhone(phoneInfo.getPhoneNumber());
        //System.out.println(userInfo);
        userData.setUserInfo(userInfo);
        //System.out.println(userData);
        // 调用服务层方法进行处理
        UserData result = userInfoService.wxLoginByPhone(userData);
        return Result.success(result);
    }

}
