package com.eLearnPlayer.entity;

/**
 * @author
 * @create 2024-04-18 12:27
 */
public class WordsInfoWithBook {
    private Integer wordId;
    private String word;
    private Integer wordRank;
    private Integer bookId;
    private String usPhonetic;
    private String ukPhonetic;
    private String usPronounce;
    private String ukPronounce;
    private String bookName;

    public Integer getWordId() {
        return wordId;
    }

    public void setWordId(Integer wordId) {
        this.wordId = wordId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Integer getWordRank() {
        return wordRank;
    }

    public void setWordRank(Integer wordRank) {
        this.wordRank = wordRank;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getUsPhonetic() {
        return usPhonetic;
    }

    public void setUsPhonetic(String usPhonetic) {
        this.usPhonetic = usPhonetic;
    }

    public String getUkPhonetic() {
        return ukPhonetic;
    }

    public void setUkPhonetic(String ukPhonetic) {
        this.ukPhonetic = ukPhonetic;
    }

    public String getUsPronounce() {
        return usPronounce;
    }

    public void setUsPronounce(String usPronounce) {
        this.usPronounce = usPronounce;
    }

    public String getUkPronounce() {
        return ukPronounce;
    }

    public void setUkPronounce(String ukPronounce) {
        this.ukPronounce = ukPronounce;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
