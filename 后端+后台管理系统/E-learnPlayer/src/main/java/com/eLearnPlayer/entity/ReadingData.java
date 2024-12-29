package com.eLearnPlayer.entity;

import java.util.List;

/**
 * 阅读数据
 * @author
 * @create 2024-04-18 2:43
 */
public class ReadingData {
    private ReadingPassagesInfo passage; // 文章信息
    private List<ReadingQuestionsInfo> questions; // 题目列表
    private List<ReadingChoicesInfo> choices; // 选项列表
    private LevelInfo levelInfo; // 水平级别信息

    public LevelInfo getLevelInfo() {
        return levelInfo;
    }

    public void setLevelInfo(LevelInfo levelInfo) {
        this.levelInfo = levelInfo;
    }

    public ReadingPassagesInfo getPassage() {
        return passage;
    }

    public void setPassage(ReadingPassagesInfo passage) {
        this.passage = passage;
    }

    public List<ReadingQuestionsInfo> getQuestions() {
        return questions;
    }

    public void setQuestions(List<ReadingQuestionsInfo> questions) {
        this.questions = questions;
    }

    public List<ReadingChoicesInfo> getChoices() {
        return choices;
    }

    public void setChoices(List<ReadingChoicesInfo> choices) {
        this.choices = choices;
    }
}
