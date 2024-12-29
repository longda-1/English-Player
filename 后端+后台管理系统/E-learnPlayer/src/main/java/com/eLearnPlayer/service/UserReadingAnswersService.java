package com.eLearnPlayer.service;

import com.eLearnPlayer.common.Result;
import com.eLearnPlayer.entity.UserReadingAnswersInfo;
import com.eLearnPlayer.mapper.UserReadingAnswersInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author
 * @create 2024-05-08 3:09
 */
@Service
public class UserReadingAnswersService {
    @Resource
    private UserReadingAnswersInfoMapper userReadingAnswersInfoMapper;

    /**
     * 添加userReadingAnswersInfo数据库
     * @param userReadingAnswersInfoList
     * @return
     */
    public Result insertUserReadingAnswers( List<UserReadingAnswersInfo> userReadingAnswersInfoList){
        try {
            for(int i=0; i<userReadingAnswersInfoList.size();i++){
                userReadingAnswersInfoMapper.insertUserReadingAnswers(userReadingAnswersInfoList.get(i));
                System.out.println("User info updated successfully.");
            }
            return Result.success();
        } catch (Exception e){
            return Result.error();
        }
    }
}
