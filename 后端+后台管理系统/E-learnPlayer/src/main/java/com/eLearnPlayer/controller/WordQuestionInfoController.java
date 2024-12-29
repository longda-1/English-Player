package com.eLearnPlayer.controller;

import com.eLearnPlayer.common.Result;
import com.eLearnPlayer.entity.SelectedRequest;
import com.eLearnPlayer.entity.WordQuestionData;
import com.eLearnPlayer.entity.WordQuestionInfo;
import com.eLearnPlayer.service.WordQuestionInfoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author
 * @create 2024-05-12 3:02
 */
@RestController
@RequestMapping(value = "/WordQuestionInfo")
public class WordQuestionInfoController {
    @Resource
    private WordQuestionInfoService wordQuestionInfoService;

    /**
     * 小程序-通过Level获得->获得整个所有单词选择题信息
     * @param selectedRequest
     * @return
     */
    @PostMapping("/getWordQuestionsByLevelForALL")
    public Result<List<WordQuestionData>> getWordQuestionsByLevelForALL(@RequestBody SelectedRequest selectedRequest){
        int level = selectedRequest.getLevel();
        int selectedRank = selectedRequest.getSelectedRank();
        // 输出接收到的参数
//        System.out.println("Received level: " + level);
        System.out.println("Received rank: " + selectedRank);

        List<WordQuestionData> wordQuestionDataList = wordQuestionInfoService.getWordQuestionsByLevelForALL(selectedRank);
        System.out.println(wordQuestionDataList.get(0));
        System.out.println(wordQuestionDataList.size());
        return Result.success(wordQuestionDataList);
    }
}
