package com.eLearnPlayer.mapper;

import com.eLearnPlayer.entity.WordChoicesInfo;
import com.eLearnPlayer.entity.WordQuestionInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordChoicesInfoMapper {
    /**
     * 通过wordQuestionId查找表word_choices_info(选择表)
     */
    List<WordChoicesInfo> findAllByWordQuestionId(Integer wordQuestionId);
}