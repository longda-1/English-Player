package com.eLearnPlayer.mapper;

import com.eLearnPlayer.entity.LevelInfo;
import com.eLearnPlayer.entity.ListeningAudioInfo;
import com.eLearnPlayer.entity.ListeningChoicesInfo;
import com.eLearnPlayer.entity.ListeningQuestionsInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListeningAudioInfoMapper {
    /**
     * 通过selectedRank（第几个）, level获得ReadingPassagesInfo
     */
    ListeningAudioInfo getAudioBySelectedRankAndLevel(@Param("selectedRank") int selectedRank, @Param("level") int level);
    /**
     *
     */
    ListeningAudioInfo getAudioByAudioId(Integer audioId);
    /**
     * 通过audioId获得对应的ListeningQuestionsInfo
     */
    List<ListeningQuestionsInfo> getQuestionsByAudioId(Integer audioId);
    /**
     *通过questions题目列表的questions.listeningQuestionId获得对应的ListeningChoicesInfo
     */
    List<ListeningChoicesInfo> getChoicesByQuestions(List<ListeningQuestionsInfo> questions);
    /**
     *通过level获得对应的LevelInfo
     */
    LevelInfo getLevelInfoByLevel(Integer level);
    /**
     * 获得所有
     */
    List<ListeningAudioInfo> getALlListeningAudioInfo();
    /**
     * 插入listening_audio_info
     */
    void insertListeningAudio(ListeningAudioInfo audioInfo);
    /**
     * 查询audioTitle,返回audioId
     */
    Integer getAudioIdByAudioTitle(@Param("audioTitle")String audioTitle);
    /**
     * 插入ListeningQuestionsInfo
     */
    void insertListeningQuestion(ListeningQuestionsInfo questionsInfo);
    /**
     * 查询查询passageText，返回ListeningQuestionId
     */
    Integer getListeningQuestionIdByPassageText(@Param("passageText") String passageText,@Param("correctAnswer") String correctAnswer,@Param("audioId") Integer audioId);
    /**
     * 插入listening_choices_info
     */
    void insertListeningChoice(ListeningChoicesInfo choicesInfo);
}