package com.eLearnPlayer.mapper;

import com.eLearnPlayer.entity.AdInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;
@Repository
public interface AdInfoMapper extends Mapper<AdInfo> {
    /**
     * 根据adName查找——web
     */
    List<AdInfo> findByAdName(@Param("adName")String adName);
    /**
     * 添加
     */
    void insertAll(@Param("adInfo")AdInfo adInfo);
    /**
     * 更新密码
     */
    void updateAdPassword(@Param("adName")String adName,@Param("newPassword")String newPassword);
}
