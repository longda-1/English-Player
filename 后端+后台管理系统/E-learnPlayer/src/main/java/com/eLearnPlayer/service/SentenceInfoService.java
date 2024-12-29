package com.eLearnPlayer.service;

import com.eLearnPlayer.entity.SentenceInfoWithBLOBs;
import com.eLearnPlayer.entity.WordBooksInfo;
import com.eLearnPlayer.mapper.SentenceInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author
 * @create 2024-05-06 5:09
 */
@Service
public class SentenceInfoService {
    @Resource
    private SentenceInfoMapper sentenceInfoMapper;

    /**
     * 根据sentenceId查询例句——小程序
     * @param sentenceId
     * @return
     */
    public List<SentenceInfoWithBLOBs> getSentenceInfoById(Integer sentenceId){
        List<SentenceInfoWithBLOBs> sentenceInfoWithBLOBs = sentenceInfoMapper.findBySentenceId(sentenceId);
        System.out.println(sentenceInfoWithBLOBs.get(0));
        System.out.println(sentenceInfoWithBLOBs.size());
        return sentenceInfoWithBLOBs;
    }
}
