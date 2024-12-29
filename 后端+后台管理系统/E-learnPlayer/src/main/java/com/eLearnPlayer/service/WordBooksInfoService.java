package com.eLearnPlayer.service;

import com.eLearnPlayer.common.Result;
import com.eLearnPlayer.entity.UserInfo;
import com.eLearnPlayer.entity.WordBooksInfo;
import com.eLearnPlayer.entity.WordBooksInfoWithGrade;
import com.eLearnPlayer.mapper.WordBooksInfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 查询方法
 * @author
 * @create 2024-04-09 3:02
 */
@Service
public class WordBooksInfoService {
    @Resource
    private WordBooksInfoMapper wordBooksInfoMapper;

    /**
     * 分页查询单词书列表-web
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<WordBooksInfoWithGrade> findPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<WordBooksInfoWithGrade> list = wordBooksInfoMapper.findAllWithGrade();
        System.out.println(list);
        return PageInfo.of(list);
    }


}
