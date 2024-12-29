package com.eLearnPlayer.controller;

import com.eLearnPlayer.common.Result;
import com.eLearnPlayer.entity.UserListeningAnswersInfo;
import com.eLearnPlayer.service.UserListeningAnswersService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author
 * @create 2024-05-18 5:56
 */
@RestController
@RequestMapping(value = "/UserListeningAnswers")
public class UserListeningAnswersController {
    @Resource
    private UserListeningAnswersService userListeningAnswersService;

    /**
     * 添加用户听力答题记录
     * @param userListeningAnswersInfo
     * @return
     */
    @PostMapping("/insertUserListeningAnswers")
    public Result insertUserListeningAnswers(@RequestBody List<UserListeningAnswersInfo> userListeningAnswersInfo){
        return userListeningAnswersService.insertUserListeningAnswers(userListeningAnswersInfo);
    }
}
