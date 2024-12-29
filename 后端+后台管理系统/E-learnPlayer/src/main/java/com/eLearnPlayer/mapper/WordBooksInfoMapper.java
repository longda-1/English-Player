package com.eLearnPlayer.mapper;
import com.eLearnPlayer.entity.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordBooksInfoMapper {

    /**
     * 查询with Grade
     */
    List<WordBooksInfoWithGrade> findAllWithGrade();

    //xin
    /**
     * 根据等级查找
     */
    List<WordBooksInfo> findByLevel(@Param("level")int level);

    /**
     * 查询指定书籍中单词序号的最大值->确定一本书有多少个单词
     */
    Integer findBookSizeByBookId(Integer bookId);
    /**
     * 根据book_id查询所有
     */
    WordBooksInfo findByBookId(@Param("bookId")int bookId);
    /**
     * 查询所有
     */
    List<WordBooksInfo> findAll();
}