package com.eLearnPlayer.controller;

import com.eLearnPlayer.common.Result;
import com.eLearnPlayer.entity.UserReadingAnswersInfo;
import com.eLearnPlayer.service.UserReadingAnswersService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author
 * @create 2024-05-08 3:07
 */
@RestController
@RequestMapping(value = "/UserReadingAnswers")
public class UserReadingAnswersController {
    @Resource
    private UserReadingAnswersService userReadingAnswersService;

    /**
     * 添加用户阅读答题记录
     * @param userReadingAnswersInfo
     * @return
     */
    @PostMapping("/insertUserReadingAnswers")
    public Result insertUserReadingAnswers(@RequestBody List<UserReadingAnswersInfo> userReadingAnswersInfo){
        return userReadingAnswersService.insertUserReadingAnswers(userReadingAnswersInfo);
    }
}
