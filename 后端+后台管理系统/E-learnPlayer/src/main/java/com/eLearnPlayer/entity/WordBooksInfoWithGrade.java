package com.eLearnPlayer.entity;

/**
 * WordBooksInfo+Grade
 * @author
 * @create 2024-04-18 10:48
 */
public class WordBooksInfoWithGrade {
    private Integer bookId;
    private String bookName;
    private Integer level;
    private String grade;

    public String getBookName() {
        return bookName;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
