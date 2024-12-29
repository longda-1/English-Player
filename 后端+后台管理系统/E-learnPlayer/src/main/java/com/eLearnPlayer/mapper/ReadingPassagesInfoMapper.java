package com.eLearnPlayer.mapper;


import com.eLearnPlayer.entity.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReadingPassagesInfoMapper {
    /**
     *查询阅读表(Reading_Passages_info)全部内容-文章id、标题、内容、级别
     */
    List<ReadingPassagesInfoWithGrade> findAllWithGrade();


    //xin
    /**
     * 通过selectedRank（第几个）, level获得ReadingPassagesInfo
     */
    ReadingPassagesInfo getPassageBySelectedRankAndLevel(@Param("selectedRank") int selectedRank, @Param("level") int level);
    /**
     * 通过passageId获得对应的 reading_passages_info
     */
    ReadingPassagesInfo getPassageByPassageId(Integer passageId);
    /**
     * 通过passageId获得对应的ReadingQuestionsInfo
     */
    List<ReadingQuestionsInfo> getQuestionsByPassageId(Integer passageId);
    /**
     * 通过questions题目列表的questions.readingQuestionId获得对应的ReadingChoicesInfo
     */
    List<ReadingChoicesInfo> getChoicesByQuestions(List<ReadingQuestionsInfo> questions);
    /**
     * 通过level获得对应的LevelInfo
     */
    LevelInfo getLevelInfoByLevel(Integer level);
    /**
     * 插入readingPassageInfo
     */
    void insertReadingPassage(ReadingPassagesInfo passageInfo);
    /**
     * 查询passageTitle,返回passageId
     */
    Integer getPassageIdByPassageTitle(@Param("passageTitle") String passageTitle);
    /**
     * 插入readingQuestionsInfo
     */
    void insertReadingQuestion(ReadingQuestionsInfo questionsInfo);
    /**
     *查询查询passageText，返回readingQuestionId
     */
    Integer getReadingQuestionIdByPassageText(@Param("passageText") String passageText);
    /**
     * 插入reading_choices_info
     */
    void insertReadingChoice(ReadingChoicesInfo choicesInfo);
}