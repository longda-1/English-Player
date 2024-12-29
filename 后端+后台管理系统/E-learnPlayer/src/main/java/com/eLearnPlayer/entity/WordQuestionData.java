package com.eLearnPlayer.entity;

import java.util.List;

/**
 * 单词问题数据
 * @author
 * @create 2024-05-12 3:22
 */
public class WordQuestionData {
    private WordQuestionInfo wordQuestionInfo;
    private List<WordChoicesInfo> wordChoicesInfo;

    public WordQuestionInfo getWordQuestionInfo() {
        return wordQuestionInfo;
    }

    public void setWordQuestionInfo(WordQuestionInfo wordQuestionInfo) {
        this.wordQuestionInfo = wordQuestionInfo;
    }

    public List<WordChoicesInfo> getWordChoicesInfo() {
        return wordChoicesInfo;
    }

    public void setWordChoicesInfo(List<WordChoicesInfo> wordChoicesInfo) {
        this.wordChoicesInfo = wordChoicesInfo;
    }
}
