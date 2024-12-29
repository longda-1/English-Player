package com.eLearnPlayer.service;

import cn.hutool.core.collection.CollectionUtil;
import com.eLearnPlayer.common.ResultCode;
import com.eLearnPlayer.entity.*;
import com.eLearnPlayer.exception.CustomException;
import com.eLearnPlayer.mapper.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @create 2024-05-12 3:05
 */
@Service
public class WordQuestionInfoService {
    @Resource
    private WordQuestionInfoMapper wordQuestionInfoMapper;
    @Resource
    private WordChoicesInfoMapper wordChoicesInfoMapper;
    @Resource
    private WordBooksInfoMapper wordBooksInfoMapper;
    @Resource
    private LevelInfoMapper levelInfoMapper;
    @Resource
    private WordsInfoMapper wordsInfoMapper;

    /**
     * 根据用户等级和关卡数获取单词选择题列表-小程序
     * @param rank
     * @return
     */
    public List<WordQuestionData> getWordQuestionsByLevelForALL(int rank){
        List<WordQuestionData> wordQuestions = new ArrayList<>();
        //当前关卡对应的起始单词序号->rank前面关卡单词数加起来的（总的）
        int startWordRank = (rank - 1)*10 +1;
        int count = 0; // 记录已经查询到的单词数量
        for(;;startWordRank++){
            WordQuestionData wordQuestionData = new WordQuestionData();
            List<WordQuestionInfo> wordQuestionInfoList = wordQuestionInfoMapper.findAllByWordQuestionId(startWordRank);
            System.out.println("wordQuestionInfoList:" + wordQuestionInfoList);
            for (int i = 0; i < wordQuestionInfoList.size(); i++) {
                List<WordChoicesInfo> wordChoicesInfoList = wordChoicesInfoMapper.findAllByWordQuestionId(wordQuestionInfoList.get(i).getWordQuestionId());
                System.out.println("wordChoicesInfoList:" + wordChoicesInfoList);
                if(wordChoicesInfoList.isEmpty()){
                    continue;
                }else {
                    //封装
                    wordQuestionData.setWordQuestionInfo(wordQuestionInfoList.get(i));
                    wordQuestionData.setWordChoicesInfo(wordChoicesInfoList);
                    System.out.println("wordQuestionData:" + wordQuestionData);
                    //放入list
                    wordQuestions.add(wordQuestionData);
                    count++;
                    System.out.println("wordQuestions:" + wordQuestions);
                    System.out.println("count:" + count);
                }
            }
            if (count >= 10) {
                // 如果已经查询到了足够数量，则停止查询
                break;
            }

        }
        return wordQuestions;
    }

}
