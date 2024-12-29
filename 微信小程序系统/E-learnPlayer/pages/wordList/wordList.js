// pages/wordList/wordList.js
import { request } from '../../request/index.js';
Page({

  /**
   * 页面的初始数据
   */
  data: {
    selectedRank: 0, // 选择的关卡
    userData: {},   //用户信息
    wordList: [], // 单词列表
    currentWord: {} // 当前单词信息
  },

  /**
   * 生命周期函数--监听页面加载
   */
  /**
   * 获取用户信息和选择关卡，从后端获取单词数据
   */
  onLoad:function(options) {
    // 从本地缓存中获取用户信息和选择的关卡
    const userData = wx.getStorageSync('userData');
    const selectedRank = wx.getStorageSync('selectedRank');
    if (userData) {
      if(userData.userInfo.head !== null){
        if (!userData.userInfo.head.startsWith("http://localhost:8000/")) {
          // 处理头像 URL，将 `\` 替换为 `/`
         userData.userInfo.head = userData.userInfo.head.replace(/\\/g, '/');
         userData.userInfo.head = 'http://localhost:8000/' + userData.userInfo.head;
        }
      } else{
        userData.userInfo.head = '../../icons/my.png';
      }
    userData.userInfo.gold = userData.userInfo.gold-1;
     this.setData({ userData,selectedRank });
     wx.setStorageSync('userData', userData);
    }
     console.log('1-userData', userData);
    // console.log('selectedRank', selectedRank);
    // console.log("this.data:",this.data);
    // 向后端请求获取单词数据
    this.fetchWords();
  },

  /**
   *  请求后端获取单词数据
   */
  fetchWords: function () {
    /// 从页面数据中获取选择的关卡和用户等级
    const selectedRank = this.data.selectedRank.level; // 获取选择的关卡
    const level = this.data.userData.levelInfo.level; // 获取用户等级
    console.log('selectedRank0' ,selectedRank);
    console.log('level' ,level);
    // 向后端发送请求获取对应关卡的单词数据
    request({
      url: '/WordsInfo/getWordsByLevelForALL',
      method: 'POST',
      data: {
        level: level,
        selectedRank: selectedRank,
      }
    }).then((res) => {
        console.log("后端返回数据:", res);
        let words = res.data;// 将获取到的单词数据存储到 words 中
        wx.setStorageSync('wordList', words);  //将单词存入缓存
        console.log("words:",words);
        this.setData({ wordList : words }); // 更新数据
        // console.log("words1111:",words);
        // console.log("wordlist2222:",this.data.wordList);
      }).catch((err) => {
        console.error('获取单词数据失败：', err);
      }).finally(() => {  //无论成功或者失败都会执行的操作
        console.log('请求完成');
      });
  },
  /**
   * 点击单词显示详细信息弹窗
   * @param {*} event 
   */
  showWordDetail(event) {
    console.log("event",event);
    const { index } = event.currentTarget.dataset; // 获取当前单词在列表中的索引
    console.log('点击的单词在列表中的序号:', index);
    const currentWord = this.data.wordList[index]; // 获取点击的单词信息
    console.log('点击的单词currentWord:', currentWord);
    if(currentWord){
      // 将 currentWord 存入本地缓存
      wx.setStorageSync('currentWord', currentWord);
      // 设置弹窗显示并更新当前单词信息
      this.setData({ currentWord});
      // 跳转到 currentWord 页面
      wx.navigateTo({
        url: '/pages/currentWord/currentWord', // 替换为你的 currentWord 页面路径
        success: function (res) {
          console.log("跳转成功");
        },
        fail: function (err) {
          console.error("跳转失败", err);
        }
      });

    } else{
      console.error('未找到单词信息');
    }
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
  /**
   * 倒计时
   */
  startWordChallenge() {
    const app = getApp();
    app.stopBackgroundAudio();
    this.changeAndPlayNewBgm();
    console.log('Starting countdown');
    this.setData({ showCountdown: true }); // 显示倒计时组件
  },
  /**
   * 倒计时结束跳转页面
   */
  onCountdownEnd() {
    const app = getApp();
    app.stopBackgroundAudio();
    // 倒计时结束后跳转到 wordChallenge 页面
    wx.navigateTo({
      url: '/pages/wordChallenge/wordChallenge'
    });
  },
  // 更改并播放新的背景音乐
  changeAndPlayNewBgm() {
    const app = getApp();
    const newBgmUrl = './bgm/countdownBGM.mp3'; // 新的背景音乐路径
    app.setBgmAudioUrl(newBgmUrl);
    //app.initBackgroundAudio(); // 初始化并播放新的背景音乐
  },
  // 页面显示时触发
  // onShow: function() {
  //   // 调用刷新数据的函数
  //   this.refreshData();
  // },

  // // 刷新数据函数
  // refreshData: function() {
  //   this.onLoad();
  // }



});