package com.eLearnPlayer.service;

import com.eLearnPlayer.common.Result;
import com.eLearnPlayer.entity.LevelInfo;
import com.eLearnPlayer.mapper.LevelInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 查询
 * @author
 * @create 2024-04-09 0:53
 */
@Service
public class LevelInfoService {
    @Resource
    private LevelInfoMapper levelInfoMapper;

    /**
     * 查询所有数据
     * @return
     */
    public List<LevelInfo> getAllLevels(){
        List<LevelInfo> list = levelInfoMapper.findAll();
        //测试
        //System.out.println("level:"+list);
        return list;
    }

    public Result addLevel(LevelInfo levelInfo){
        // 检查待插入的 Grade 是否已存在于数据库中
        List existingLevel = levelInfoMapper.findByGrade(levelInfo.getGrade());
        if (existingLevel == null || !(existingLevel.isEmpty())) {
            // 如果已存在，则拒绝插入并返回错误信息
            return Result.error();
        } else {
            // 如果不存在，则执行插入操作
            levelInfoMapper.insertGrade(levelInfo.getGrade());
            return Result.success();
        }
    }


}
