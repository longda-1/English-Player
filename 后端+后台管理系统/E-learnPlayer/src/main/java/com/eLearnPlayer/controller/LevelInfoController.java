package com.eLearnPlayer.controller;

import com.eLearnPlayer.common.Result;
import com.eLearnPlayer.entity.LevelInfo;
import com.eLearnPlayer.service.LevelInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户英语水平增删改查器
 * @author
 * @create 2024-04-08 17:37
 */
@RestController
@RequestMapping(value = "/LevelInfo")
public class LevelInfoController {
    @Resource
    private LevelInfoService levelInfoService;

    /**
     * 获取水平表
     * @return
     */
    @GetMapping("/getAllLevels")
    public List<LevelInfo> getAllLevels(){
        return levelInfoService.getAllLevels();
    }

    /**
     * 添加水平表
     * @param levelInfo
     */
    @PostMapping("/addLevel")
    public Result addLevel(@RequestBody LevelInfo levelInfo){
        return levelInfoService.addLevel(levelInfo);
    }



}
