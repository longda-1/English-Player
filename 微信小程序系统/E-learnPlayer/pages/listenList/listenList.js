// pages/listenList/listenList.js
import { request } from '../../request/index.js';
Page({

  /**
   * 页面的初始数据
   */
  data: {
    listeningData:{},
    correctCount: 0, // 答对题目数量
    selectedAnswers: {}, // 用户选择的答案
    totalCount: 0, // 总题目数量
    UserListeningAnswersInfo: [] // 用户答题记录
  },

  onLoad: function () {
    const app = getApp();
    app.stopBackgroundAudio();
    // 获取后端提供的文章和题目数据，并渲染到页面
    const selectedRank = wx.getStorageSync('selectedRank');
    const userData = wx.getStorageSync('userData');
    const level = userData.levelInfo.level;
    console.log("selectedRank",selectedRank);
    console.log("level:",level)
    this.getListeningData(selectedRank.level,level);
  },
  /**
   * 调用后端接口获取文章和题目数据
   */
  getListeningData: function(selectedRank,level) {
    // 向后端发送请求获取对应关卡的阅读数据
    request({
      url: '/ListeningInfo/getListeningByLevelAndRank',
      method: 'GET',
      data: {
        level: level,
        selectedRank: selectedRank,
      }
    }).then((res) => {
      console.log('Response:', res); // 输出响应内容，用于调试
      //后端返回数据在res.data中
      const listeningData = res.data;
      if(!listeningData){
        console.error('Failed to fetch Listening data: Empty response');
        return;
      }
      console.log("listeningData",listeningData)
      //更新页面数据
      this.setData({
        listeningData:listeningData
    });
    console.log("listeningData111111111",listeningData);
    const totalCount = listeningData.questions.length;
    this.setData({
      totalCount:totalCount
    });
    }).catch(err => {
      console.error('Failed to fetch listening data:', err);
    }).finally(() => {  //无论成功或者失败都会执行的操作
      console.log('请求完成');
    });
  },
/**
   * 播放单词发音
   * @param {*} event 
   */
  playPronunciation() {
    let audioFile = this.data.listeningData.audioInfo.audioFile; // 获取单词和发音类型
    console.log("audioFile1",audioFile);
    audioFile = audioFile.replace(/\\/g, '/');
    console.log("audioFile2",audioFile);
    const audioUrl = 'http://localhost:8000/' + audioFile; // 构建发音链接
    console.log("audioUrl",audioUrl);
    wx.playBackgroundAudio({ // 使用微信小程序的音频播放接口
        dataUrl: audioUrl
    });
  },
/**
   * 选项按钮点击事件处理函数
   */
  /**
   * 用户点击选项按钮
   */
  selectChoice:function (e) {
    console.log("e.currentTarget.dataset:", e.currentTarget.dataset);
    // 获取用户选择的答案索引
    const choiceIndex = e.currentTarget.dataset.index;
    console.log("choiceIndex",choiceIndex);
    const questionIndex = e.currentTarget.dataset.questionId;
    console.log("questionIndex",questionIndex);
    // 更新用户选择的答案
      // 创建selectedAnswers的副本
      const updatedSelectedAnswers = { ...this.data.selectedAnswers };
      updatedSelectedAnswers[questionIndex] = choiceIndex; // 保存用户在该题目中选择的选项
      // 更新页面数据
      this.setData({
        selectedAnswers: updatedSelectedAnswers
      });
      console.log("selectedAnswers",this.data.selectedAnswers);
  },
/**
   * 提交
   */
  submitAnswers:function (e) {
    // 获取 selectedAnswers 的长度
    const selectedAnswers = this.data.selectedAnswers;
    const selectedAnswersLength = Object.keys(selectedAnswers).length;
    console.log("selectedAnswers length:", selectedAnswersLength);
    if(selectedAnswersLength === this.data.totalCount){
      this.checkAnswers();
      if(this.data.correctCount/this.data.totalCount >=0.8){
        // 将答案数据发送到后端保存
        this.saveAnswers(this.data.UserReadingAnswersInfo);
        //游戏成功弹窗
        this.handleGamePass();
      }else{
        //游戏失败弹窗
        this.handleGameEnd();
      }
    }else{
      wx.showToast({
        title: "题目未做完",//提示文字
        duration:1000,//显示时长
        mask:true,//是否显示透明蒙层，防止触摸穿透，默认：false  
        icon:'none', //图标，支持"success"、"loading"  
        success:function(){ },//接口调用成功
        fail: function () { },  //接口调用失败的回调函数  
        complete: function () { } //接口调用结束的回调函数  
     })
    }
  },
  /**
   * 检查答案
   */
  checkAnswers: function(){
    const { selectedAnswers, listeningData } = this.data;
    let correctCount = 0;
    console.log("______________________:");
    // console.log("selectedAnswers:",selectedAnswers);
    // console.log("readingData:",readingData);
    // console.log("correctCount:",correctCount);
     // 创建答案字母到数字的映射
     const answerMap = {
      'A': 1,
      'B': 2,
      'C': 3,
      'D': 4
    };
    const UserListeningAnswersInfo = this.data.UserListeningAnswersInfo;
    listeningData.questions.forEach(question => {
      const correctAnswer = answerMap[question.correctAnswer]; // 转换成数字
      const userAnswer = selectedAnswers[question.readingQuestionId];
      // console.log("userAnswer::",userAnswer);
      const isCorrect = userAnswer === correctAnswer ? 1 : 0;
      // console.log("isCorrect::",isCorrect);
      if (isCorrect) {
        correctCount++;
        // console.log("correctCount::",correctCount);
      }
      const userData = wx.getStorageSync('userData');
      // 准备提交的答案数据
      UserListeningAnswersInfo.push({
        userId: userData.userInfo.userId,
        readingQuestionId: question.listeningQuestionId,
        selectedAnswer: userAnswer,
        isCorrect: isCorrect
      });
    });
    // 更新页面数据
    this.setData({
      correctCount: correctCount,
      UserListeningAnswersInfo: UserListeningAnswersInfo // 保存答案信息
    });
    console.log(`正确题目数量: ${correctCount}`);
    console.log("UserListeningAnswersInfo::",UserListeningAnswersInfo);
    console.log("——————————————————————————————");
  },
  /**
   * 保存答案数据到后端
   */
  saveAnswers: function (UserListeningAnswersInfo) {
    request({
      url:'/UserListeningAnswers/insertUserListeningAnswers',
      method:'POST',
      data : UserListeningAnswersInfo,
    }).then(res => {
      console.log("userData 更新成功", res);
    }).catch(err => {
      console.error('userData 更新失败：', err);
    });
    this.setData({ UserListeningAnswersInfo });
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
       if (userData.userInfo.jewel-2 > 0) {
         // 足够，重新开始游戏
         this.reloadGame();
       } else {
         // 不足，显示提示并跳转到上一页
         wx.showToast({
           title: "钻石不足",
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
  userData.userInfo.jewel = userData.userInfo.jewel +5;
  let selectedRank = wx.getStorageSync('selectedRank') || 0;
  console.log("0000selectedRank",selectedRank);
  selectedRank.level++;
  console.log("1111selectedRank",selectedRank);
  wx.setStorageSync('selectedRank', selectedRank);
  if (userData && userData.userInfo && userData.userInfo.rank) {
    let ranks = userData.userInfo.rank.split('-'); // 以连字符分隔的数字串
    console.log("ranks1111111111111:",ranks);
    if (ranks.length > 0) {
      let firstRank = parseInt(ranks[3]); 
      console.log("firstRank",firstRank);
      console.log("selectedRank",selectedRank);
      if (!isNaN(firstRank) && firstRank+1===selectedRank.level-1) {
        firstRank++; // 加一
        ranks[3] = firstRank.toString(); // 转换为字符串并更新到数组中
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
       // 检查本地缓存中钻石数量
       if (userData.userInfo.jewel-2 > 0) {
         // 足够下一关
         //-1
         const userData = wx.getStorageSync('userData');
          userData.userInfo.jewel = userData.userInfo.jewel-1;
          wx.setStorageSync('userData', userData);
         //更新UserData
        this.sendUserData(userData);  
         wx.redirectTo({
          url: '/pages/listenList/listenList',
          success: function(res) {
            console.log('成功重新加载并跳转页面');
          },
          fail: function(err) {
            console.error('重新加载并跳转页面失败', err);
          }
        });
       } else {
         // 钻石不足，显示提示并跳转到上一页
         wx.showToast({
           title: "钻石不足",
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
// 重新加载游戏
reloadGame: function() {
  wx.navigateTo({
    url: '/pages/listenList/listenList',
    success: function(res) {
      console.log('成功进入页面');
    },
    fail: function(err) {
      console.error('进入页面失败', err);
    }
  });
}
})