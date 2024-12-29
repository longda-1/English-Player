package com.eLearnPlayer.service;

import com.eLearnPlayer.entity.WordCollectionsInfo;
import com.eLearnPlayer.mapper.WordCollectionsInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author
 * @create 2024-05-01 3:11
 */
@Service
public class WordCollectionService {
    @Resource
    private WordCollectionsInfoMapper wordCollectionsInfoMapper;

    /**
     * 通过userId查找所有WordCollection
     * @param userId
     * @return
     */
    public List<WordCollectionsInfo> getWordCollection(Integer userId){
        List<WordCollectionsInfo> wordCollectionsInfoList = wordCollectionsInfoMapper.findAllByUserId(userId);
//        System.out.println(wordCollectionsInfoList.get(0));
//        System.out.println(wordCollectionsInfoList.size());
        return wordCollectionsInfoList;
    }

}
