package com.eLearnPlayer.mapper;

import com.eLearnPlayer.entity.UserReadingAnswersInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReadingAnswersInfoMapper {
    void insertUserReadingAnswers(@Param("userReadingAnswersInfo")UserReadingAnswersInfo userReadingAnswersInfo);
}