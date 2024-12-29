package com.eLearnPlayer.service;

import com.eLearnPlayer.common.Result;
import com.eLearnPlayer.entity.UserListeningAnswersInfo;
import com.eLearnPlayer.mapper.UserListeningAnswersInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author
 * @create 2024-05-18 5:56
 */
@Service
public class UserListeningAnswersService {
    @Resource
    private UserListeningAnswersInfoMapper userListeningAnswersInfoMapper;

    public Result insertUserListeningAnswers(List<UserListeningAnswersInfo> userListeningAnswersInfoList){
        try {
            for(int i=0; i<userListeningAnswersInfoList.size();i++){
                userListeningAnswersInfoMapper.insertUserListeningAnswers(userListeningAnswersInfoList.get(i));
                System.out.println("User info updated successfully.");
            }
            return Result.success();
        } catch (Exception e){
            return Result.error();
        }
    }
}
