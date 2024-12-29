package com.eLearnPlayer.entity;

import java.util.List;

/**
 * 单词基本信息
 * @author
 * @create 2024-04-03 14:17
 */
public class WordDetails {
    private WordsInfo wordsInfo;                    //单词基本信息
    private WordBooksInfo wordBooksInfo;            //单词书信息
    private List<SentenceInfo> sentenceInfoList;    //例句信息列表
    private List<WordSynonymsInfo> wordSynonymsInfoList;   //同义词信息列表
    private List<WordPhrasesInfo> wordPhrasesInfoList; // 短语信息列表
    private List<WordsRootInfo> wordsRootInfoList; // 同根词信息列表
    private LevelInfo levelInfo; // 水平等级信息

    public WordsInfo getWordsInfo() {
        return wordsInfo;
    }

    public void setWordsInfo(WordsInfo wordsInfo) {
        this.wordsInfo = wordsInfo;
    }

    public WordBooksInfo getWordBooksInfo() {
        return wordBooksInfo;
    }

    public void setWordBooksInfo(WordBooksInfo wordBooksInfo) {
        this.wordBooksInfo = wordBooksInfo;
    }

    public List<SentenceInfo> getSentenceInfoList() {
        return sentenceInfoList;
    }

    public void setSentenceInfoList(List<SentenceInfo> sentenceInfoList) {
        this.sentenceInfoList = sentenceInfoList;
    }

    public List<WordSynonymsInfo> getWordSynonymsInfoList() {
        return wordSynonymsInfoList;
    }

    public void setWordSynonymsInfoList(List<WordSynonymsInfo> wordSynonymsInfoList) {
        this.wordSynonymsInfoList = wordSynonymsInfoList;
    }

    public List<WordPhrasesInfo> getWordPhrasesInfoList() {
        return wordPhrasesInfoList;
    }

    public void setWordPhrasesInfoList(List<WordPhrasesInfo> wordPhrasesInfoList) {
        this.wordPhrasesInfoList = wordPhrasesInfoList;
    }

    public List<WordsRootInfo> getWordsRootInfoList() {
        return wordsRootInfoList;
    }

    public void setWordsRootInfoList(List<WordsRootInfo> wordsRootInfoList) {
        this.wordsRootInfoList = wordsRootInfoList;
    }

    public LevelInfo getLevelInfo() {
        return levelInfo;
    }

    public void setLevelInfo(LevelInfo levelInfo) {
        this.levelInfo = levelInfo;
    }
}
