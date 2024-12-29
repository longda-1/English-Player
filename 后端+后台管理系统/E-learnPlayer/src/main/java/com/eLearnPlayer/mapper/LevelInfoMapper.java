package com.eLearnPlayer.mapper;

import com.eLearnPlayer.entity.LevelInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LevelInfoMapper {
    List<LevelInfo> findAll();  //查询所有
    List<LevelInfo> findByGrade(String grade);  //通过名称查询
    int insertGrade(@Param("grade") String grade);   //插入水平名
    /**
     * 根据Level查询等级
     */
    LevelInfo findByLevel(Integer level);
}