// pages/currentWord/currentWord.js
import { request } from '../../request/index.js';
Page({

  /**
   * 页面的初始数据
   */
  data: {
    currentWord: {},
    exampleSentences:{}
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // 从本地缓存中获取 currentWord
    const currentWord = wx.getStorageSync('currentWord');
    console.log(currentWord);
    // 设置页面标题为 currentWord 的单词
    wx.setNavigationBarTitle({
      title: currentWord.wordsInfo.word
    });
    this.setData({currentWord});
    // 加载例句信息
    this.loadExampleSentences(currentWord.sentenceInfoList);
  },
  /**
   * 加载例句信息
   */
  loadExampleSentences: function (sentenceInfoList) {
    // 遍历每个例句的 sentenceId，发送请求获取例句信息
    sentenceInfoList.forEach(sentence => {
      console.log("sentence:",sentence);
      const sentenceId = sentence.sentenceId;
      console.log("sentenceId:",sentenceId);
      request({
        url: '/SentenceInfo/getSentenceById',
        method: 'GET',
        data: {
          sentenceId: sentenceId // 将 sentenceId 包含在请求数据中
        }
      }).then(res => {
        // 请求成功，将例句信息添加到 exampleSentences 数组中
        const exampleSentence = res.data; // 假设返回的是单个例句信息
        // 在页面数据中以 sentenceId 作为键，保存每个 sentenceId 对应的例句信息
        const exampleSentences = this.data.exampleSentences || {};
        exampleSentences[sentenceId] = exampleSentence;
        this.setData({ exampleSentences });
        console.log("exampleSentences:",exampleSentences);
      }).catch(err => {
        console.error('获取例句信息失败：', err);
      });
    });
  },

/**
   * 播放单词发音
   * @param {*} event 
   */
  playPronunciation(event) {
    const { word, type } = event.currentTarget.dataset; // 获取单词和发音类型
    const audioType = type === '1' ? '1' : '2'; // 将类型转换为字符串
    const audioUrl = `https://dict.youdao.com/dictvoice?audio=${word}&type=${audioType}`; // 构建发音链接
    wx.playBackgroundAudio({ // 使用微信小程序的音频播放接口
        dataUrl: audioUrl,
        title: word,
    });
  },
})