package com.eLearnPlayer.entity;

import java.util.List;

/**
 * @author
 * @create 2024-04-22 15:56
 */
public class ListeningData {
    private ListeningAudioInfo audioInfo;   //音频信息
    private List<ListeningQuestionsInfo> questions; //题目列表
    private List<ListeningChoicesInfo> choices;     //选项列表
    private LevelInfo levelInfo;    //水平等级信息

    public ListeningAudioInfo getAudioInfo() {
        return audioInfo;
    }

    public void setAudioInfo(ListeningAudioInfo audioInfo) {
        this.audioInfo = audioInfo;
    }

    public List<ListeningQuestionsInfo> getQuestions() {
        return questions;
    }

    public void setQuestions(List<ListeningQuestionsInfo> questions) {
        this.questions = questions;
    }

    public List<ListeningChoicesInfo> getChoices() {
        return choices;
    }

    public void setChoices(List<ListeningChoicesInfo> choices) {
        this.choices = choices;
    }

    public LevelInfo getLevelInfo() {
        return levelInfo;
    }

    public void setLevelInfo(LevelInfo levelInfo) {
        this.levelInfo = levelInfo;
    }
}
