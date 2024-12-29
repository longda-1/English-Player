// pages/wordChallenge/wordChallenge.js
import { request } from '../../request/index.js';
Page({
  /**
   * 页面的初始数据
   */
  data: {
    wordList: [], // 单词列表
    definitionList: [], // 解释列表
    countdown: 180, // 倒计时，单位：秒
    selectedWordIndex: null, // 选中的单词索引
    selectedDefinitionIndex: null, // 选中的解释索引
    selectedWordErrorIndex: null, 
    selectedDefinitionErrorIndex: null, 
    selectedWordRightIndex: null, 
    selectedDefinitionRightIndex: null, 
    matchedPairs: [], // 匹配成功的单词和解释对
    
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad:function(options) {
    const app = getApp();
    const newBgmUrl = './bgm/Race.mp3'; // 新的背景音乐路径
    app.setBgmAudioUrl(newBgmUrl);
    // 从本地缓存中获取单词列表和解释列表
    let wordList = wx.getStorageSync('wordList') || [];
    console.log("wordlist:",wordList);
    let definitionList = wordList.map(word => {
      let translation = "";
      if (word.wordSynonymsInfoList && word.wordSynonymsInfoList.length > 0) {
        translation = word.wordSynonymsInfoList[0].translation;
      }
      console.log("translation111:",translation);
      return {
        wordId: word.wordsInfo.wordId,
        translation: translation
      };
    });
    //console.log("translation:",definitionList[0].translation);
    // 打乱单词和解释的顺序
    wordList = this.shuffle(wordList);
    definitionList = this.shuffle(definitionList);
    this.setData({
        wordList: wordList,
        definitionList: definitionList
    });
    // console.log("wordlist2222:",wordList);
    console.log("definitionList:",definitionList);
    this.startCountdown();
  },

  // 打乱数组顺序
  shuffle: function(array) {
    return array.sort(() => Math.random() - 0.5);
  },

  // 倒计时逻辑
  startCountdown: function() {
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
  selectedRank.level++;
  console.log("selectedRank",selectedRank);
  console.log("______________________:");
  wx.setStorageSync('selectedRank', selectedRank);
  if (userData && userData.userInfo && userData.userInfo.rank) {
    let ranks = userData.userInfo.rank.split('-'); // 以连字符分隔的数字串
    if (ranks.length > 0) {
      let firstRank = parseInt(ranks[0]); // 获取第一个数字并转换为整数
      console.log("firstRank111:",firstRank);
      console.log("firstRank+1:",firstRank+1);
      console.log("selectedRank-1:",selectedRank.level-1);
      if (!isNaN(firstRank) && firstRank+1===selectedRank.level-1) {
        console.log("firstRank:",firstRank);
        firstRank++; // 将第一个数字加一
        ranks[0] = firstRank.toString(); // 转换为字符串并更新到数组中
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
          wx.redirectTo({
            url: '/pages/wordList/wordList',
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
    url: '/pages/wordChallenge/wordChallenge',
    success: function(res) {
      console.log('成功进入页面');
    },
    fail: function(err) {
      console.error('进入页面失败', err);
    }
  });
},
  /**
   * 在 selectWord 和 selectDefinition 函数中实现匹配逻辑，并调用 checkMatch 函数检查是否匹配成功
   */
  /**
   * 选择单词事件处理函数
   * @param {*} event 
   */
  selectWord: function(event) {
    const { index } = event.currentTarget.dataset;
    const { selectedWordIndex, matchedPairs  } = this.data;
    console.log("111111111111111111111111:");
    console.log("index:",index);
    console.log("selectedWordIndex:",selectedWordIndex);
    // 获取当前选中按钮所在列
    const currentColumn = Math.floor(index / 10);
    const previousColumn = selectedWordIndex !== null ? Math.floor(selectedWordIndex / 10) : null;
    if(selectedWordIndex !== null && selectedWordIndex === index){
      // 用户再次点击已选中的单词按钮，则取消选中
      this.setData({
        selectedWordIndex: null
      });
      console.log("selectedWordIndex01:",selectedWordIndex);
    } else if(previousColumn !== null && currentColumn === previousColumn) {
      // 用户点击同一列的新单词按钮，则取消之前选中的按钮，选中新按钮
      this.setData({
        selectedWordIndex: index
      });
      console.log("selectedWordIndex02:",selectedWordIndex);
    }else {
      this.setData({
        selectedWordIndex: index
      });
      console.log("selectedWordIndex03:",selectedWordIndex);
      // 检查是否有匹配的解释被选中
      if(this.data.selectedDefinitionIndex !== null){
        this.checkMatch();
      }
    }
    console.log("index1111111:",index);
    console.log("selectedWordIndex1111111111111:",selectedWordIndex);
  },

  /**
   * 选择解释事件处理函数
   * @param {*} event 
   */
  selectDefinition: function(event) {
    const { index } = event.currentTarget.dataset;
    const { selectedDefinitionIndex } = this.data;
    console.log("11111111111111111111:");
    console.log("index11:",index);
    console.log("selectedDefinitionIndex11:",selectedDefinitionIndex);
    // 获取当前选中按钮所在列
    const currentColumn = Math.floor(index / 10);
    const previousColumn = selectedDefinitionIndex !== null ? Math.floor(selectedDefinitionIndex / 10) : null;
    if(selectedDefinitionIndex !== null && selectedDefinitionIndex === index){
      // 用户再次点击已选中的解释按钮，则取消选中
      this.setData({
        selectedDefinitionIndex: null
      });
      console.log("selectedDefinitionIndex01:",selectedDefinitionIndex);
    } else if(previousColumn !== null && currentColumn === previousColumn){
      // 用户点击同一列的新解释按钮，则取消之前选中的按钮，选中新按钮
      this.setData({
        selectedDefinitionIndex: index
      });
      console.log("selectedDefinitionIndex02:",selectedDefinitionIndex);
    } else{
      this.setData({
        selectedDefinitionIndex: index
      });
      console.log("selectedDefinitionIndex03:",selectedDefinitionIndex);
      console.log("selectedWordIndex1111111111112:",this.data.selectedWordIndex);
      // 检查是否有匹配的单词被选中
      if (this.data.selectedWordIndex !== null) {
        this.checkMatch();
      }
    }
    // console.log("index222222222222222:",index);
    // console.log("selectedDefinitionIndex222222222222222:",selectedDefinitionIndex);
    // console.log("selectedWordIndex222222222222222:",this.data.selectedWordIndex);
  },

  // 检查是否匹配成功
  checkMatch: function() {
    const { selectedWordIndex, selectedDefinitionIndex, wordList, definitionList, matchedPairs } = this.data;
    console.log(this.data);
    console.log(selectedWordIndex, selectedDefinitionIndex, wordList, definitionList, matchedPairs);
    if (selectedWordIndex !== null && selectedDefinitionIndex !== null) {
        const word = wordList[selectedWordIndex];
        const definition = definitionList[selectedDefinitionIndex];
        console.log("word",word);
        console.log("definition",definition);
         // 检查是否属于同一个数组，即是否是匹配成功的单词和解释对
         console.log("word.wordSynonymsInfoList[0].translation",word.wordSynonymsInfoList[0].translation);
         console.log("definition.translation",definition.translation);
        if (word.wordSynonymsInfoList[0].translation === definition.translation) {
            // 匹配成功
            matchedPairs.push({ wordIndex: selectedWordIndex, definitionIndex: selectedDefinitionIndex });
            this.setData({
                matchedPairs: matchedPairs,   //将匹配成功的单词和解释对存储在 matchedPairs 中
            });
            this.setData({
              ['wordList[' + selectedWordIndex + '].matched']: true,
              ['definitionList[' + selectedDefinitionIndex + '].matched']: true
          });
            console.log("matchedPairs:",matchedPairs);
            this.setData({
              selectedWordErrorIndex: null,
              selectedDefinitionErrorIndex: null,
              selectedWordIndex: null,
              selectedDefinitionIndex: null,
              selectedWordRightIndex: selectedWordIndex, 
              selectedDefinitionRightIndex: selectedDefinitionIndex 
          });
          console.log("matchedPairs",matchedPairs);
          // 检查是否达到10对匹配
          if (matchedPairs.length === 10) {
            this.handleGamePass();
        }
          } else {
            // 匹配失败
            this.setData({
              // 将点击的按钮的样式设置为 selected_error
              selectedWordErrorIndex: selectedWordIndex,
              selectedDefinitionErrorIndex: selectedDefinitionIndex
              });
            setTimeout(() => {
              this.setData({
                  selectedWordErrorIndex: null,
                  selectedDefinitionErrorIndex: null,
                  selectedWordIndex: null,
                  selectedDefinitionIndex: null
              });
            }, 500);
            // TODO: 时间减少10秒
            if (this.data.countdown >= 10) {
              this.setData({
                countdown: this.data.countdown - 10
              }); 
            } else {
              // 触发游戏失败弹窗
              this.handleGameEnd();
            }
          }
        }
      },
  // 点击返回 wordList 页面按钮
  returnToWordListPage: function() {
    wx.navigateBack({
      delta: 2 // 假设wordList页面在返回栈中的位置是2步之前
    });
  },


  // 点击刷新页面按钮
  refreshPage: function() {
    // ...
  },
  isMatchedPair: function(index) {
    return this.data.matchedPairs.some(pair => pair.wordIndex === index);
  },
  // 页面被卸载时执行的函数
  onUnload: function() {
    wx.navigateBack({
      delta: 1 // 返回到wordList
    });
  }
});