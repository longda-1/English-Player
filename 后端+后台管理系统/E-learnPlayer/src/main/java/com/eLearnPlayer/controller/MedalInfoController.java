package com.eLearnPlayer.controller;

import com.eLearnPlayer.common.Result;
import com.eLearnPlayer.entity.MedalInfo;
import com.eLearnPlayer.service.MedalInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author
 * @create 2024-05-21 4:14
 */
@RestController
@RequestMapping(value = "/MedalInfo")
public class MedalInfoController {
    @Resource
    private MedalInfoService medalInfoService;

    /**
     * 获取全部medal
     * @return
     */
    @GetMapping("/getAllMedalInfo")
    public Result<List<MedalInfo>> getAllMedalInfo(){
        return Result.success(medalInfoService.getAllMedalInfo());
    }
}
