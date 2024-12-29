package com.eLearnPlayer.controller;

import com.eLearnPlayer.common.Result;
import com.eLearnPlayer.entity.WordCollectionsInfo;
import com.eLearnPlayer.service.WordCollectionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 单词收藏控制器
 * @author
 * @create 2024-05-01 3:10
 */
@RestController
@RequestMapping(value = "/wordCollection")
public class WordCollectionController {
    @Resource
    private WordCollectionService wordCollectionService;

    /**
     * 通过userId获取用户单词收藏夹表 - 小程序
     * @param userId
     * @return
     */
    @PostMapping("/getWordCollection")
    public Result<List<WordCollectionsInfo>> getWordCollection(@RequestBody Integer userId){
        //System.out.println("userId:"+userId);
        return Result.success(wordCollectionService.getWordCollection(userId));
    }
}
