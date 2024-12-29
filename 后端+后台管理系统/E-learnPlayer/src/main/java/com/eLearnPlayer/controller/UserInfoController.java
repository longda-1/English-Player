package com.eLearnPlayer.controller;

import com.eLearnPlayer.common.Result;
import com.eLearnPlayer.entity.UserData;
import com.eLearnPlayer.entity.UserInfo;

import com.eLearnPlayer.service.UserInfoService;
import com.github.pagehelper.PageInfo;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户增删改查控制器
 * @author
 * @create 2024-03-25 11:48
 */
@RestController
@RequestMapping(value = "/UserInfo")
public class UserInfoController {
    @Resource
    private UserInfoService userInfoService;

    /**
     * 分页查询用户列表
     * @param pageNum   第几页
     * @param pageSize  每页大小
     * @return
     */
    @GetMapping("/page/{pageNum}")
    public Result<PageInfo<UserData>> page(@PathVariable Integer pageNum,
                                           @RequestParam(defaultValue = "10") Integer pageSize){
        return Result.success(userInfoService.findPage(pageNum,pageSize));
    }

    /**
     * 更新用户信息——小程序
     * @param userData
     * @return
     */
    @PostMapping("/updateUser")
    public Result updateUser(@RequestBody UserData userData){
        UserInfo userInfo = userData.getUserInfo();
//        System.out.println("userInfo.head:"+userInfo.getHead());
        if(userInfo.getHead() == "http://localhost:8000/icons/my.png"){
            userInfo.setHead("user\\my.png");
        }else {
            String replacedString01 = userInfo.getHead().replace("http://localhost:8000/", "");
            String replacedString02 = replacedString01.replace("/", "\\");
            userInfo.setHead(replacedString02);
        }

//        System.out.println("userInfo:"+userInfo);

        return userInfoService.updateUser(userInfo);
    }

    @PostMapping("/insertUser")
    public Result insertUser(@RequestBody UserData userData){
        System.out.println(userData);
        UserInfo userInfo = userData.getUserInfo();
        if (userInfo.getGold() == null){
            userInfo.setGold(5);
        }
        if (userInfo.getJewel() == null){
            userInfo.setJewel(5);
        }
        if (userInfo.getRank() == null || userInfo.getRank().isEmpty()){
            userInfo.setRank("1-0-0-0");
        }
        if (userInfo.getMedalId() == null){
            userInfo.setMedalId(5);
        }
        if (userInfo.getHead() != null && !userInfo.getHead().isEmpty()){
            userInfo.setHead("user\\"+userInfo.getHead());
        }
        System.out.println("userInfo:"+userInfo);
        return userInfoService.insertUser(userInfo);
    }

}
