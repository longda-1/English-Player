package com.eLearnPlayer.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table words_info
 */
@Table(name = "words_info")
public class WordsInfo {
    /**
     * Database Column Remarks:
     *   ����id
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column words_info.Word_id
     *
     * @mbg.generated
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer wordId;

    /**
     * Database Column Remarks:
     *    ����
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column words_info.Word
     *
     * @mbg.generated
     */
    private String word;

    /**
     * Database Column Remarks:
     *    �������
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column words_info.Word_rank
     *
     * @mbg.generated
     */
    private Integer wordRank;

    /**
     * Database Column Remarks:
     *    �����������id
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column words_info.Book_id
     *
     * @mbg.generated
     */
    private Integer bookId;

    /**
     * Database Column Remarks:
     *    ��ʽ����
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column words_info.Us_phonetic
     *
     * @mbg.generated
     */
    private String usPhonetic;

    /**
     * Database Column Remarks:
     *    Ӣʽ����
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column words_info.Uk_phonetic
     *
     * @mbg.generated
     */
    private String ukPhonetic;

    /**
     * Database Column Remarks:
     *    ��ʽ����
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column words_info.Us_pronounce
     *
     * @mbg.generated
     */
    private String usPronounce;

    /**
     * Database Column Remarks:
     *    Ӣʽ����
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column words_info.Uk_pronounce
     *
     * @mbg.generated
     */
    private String ukPronounce;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column words_info.Word_id
     *
     * @return the value of words_info.Word_id
     *
     * @mbg.generated
     */
    public Integer getWordId() {
        return wordId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column words_info.Word_id
     *
     * @param wordId the value for words_info.Word_id
     *
     * @mbg.generated
     */
    public void setWordId(Integer wordId) {
        this.wordId = wordId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column words_info.Word
     *
     * @return the value of words_info.Word
     *
     * @mbg.generated
     */
    public String getWord() {
        return word;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column words_info.Word
     *
     * @param word the value for words_info.Word
     *
     * @mbg.generated
     */
    public void setWord(String word) {
        this.word = word == null ? null : word.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column words_info.Word_rank
     *
     * @return the value of words_info.Word_rank
     *
     * @mbg.generated
     */
    public Integer getWordRank() {
        return wordRank;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column words_info.Word_rank
     *
     * @param wordRank the value for words_info.Word_rank
     *
     * @mbg.generated
     */
    public void setWordRank(Integer wordRank) {
        this.wordRank = wordRank;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column words_info.Book_id
     *
     * @return the value of words_info.Book_id
     *
     * @mbg.generated
     */
    public Integer getBookId() {
        return bookId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column words_info.Book_id
     *
     * @param bookId the value for words_info.Book_id
     *
     * @mbg.generated
     */
    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column words_info.Us_phonetic
     *
     * @return the value of words_info.Us_phonetic
     *
     * @mbg.generated
     */
    public String getUsPhonetic() {
        return usPhonetic;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column words_info.Us_phonetic
     *
     * @param usPhonetic the value for words_info.Us_phonetic
     *
     * @mbg.generated
     */
    public void setUsPhonetic(String usPhonetic) {
        this.usPhonetic = usPhonetic == null ? null : usPhonetic.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column words_info.Uk_phonetic
     *
     * @return the value of words_info.Uk_phonetic
     *
     * @mbg.generated
     */
    public String getUkPhonetic() {
        return ukPhonetic;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column words_info.Uk_phonetic
     *
     * @param ukPhonetic the value for words_info.Uk_phonetic
     *
     * @mbg.generated
     */
    public void setUkPhonetic(String ukPhonetic) {
        this.ukPhonetic = ukPhonetic == null ? null : ukPhonetic.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column words_info.Us_pronounce
     *
     * @return the value of words_info.Us_pronounce
     *
     * @mbg.generated
     */
    public String getUsPronounce() {
        return usPronounce;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column words_info.Us_pronounce
     *
     * @param usPronounce the value for words_info.Us_pronounce
     *
     * @mbg.generated
     */
    public void setUsPronounce(String usPronounce) {
        this.usPronounce = usPronounce == null ? null : usPronounce.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column words_info.Uk_pronounce
     *
     * @return the value of words_info.Uk_pronounce
     *
     * @mbg.generated
     */
    public String getUkPronounce() {
        return ukPronounce;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column words_info.Uk_pronounce
     *
     * @param ukPronounce the value for words_info.Uk_pronounce
     *
     * @mbg.generated
     */
    public void setUkPronounce(String ukPronounce) {
        this.ukPronounce = ukPronounce == null ? null : ukPronounce.trim();
    }
}