package com.eLearnPlayer.controller;

import com.eLearnPlayer.common.Result;
import com.eLearnPlayer.entity.SentenceInfoWithBLOBs;
import com.eLearnPlayer.service.SentenceInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author
 * @create 2024-05-06 5:09
 */
@RestController
@RequestMapping(value = "/SentenceInfo")
public class SentenceInfoController {
    @Resource
    private SentenceInfoService sentenceInfoService;

    /**
     * 根据sentenceId查询例句——小程序
     * @param sentenceId
     * @return
     */
    @GetMapping("/getSentenceById")
    public Result<List<SentenceInfoWithBLOBs>> getSentenceById(@RequestParam Integer sentenceId){
        System.out.println(sentenceId);
        List<SentenceInfoWithBLOBs> sentenceInfoWithBLOBsList = sentenceInfoService.getSentenceInfoById(sentenceId);
        System.out.println(sentenceInfoWithBLOBsList.get(0));
        System.out.println(sentenceInfoWithBLOBsList.size());
        return Result.success(sentenceInfoWithBLOBsList);
    }
}
