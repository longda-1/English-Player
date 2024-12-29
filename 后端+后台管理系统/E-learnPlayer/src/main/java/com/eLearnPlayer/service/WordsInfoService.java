package com.eLearnPlayer.service;

import cn.hutool.core.collection.CollectionUtil;
import com.eLearnPlayer.common.ResultCode;
import com.eLearnPlayer.entity.*;
import com.eLearnPlayer.exception.CustomException;
import com.eLearnPlayer.mapper.LevelInfoMapper;
import com.eLearnPlayer.mapper.WordBooksInfoMapper;
import com.eLearnPlayer.mapper.WordsInfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @create 2024-04-14 22:25
 */
@Service
public class WordsInfoService {
    @Resource
    private WordsInfoMapper wordsInfoMapper;
    @Resource
    private WordBooksInfoMapper wordBooksInfoMapper;
    @Resource
    private LevelInfoMapper levelInfoMapper;

    /**
     * 根据用户等级和关卡数获取单词列表-小程序
     * @param level
     * @param rank
     * @return
     */
    public List<WordDetails> getWordsByLevelForAll(int level,int rank){
        //根据用户等级从书籍表中查询对应的书籍
        List<WordBooksInfo> wordBooksInfoList = wordBooksInfoMapper.findByLevel(level);
        if (CollectionUtil.isEmpty(wordBooksInfoList)){
            //处理未找到对应书籍的情况
            throw new CustomException(ResultCode.BOOK_EXIST_ERROR);
        }
        List<WordDetails> words = new ArrayList<>();
        //当前关卡对应的起始单词序号->rank前面关卡单词数加起来的（总的）
        int startWordRank = (rank - 1)*10 +1;
        int count = 0; // 记录已经查询到的单词数量

        //对 wordBooksInfoList 中的每个元素都执行循环体中的操作。
        //使用 book 这个变量来依次代表列表中的每一本书籍，然后对每本书籍执行循环体中的操作。
        for (WordBooksInfo book : wordBooksInfoList) {
            // 查询当前书籍中最大的单词序号
            Integer maxRank = wordBooksInfoMapper.findBookSizeByBookId(book.getBookId());
            if (maxRank == null){
                //处理当前书籍无单词的情况
                continue;
            }
            //若当前关卡所用提取的单词已经过了这个单词书
            if (startWordRank >= maxRank){
                startWordRank -= maxRank;
                continue;
            }

            //计算当前书籍下需要查询的单词数量
            //maxRank - startWordRank + 1   这本书还剩下多少个单词没查询
            //min() 剩的多->10-count，剩的不够连接下一本书-maxRank - startWordRank + 1 (0)
            int remainingWords = Math.min(10-count,maxRank - startWordRank + 1);
            if (remainingWords <= 0 ){
                //若当前书籍已经查询完毕，则继续下一本书
                continue;
            }
            // 根据书籍和单词 ID 范围从单词表中查询单词列表
            //循环
            int endWordRank = startWordRank + remainingWords - 1;
            for (;startWordRank<=endWordRank;startWordRank++){
                WordDetails wordDetails = new WordDetails();
                WordsInfo wordsInfo = wordsInfoMapper.findWordByBookIdAndWordRank(book.getBookId(), startWordRank);
                WordBooksInfo wordBooksInfo = wordBooksInfoMapper.findByBookId(book.getBookId());
                List<SentenceInfo> sentenceInfoList = wordsInfoMapper.findSentenceByWordId(wordsInfo.getWordId());
                List<WordSynonymsInfo> wordSynonymsInfoList = wordsInfoMapper.findSynonymsByWordId(wordsInfo.getWordId());
                List<WordPhrasesInfo> wordPhrasesInfoList = wordsInfoMapper.findPhrasesByWordId(wordsInfo.getWordId());
                List<WordsRootInfo> wordsRootInfoList = wordsInfoMapper.findRootByWordId(wordsInfo.getWordId());
                LevelInfo levelInfo = levelInfoMapper.findByLevel(wordBooksInfo.getLevel());
                //封装
                wordDetails.setWordsInfo(wordsInfo);
                wordDetails.setWordBooksInfo(wordBooksInfo);
                wordDetails.setSentenceInfoList(sentenceInfoList);
                wordDetails.setWordSynonymsInfoList(wordSynonymsInfoList);
                wordDetails.setWordPhrasesInfoList(wordPhrasesInfoList);
                wordDetails.setWordsRootInfoList(wordsRootInfoList);
                wordDetails.setLevelInfo(levelInfo);
                if(wordDetails.getWordSynonymsInfoList().isEmpty()){
                    continue;
                }else {
                    //放入list
                    words.add(wordDetails);
                    count ++;
                    System.out.println("wordDetails:"+wordDetails);
                    System.out.println("count:"+count);
                }

            }
            if (count >= 10) {
                // 如果已经查询到了足够数量的单词，则停止查询
                break;
            }
        }

        return words;
    }

    /**
     * 分页查询单词列表-web
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<WordsInfoWithBook> findPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<WordsInfoWithBook> list = wordsInfoMapper.findAllWithBook();
        System.out.println(list);
        return PageInfo.of(list);
    }

    /**
     * 通过wordId获取单词-web
     * @param wordId
     * @return
     */
    public WordDetails getWordByWordId(Integer wordId){
        WordDetails wordDetails = new WordDetails();
        WordsInfo wordsInfo = wordsInfoMapper.findByWordId(wordId);
        WordBooksInfo wordBooksInfo = wordBooksInfoMapper.findByBookId(wordsInfo.getBookId());
        List<SentenceInfo> sentenceInfoList = wordsInfoMapper.findSentenceByWordId(wordId);
        List<WordSynonymsInfo> wordSynonymsInfoList = wordsInfoMapper.findSynonymsByWordId(wordId);
        List<WordPhrasesInfo> wordPhrasesInfoList = wordsInfoMapper.findPhrasesByWordId(wordId);
        List<WordsRootInfo> wordsRootInfoList = wordsInfoMapper.findRootByWordId(wordId);
        LevelInfo levelInfo01 = levelInfoMapper.findByLevel(wordBooksInfo.getLevel());
        //封装
        wordDetails.setWordsInfo(wordsInfo);
        wordDetails.setWordBooksInfo(wordBooksInfo);
        wordDetails.setSentenceInfoList(sentenceInfoList);
        wordDetails.setWordSynonymsInfoList(wordSynonymsInfoList);
        wordDetails.setWordPhrasesInfoList(wordPhrasesInfoList);
        wordDetails.setWordsRootInfoList(wordsRootInfoList);
        wordDetails.setLevelInfo(levelInfo01);
        System.out.println("______");
        System.out.println(wordDetails);
        return wordDetails;
    }
}
