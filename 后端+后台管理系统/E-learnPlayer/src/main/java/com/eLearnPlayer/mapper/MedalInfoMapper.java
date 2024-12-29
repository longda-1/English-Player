package com.eLearnPlayer.mapper;

import com.eLearnPlayer.entity.MedalInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedalInfoMapper {
   //获取所有
    List<MedalInfo> getAll();
}