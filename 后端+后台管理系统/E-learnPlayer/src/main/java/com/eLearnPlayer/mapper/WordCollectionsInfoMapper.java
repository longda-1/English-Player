package com.eLearnPlayer.mapper;

import com.eLearnPlayer.entity.WordCollectionsInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordCollectionsInfoMapper {
    /**
     * 通过userId查询所有
     */
    List<WordCollectionsInfo> findAllByUserId(Integer userId);
}