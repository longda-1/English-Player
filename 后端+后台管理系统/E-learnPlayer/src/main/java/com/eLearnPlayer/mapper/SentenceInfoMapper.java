package com.eLearnPlayer.mapper;

import com.eLearnPlayer.entity.SentenceInfo;
import com.eLearnPlayer.entity.SentenceInfoWithBLOBs;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SentenceInfoMapper {
    /**
     *  根据SentenceId查找例句
     */
    List<SentenceInfoWithBLOBs> findBySentenceId(@Param("sentenceId") Integer sentenceId);
}