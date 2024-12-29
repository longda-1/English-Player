package com.eLearnPlayer.controller;

import com.eLearnPlayer.common.Result;
import com.eLearnPlayer.entity.UserInfo;
import com.eLearnPlayer.entity.WordBooksInfo;
import com.eLearnPlayer.entity.WordBooksInfoWithGrade;
import com.eLearnPlayer.entity.WordDetails;
import com.eLearnPlayer.service.WordBooksInfoService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author
 * @create 2024-04-09 3:00
 */
@RestController
@RequestMapping(value = "/WordBooksInfo")
public class WordBooksInfoController {
    @Resource
    private WordBooksInfoService wordBooksInfoService;

    /**
     * 分页获取wordBooks——web网页
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/page/{pageNum}")
    public Result<PageInfo<WordBooksInfoWithGrade>> page(@PathVariable Integer pageNum,
                                                         @RequestParam(defaultValue = "10") Integer pageSize){
        return Result.success(wordBooksInfoService.findPage(pageNum,pageSize));
    }

//    /**
//     * 搜索书=web
//     * @param bookName
//     * @return
//     */
//    @GetMapping("searchByBookName")
//    public Result<PageInfo<WordBooksInfoWithGrade>> searchWordBooks(@RequestParam String bookName){
//
//    }
}
