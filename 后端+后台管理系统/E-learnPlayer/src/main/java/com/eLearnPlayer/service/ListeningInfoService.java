package com.eLearnPlayer.service;

import com.eLearnPlayer.entity.*;
import com.eLearnPlayer.mapper.ListeningAudioInfoMapper;
import com.eLearnPlayer.mapper.ListeningQuestionsInfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @create 2024-04-18 16:04
 */
@Service
public class ListeningInfoService {
    @Resource
    private ListeningAudioInfoMapper listeningAudioInfoMapper;

    /**
     * 通过选中的关卡，水平获得听力数据（ListeningData封装）——小程序端
     * @param level
     * @param selectedRank
     * @return
     */
    public ListeningData getListeningByLevelAndRank(Integer level, Integer selectedRank){
        ListeningData listeningData = new ListeningData();
        //查询听力信息
        int adjustedSelectedRank = selectedRank - 1;
        ListeningAudioInfo listeningAudioInfo = listeningAudioInfoMapper.getAudioBySelectedRankAndLevel(adjustedSelectedRank,level);
        List<ListeningQuestionsInfo> listeningQuestionsInfoList = listeningAudioInfoMapper.getQuestionsByAudioId(listeningAudioInfo.getAudioId());
        List<ListeningChoicesInfo> listeningChoicesInfoList = listeningAudioInfoMapper.getChoicesByQuestions(listeningQuestionsInfoList);
        LevelInfo listeningLevelInfo = listeningAudioInfoMapper.getLevelInfoByLevel(level);
        //封装ListeningData
        listeningData.setAudioInfo(listeningAudioInfo);
        listeningData.setQuestions(listeningQuestionsInfoList);
        listeningData.setChoices(listeningChoicesInfoList);
        listeningData.setLevelInfo(listeningLevelInfo);
        System.out.println("11111");
        return listeningData;
    }

    public PageInfo<ListeningData> findPage(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<ListeningData> allListeningAudioInfo = this.getAllListeningData();
        System.out.println(allListeningAudioInfo);
        return PageInfo.of(allListeningAudioInfo);
    }

    /**
     * 查询所有听力信息
     * @return
     */
    public List<ListeningData> getAllListeningData(){
        // 获取所有听力音频信息
        List<ListeningAudioInfo> allListeningAudioInfo = listeningAudioInfoMapper.getALlListeningAudioInfo();
        if (allListeningAudioInfo == null || allListeningAudioInfo.isEmpty()) {
            // 处理没有听力音频信息的情况
            return null;
        }
        System.out.println(allListeningAudioInfo);
        // 创建列表用于存储所有听力信息
        List<ListeningData> allListeningData = new ArrayList<>();

        // 遍历所有听力音频信息
        for (ListeningAudioInfo audioInfo : allListeningAudioInfo) {
            // 获取音频对应的题目信息列表
            List<ListeningQuestionsInfo> listeningQuestionsInfoList = listeningAudioInfoMapper.getQuestionsByAudioId(audioInfo.getAudioId());
            if (listeningQuestionsInfoList == null || listeningQuestionsInfoList.isEmpty()) {
                // 处理题目信息列表为空的情况
                continue; // 跳过当前音频，继续下一个
            }
            // 获取题目对应的选项信息列表
            List<ListeningChoicesInfo> listeningChoicesInfoList = listeningAudioInfoMapper.getChoicesByQuestions(listeningQuestionsInfoList);
            if (listeningChoicesInfoList == null || listeningChoicesInfoList.isEmpty()) {
                // 处理选项信息列表为空的情况
                continue; // 跳过当前音频，继续下一个
            }
            // 获取音频对应的水平等级信息
            LevelInfo listeningLevelInfo = listeningAudioInfoMapper.getLevelInfoByLevel(audioInfo.getLevel());
            if (listeningLevelInfo == null) {
                // 处理水平等级信息不存在的情况
                continue; // 跳过当前音频，继续下一个
            }

            // 封装ListeningData
            ListeningData listeningData = new ListeningData();
            listeningData.setAudioInfo(audioInfo);
            listeningData.setQuestions(listeningQuestionsInfoList);
            listeningData.setChoices(listeningChoicesInfoList);
            listeningData.setLevelInfo(listeningLevelInfo);

            // 将当前听力信息添加到列表中
            allListeningData.add(listeningData);
        }
        return allListeningData;
    }

    /**
     * 根据audioId获得听力详情——web
     * @param audioId
     * @return
     */
    public ListeningData getListeningData(Integer audioId){
        ListeningData listeningData = new ListeningData();
        //查找
        ListeningAudioInfo listeningAudioInfo = listeningAudioInfoMapper.getAudioByAudioId(audioId);
        List<ListeningQuestionsInfo> questions = listeningAudioInfoMapper.getQuestionsByAudioId(audioId);
        List<ListeningChoicesInfo> choices = listeningAudioInfoMapper.getChoicesByQuestions(questions);
        LevelInfo levelInfo = listeningAudioInfoMapper.getLevelInfoByLevel(listeningAudioInfo.getLevel());
        //封装
        listeningData.setAudioInfo(listeningAudioInfo);
        listeningData.setQuestions(questions);
        listeningData.setChoices(choices);
        listeningData.setLevelInfo(levelInfo);

        System.out.println(listeningData);
        return listeningData;
    }


    public void upDataListeningData(ListeningData listeningData){
        ListeningAudioInfo audioInfo = listeningData.getAudioInfo();
        listeningAudioInfoMapper.insertListeningAudio(audioInfo);
        Integer audioId = listeningAudioInfoMapper.getAudioIdByAudioTitle(audioInfo.getAudioTitle());
        System.out.println("audioId:"+audioId);

        List<ListeningQuestionsInfo> questions = listeningData.getQuestions();
        List<ListeningChoicesInfo> choices = listeningData.getChoices();
        int count = 0;
        for (int i=0;i<questions.size();i++){
            ListeningQuestionsInfo listeningQuestionsInfo = questions.get(i);
            listeningQuestionsInfo.setAudioId(audioId);
            listeningAudioInfoMapper.insertListeningQuestion(listeningQuestionsInfo);

            Integer listeningQuestionId = listeningAudioInfoMapper.getListeningQuestionIdByPassageText(listeningQuestionsInfo.getPassageText(),listeningQuestionsInfo.getCorrectAnswer(),listeningQuestionsInfo.getAudioId());
            for (int j=0;j<4;j++){
                ListeningChoicesInfo listeningChoicesInfo = choices.get(count);
                listeningChoicesInfo.setListeningQuestionId(listeningQuestionId);
                listeningAudioInfoMapper.insertListeningChoice(listeningChoicesInfo);
                count++;
            }
        }
    }
}
