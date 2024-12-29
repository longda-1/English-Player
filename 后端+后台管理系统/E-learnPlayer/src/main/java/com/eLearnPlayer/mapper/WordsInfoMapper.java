package com.eLearnPlayer.mapper;

import com.eLearnPlayer.entity.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordsInfoMapper {
    /**
     * 查询with Book
     */
    List<WordsInfoWithBook> findAllWithBook();

    //xin
    /**
     * 通过Book_id和Word_rank查询
     */
    WordsInfo findWordByBookIdAndWordRank(@Param("bookId") Integer bookId, @Param("wordRank") Integer wordRank);
    /**
     * 通过word_id查询
     */
    WordsInfo findByWordId(@Param("wordId") Integer wordId);
    /**
     * 通过word_id查找表sentence_info(例句表)
     */
    List<SentenceInfo> findSentenceByWordId(Integer wordId);
    /**
     * 通过word_id查找表word_synonyms_info(同义词表)
     */
    List<WordSynonymsInfo> findSynonymsByWordId(Integer wordId);
    /**
     * 通过word_id查找表word_phrases_info(短语表)
     */
    List<WordPhrasesInfo> findPhrasesByWordId(Integer wordId);
    /**
     * 通过word_id查找表words_root_info(同根词表)
     */
    List<WordsRootInfo> findRootByWordId(Integer wordId);

}