package com.eLearnPlayer.mapper;

import com.eLearnPlayer.entity.WordQuestionInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordQuestionInfoMapper {
    /**
     * 通过word_question_id查找表word_question_info(问题表)
     */
    List<WordQuestionInfo> findAllByWordQuestionId(Integer wordQuestionId);
}