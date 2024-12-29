package com.eLearnPlayer.service;

import com.eLearnPlayer.entity.*;
import com.eLearnPlayer.mapper.ReadingPassagesInfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @create 2024-04-20 12:23
 */
@Service
public class ReadingInfoService {
    @Resource
    private ReadingPassagesInfoMapper readingPassagesInfoMapper;


    /**
     * 分页查询阅读列表-web
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<ReadingPassagesInfoWithGrade> findPage(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<ReadingPassagesInfoWithGrade> list = readingPassagesInfoMapper.findAllWithGrade();
        System.out.println(list);
        return PageInfo.of(list);
    }

    /**
     * 根据passageId获得阅读详情——web
     * @param passageId
     * @return
     */
    public ReadingData getReadingDetails(Integer passageId){
        ReadingData readingData = new ReadingData();

        //查找
        ReadingPassagesInfo passagesInfo = readingPassagesInfoMapper.getPassageByPassageId(passageId);
        List<ReadingQuestionsInfo> questions = readingPassagesInfoMapper.getQuestionsByPassageId(passageId);
        List<ReadingChoicesInfo> choices = readingPassagesInfoMapper.getChoicesByQuestions(questions);
        LevelInfo levelInfo = readingPassagesInfoMapper.getLevelInfoByLevel(passagesInfo.getLevel());
        //封装
        readingData.setPassage(passagesInfo);
        readingData.setQuestions(questions);
        readingData.setChoices(choices);
        readingData.setLevelInfo(levelInfo);

        System.out.println(readingData);
        return readingData;
    }

    /**
     * 通过选中的关卡，水平获得阅读数据（ReadingData封装）——小程序端
     * 就一条，不用list封装了
     */
    public ReadingData getReadingByLevelAndRank(Integer level, Integer selectedRank){
        ReadingData readingData = new ReadingData();
        //查询文章信息
        int adjustedSelectedRank = selectedRank - 1;
        ReadingPassagesInfo readingPassagesInfo = readingPassagesInfoMapper.getPassageBySelectedRankAndLevel(adjustedSelectedRank, level);
        List<ReadingQuestionsInfo> readingQuestionsInfoList = readingPassagesInfoMapper.getQuestionsByPassageId(readingPassagesInfo.getPassageId());
        List<ReadingChoicesInfo> readingChoicesInfoList = readingPassagesInfoMapper.getChoicesByQuestions(readingQuestionsInfoList);
        LevelInfo readingLevelInfo = readingPassagesInfoMapper.getLevelInfoByLevel(level);
        //封装ReadingData
        readingData.setPassage(readingPassagesInfo);
        readingData.setQuestions(readingQuestionsInfoList);
        readingData.setChoices(readingChoicesInfoList);
        readingData.setLevelInfo(readingLevelInfo);
        System.out.println("11111");
        return readingData;
    }

    /**
     * 更新readingData - web
     * @param readingData
     */
    public void upDataReadingData(ReadingData readingData){
        ReadingPassagesInfo passagesInfo = readingData.getPassage();
        readingPassagesInfoMapper.insertReadingPassage(passagesInfo);
        Integer passageId = readingPassagesInfoMapper.getPassageIdByPassageTitle(passagesInfo.getPassageTitle());
        System.out.println("passageId:"+passageId);

        List<ReadingQuestionsInfo> questions = readingData.getQuestions();
        List<ReadingChoicesInfo> choices = readingData.getChoices();
        int count = 0;
        for (int i=0;i<questions.size();i++){
            ReadingQuestionsInfo readingQuestionsInfo = questions.get(i);
            readingQuestionsInfo.setPassageId(passageId);
            readingPassagesInfoMapper.insertReadingQuestion(readingQuestionsInfo);
            Integer readingQuestionId = readingPassagesInfoMapper.getReadingQuestionIdByPassageText(readingQuestionsInfo.getPassageText());
            for (int j=0;j<4;j++){
                ReadingChoicesInfo readingChoicesInfo = choices.get(count);
                readingChoicesInfo.setReadingQuestionId(readingQuestionId);
                readingPassagesInfoMapper.insertReadingChoice(readingChoicesInfo);
                count++;
            }
        }
    }
}
