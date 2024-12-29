package com.eLearnPlayer.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table word_synonyms_info
 */
@Table(name = "word_synonyms_info")
public class WordSynonymsInfo {
    /**
     * Database Column Remarks:
     *   ͬ���id
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column word_synonyms_info.Word_Synonym_id
     *
     * @mbg.generated
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer wordSynonymId;

    /**
     * Database Column Remarks:
     *    ���������id
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column word_synonyms_info.Word_id
     *
     * @mbg.generated
     */
    private Integer wordId;

    /**
     * Database Column Remarks:
     *    ����
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column word_synonyms_info.Part_of_speech
     *
     * @mbg.generated
     */
    private String partOfSpeech;

    /**
     * Database Column Remarks:
     *    ����
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column word_synonyms_info.Translation
     *
     * @mbg.generated
     */
    private String translation;

    /**
     * Database Column Remarks:
     *    ͬ���
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column word_synonyms_info.Synonym
     *
     * @mbg.generated
     */
    private String synonym;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column word_synonyms_info.Word_Synonym_id
     *
     * @return the value of word_synonyms_info.Word_Synonym_id
     *
     * @mbg.generated
     */
    public Integer getWordSynonymId() {
        return wordSynonymId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column word_synonyms_info.Word_Synonym_id
     *
     * @param wordSynonymId the value for word_synonyms_info.Word_Synonym_id
     *
     * @mbg.generated
     */
    public void setWordSynonymId(Integer wordSynonymId) {
        this.wordSynonymId = wordSynonymId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column word_synonyms_info.Word_id
     *
     * @return the value of word_synonyms_info.Word_id
     *
     * @mbg.generated
     */
    public Integer getWordId() {
        return wordId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column word_synonyms_info.Word_id
     *
     * @param wordId the value for word_synonyms_info.Word_id
     *
     * @mbg.generated
     */
    public void setWordId(Integer wordId) {
        this.wordId = wordId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column word_synonyms_info.Part_of_speech
     *
     * @return the value of word_synonyms_info.Part_of_speech
     *
     * @mbg.generated
     */
    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column word_synonyms_info.Part_of_speech
     *
     * @param partOfSpeech the value for word_synonyms_info.Part_of_speech
     *
     * @mbg.generated
     */
    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech == null ? null : partOfSpeech.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column word_synonyms_info.Translation
     *
     * @return the value of word_synonyms_info.Translation
     *
     * @mbg.generated
     */
    public String getTranslation() {
        return translation;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column word_synonyms_info.Translation
     *
     * @param translation the value for word_synonyms_info.Translation
     *
     * @mbg.generated
     */
    public void setTranslation(String translation) {
        this.translation = translation == null ? null : translation.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column word_synonyms_info.Synonym
     *
     * @return the value of word_synonyms_info.Synonym
     *
     * @mbg.generated
     */
    public String getSynonym() {
        return synonym;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column word_synonyms_info.Synonym
     *
     * @param synonym the value for word_synonyms_info.Synonym
     *
     * @mbg.generated
     */
    public void setSynonym(String synonym) {
        this.synonym = synonym == null ? null : synonym.trim();
    }
}