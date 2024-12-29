package com.eLearnPlayer.service;

import com.eLearnPlayer.entity.MedalInfo;
import com.eLearnPlayer.mapper.MedalInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author
 * @create 2024-05-21 4:15
 */
@Service
public class MedalInfoService {
    @Resource
    private MedalInfoMapper medalInfoMapper;

    /**
     * 获取全部medal
     * @return
     */
    public List<MedalInfo> getAllMedalInfo(){
        List<MedalInfo> medalInfoList = medalInfoMapper.getAll();
        System.out.println(medalInfoList);
        return medalInfoList;
    }
}
