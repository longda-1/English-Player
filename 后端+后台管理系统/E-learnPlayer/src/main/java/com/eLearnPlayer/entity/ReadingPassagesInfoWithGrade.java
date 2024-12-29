package com.eLearnPlayer.entity;

/**
 * @author
 * @create 2024-04-20 12:38
 */
public class ReadingPassagesInfoWithGrade {
    private Integer passageId;
    private String passageTitle;
    private Integer level;
    private String passageText;
    private String grade;

    public Integer getPassageId() {
        return passageId;
    }

    public void setPassageId(Integer passageId) {
        this.passageId = passageId;
    }

    public String getPassageTitle() {
        return passageTitle;
    }

    public void setPassageTitle(String passageTitle) {
        this.passageTitle = passageTitle;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getPassageText() {
        return passageText;
    }

    public void setPassageText(String passageText) {
        this.passageText = passageText;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
