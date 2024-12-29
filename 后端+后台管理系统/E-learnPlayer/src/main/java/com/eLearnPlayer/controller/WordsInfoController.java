package com.eLearnPlayer.controller;

import com.eLearnPlayer.common.Result;
import com.eLearnPlayer.entity.*;
import com.eLearnPlayer.service.WordsInfoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author
 * @create 2024-04-14 22:23
 */
@RestController
@RequestMapping(value = "/WordsInfo")
public class WordsInfoController {
    @Resource
    private WordsInfoService wordsInfoService;


    /**
     * 小程序-通过Level获得->获得整个所有单词信息
     * @param selectedRequest
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping("/getWordsByLevelForALL")
    public Result<List<WordDetails>> getWordsByLevelForALL(@RequestBody SelectedRequest selectedRequest){
        int level = selectedRequest.getLevel();
        int selectedRank = selectedRequest.getSelectedRank();
        // 输出接收到的参数
        System.out.println("Received level: " + level);
        System.out.println("Received rank: " + selectedRank);

        List<WordDetails> wordList = wordsInfoService.getWordsByLevelForAll(level, selectedRank);
        System.out.println(wordList.get(0));
        System.out.println(wordList.size());
        return Result.success(wordList);
    }

    /**
     * 分页获取words——web网页
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/page/{pageNum}")
    public Result<PageInfo<WordsInfoWithBook>> page(@PathVariable Integer pageNum,
                                                    @RequestParam(defaultValue = "20") Integer pageSize){
        return Result.success(wordsInfoService.findPage(pageNum,pageSize));
    }

    /**
     * 通过wordId查询所有信息——web
     * @param wordId
     * @return
     */
    @PostMapping("/getWordByWordId")
    public Result<WordDetails> getWordByWordId(@RequestBody Integer wordId){
//        System.out.println(wordId);
        WordDetails wordDetails = wordsInfoService.getWordByWordId(wordId);
//        System.out.println(wordDetails);
        return Result.success(wordDetails);
    }
}
