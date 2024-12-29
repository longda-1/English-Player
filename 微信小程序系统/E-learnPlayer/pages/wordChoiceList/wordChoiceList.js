// pages/wordChoiceList/wordChoiceList.js
import { request } from '../../request/index.js';
Page({

  /**
   * 页面的初始数据
   */
  data: {
    selectedRank: 0, // 选择的关卡
    userData: {},   //用户信息
    wordQuestions: [], // 单词选择题列表
    currentIndex: 0, // 当前题目索引
    currentQuestion: {}, // 当前显示的题目
    letters: ['A', 'B', 'C', 'D'], // 字母序列数组
    countdown: 180, // 初始倒计时时间，单位秒
    isRight:5,  //选择正确的选项
    isError:5,
    heartsLeft:3,
    hearts:3,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad:function(options) {
    const app = getApp();
    const newBgmUrl = './bgm/choice.mp3'; // 新的背景音乐路径
    app.setBgmAudioUrl(newBgmUrl);
    const userData = wx.getStorageSync('userData');
    const selectedRank = wx.getStorageSync('selectedRank');
    this.setData({ userData,selectedRank });
     console.log('1-userData', userData);
     // 向后端请求获取单词选择题
    this.fetchWordQuestions();

    // 启动倒计时器
    this.startCountDown();
  },
/**
 * 请求后端单词选择题
 */
fetchWordQuestions: function(){
  const selectedRank = this.data.selectedRank.level; // 获取选择的关卡
  const level = this.data.userData.levelInfo.level; // 获取用户等级
  console.log('selectedRank0' ,selectedRank);
  console.log('level' ,level);
  // 向后端发送请求获取对应关卡的单词数据
  request({
    url: '/WordQuestionInfo/getWordQuestionsByLevelForALL',
    method: 'POST',
    data: {
      level: level,
      selectedRank: selectedRank,
    }
  }).then((res) => {
      console.log("后端返回数据:", res);
      let wordQuestions = res.data;// 将获取到的单词数据存储到 wordQuestions 中
      wx.setStorageSync('wordQuestions', wordQuestions);  //将单词存入缓存
      console.log("wordQuestions:", wordQuestions);
      // 更新当前题目为第一个题目
      this.setData({ wordQuestions: wordQuestions, currentQuestion: wordQuestions[0] });
    }).catch((err) => {
      console.error('获取单词数据失败：', err);
    }).finally(() => {  //无论成功或者失败都会执行的操作
      console.log('请求完成');
    });
},
/**
   * 选择答案事件处理函数
   */
  selectAnswer: function (event) {
    // 获取用户选择的答案索引
    const choiceIndex = event.currentTarget.dataset.index;
    console.log("choiceIndex",choiceIndex);
    // 检查答案
    this.checkAnswer(choiceIndex);
    console.log("isError",this.data.isError);
    console.log("isRight",this.data.isRight);
    // 暂停一段时间后再跳转到下一题
    setTimeout(() => {
      // 判断是否已经是最后一个题目
      if (this.data.currentIndex < this.data.wordQuestions.length - 1) {
        // 如果不是最后一个题目，则将 currentIndex 加一
        this.setData({
          currentIndex: this.data.currentIndex + 1,
          // 更新当前显示的题目为下一个题目
          currentQuestion: this.data.wordQuestions[this.data.currentIndex + 1]
        });
        const isRight = 5;
        this.setData({isRight : isRight});
        const isError = 5;
        this.setData({isError : isError});
      }else{
        // 否则已经是最后一个题目，不做处理
        const isRight = 5;
        this.setData({isRight : isRight});
        const isError = 5;
        this.setData({isError : isError});
        this.handleGamePass();
      }
    }, 1000);
    

  },
// 检查答案
checkAnswer: function(choiceIndex) {
  // 获取正确选项的索引
  const correctIndex = this.data.currentQuestion.wordQuestionInfo.correctChoice;
  console.log("correctIndex",correctIndex);
  // 检查用户选择的答案是否正确
  if (choiceIndex+1 === correctIndex) {
    // 用户选择的答案是正确的
    console.log('答案正确！');
    setTimeout(() => {
      const isRight = choiceIndex;
      this.setData({isRight : isRight});
    },500);
  } else {
    // 用户选择的答案是错误的
    console.log('答案错误！');
    setTimeout(() => {
      const isError = choiceIndex;
      this.setData({isError : isError});
    },500);
    // TODO: 时间减少10秒
    if (this.data.countdown >= 10) {
      this.setData({
        countdown: this.data.countdown - 10
      }); 
    } else {
      // 触发游戏失败弹窗
      this.handleGameEnd();
    }
    //心心消失一个
    const heartsLeft = this.data.heartsLeft - 1; // 减少一个心心
    this.setData({
      heartsLeft: heartsLeft
    });
    // 当没有心心剩余时触发游戏失败
    if (heartsLeft === 0) {
      this.handleGameEnd(); // 触发游戏失败弹窗
    }
  }
},

/**
   * 启动倒计时器
   */
  startCountDown: function () {
    let intervalId = setInterval(() => {
      let countdown = this.data.countdown - 1;
      if (countdown >= 0) {
        this.setData({
          countdown: countdown
        });
      } else {
        clearInterval(intervalId);
        // 处理倒计时结束逻辑
        this.handleGameEnd();
      }
    }, 1000);
  },
/**
 * 游戏失败弹窗
 */
// 处理游戏结束逻辑
handleGameEnd: function() {
  const app = getApp();
  const newBgmUrl = './bgm/Menu Theme.mp3'; // 新的背景音乐路径
  app.setBgmAudioUrl(newBgmUrl);
  // 修改本地缓存中的用户数据
  let userData = wx.getStorageSync('userData') || {};
wx.showModal({
  title: '游戏失败',
  showCancel: true,
  cancelText:'返回',
  confirmText:'再来一次',
  success: (res) => {
    if (res.cancel) {
      wx.navigateBack({
        delta:1
      });
    }else if(res.confirm){
       // 检查本地缓存中金币数量
       if (userData.userInfo.gold-1 > 0) {
         // 金币足够，重新开始游戏
         this.reloadGame();
       } else {
         // 金币不足，显示提示并跳转到上一页
         wx.showToast({
           title: "金币不足",
           duration: 1500,
           icon: 'none',
           complete: () => {
            wx.setStorageSync('selectedRank', 0);
             wx.navigateBack({
               delta: 1
             });
           }
         });
       }
     }
   }
 });

},
/**
 * 游戏成功弹窗
 */
// 处理成功结束逻辑
handleGamePass: function() {
  const app = getApp();
  const newBgmUrl = './bgm/Menu Theme.mp3'; // 新的背景音乐路径
  app.setBgmAudioUrl(newBgmUrl);
  // 修改本地缓存中的用户数据
  let userData = wx.getStorageSync('userData') || {};
  userData.userInfo.gold = userData.userInfo.gold +5;
  let selectedRank = wx.getStorageSync('selectedRank') || 0;
  console.log("0000selectedRank",selectedRank);
  selectedRank.level++;
  console.log("1111selectedRank",selectedRank);
  wx.setStorageSync('selectedRank', selectedRank);
  if (userData && userData.userInfo && userData.userInfo.rank) {
    let ranks = userData.userInfo.rank.split('-'); // 以连字符分隔的数字串
    console.log("ranks1111111111111:",ranks);
    if (ranks.length > 0) {
      let firstRank = parseInt(ranks[1]); // 获取第二个数字并转换为整数
      console.log("firstRank",firstRank);
      console.log("selectedRank",selectedRank);
      if (!isNaN(firstRank) && firstRank+1===selectedRank.level-1) {
        firstRank++; // 加一
        ranks[1] = firstRank.toString(); // 转换为字符串并更新到数组中
        userData.userInfo.rank = ranks.join('-'); // 更新到userData.userInfo.rank中
        console.log("rank111111:",userData.userInfo.rank)
      }
    }
  }
  wx.setStorageSync('userData', userData);
  //更新UserData
  this.sendUserData(userData);
wx.showModal({
  title: '恭喜通关',
  content:'是否进入下一关？',
  showCancel:true,
  cancelText:'🔙',
  confirmText:'✔',
  success: (res) => {
    if (res.cancel) {
      wx.navigateBack({
        delta:1
      });
    }else if(res.confirm){
       // 检查本地缓存中金币数量
       if (userData.userInfo.gold-1 > 0) {
         // 金币足够下一关
         //金币-1
         const userData = wx.getStorageSync('userData');
          userData.userInfo.gold = userData.userInfo.gold-1;
          wx.setStorageSync('userData', userData);
         //更新UserData
        this.sendUserData(userData);  
         wx.redirectTo({
          url: '/pages/wordChoiceList/wordChoiceList',
          success: function(res) {
            console.log('成功重新加载并跳转页面');
          },
          fail: function(err) {
            console.error('重新加载并跳转页面失败', err);
          }
        });
       } else {
         // 金币不足，显示提示并跳转到上一页
         wx.showToast({
           title: "金币不足",
           duration: 1500,
           icon: 'none',
           complete: () => {
            wx.setStorageSync('selectedRank', 0);
             wx.navigateBack({
               delta: 1
             });
           }
         });
       }
     }
   }
 });
},
/**
 * 更新userData更新请求
 */
sendUserData: function(userData) {
  // 发起 POST 请求
  request({
    url: '/UserInfo/updateUser',
    method: 'POST',
    data: userData,
  }).then(res => {
    console.log("userData 更新成功", res);
  }).catch(err => {
    console.error('userData 更新失败：', err);
  });
  this.setData({ userData });
},

/**
 * 重新加载
 * @param {*} event 
 */
// 重新加载游戏
reloadGame: function() {
  wx.navigateTo({
    url: '/pages/wordChoiceList/wordChoiceList',
    success: function(res) {
      console.log('成功进入页面');
    },
    fail: function(err) {
      console.error('进入页面失败', err);
    }
  });
},

  
})