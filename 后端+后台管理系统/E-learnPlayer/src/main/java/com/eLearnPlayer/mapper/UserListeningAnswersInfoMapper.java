package com.eLearnPlayer.mapper;

import com.eLearnPlayer.entity.UserListeningAnswersInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserListeningAnswersInfoMapper {
   void insertUserListeningAnswers(@Param("userListeningAnswersInfo")UserListeningAnswersInfo userListeningAnswersInfo);
}