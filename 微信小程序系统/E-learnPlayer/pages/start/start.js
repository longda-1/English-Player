import { request } from '../../request/index.js';
Page({
  data: {
    phoneNumber: '', // 用户手机号
  },
  //BGM
  onLoad() {
    const app = getApp();
    app.initBackgroundAudio(); // 初始化背景音乐播放
  },
  // “开始旅途”按钮点击事件处理函数
  startJourney: function () {
    // 检查本地缓存是否含有用户信息(带有Grade)
    const userData = wx.getStorageSync('userData');
    console.log("用户信息：",userData);
    if (!userData) {
      // 若本地缓存没有用户信息，则显示授权弹窗
      wx.showModal({
        title: '请输入手机号',
        placeholderText:'电话号码', //显示输入框
        editable: true,
        showCancel: true,
        confirmText: '提交',
        success: (res) => {
          if (res.confirm) {
            // 用户点击了确定按钮，处理用户输入的手机号
            this.handlePhoneNumberInput(this.data.phoneNumber);
          }
        },
        // 弹窗内容为一个输入框
        input: true,
        // 绑定输入框输入事件
        success: (res) => {
          if (res.confirm) {
            // 用户点击了确定按钮，处理用户输入的手机号
            this.handlePhoneNumberInput(res.content);
          }
        }
      });
    } else {
      // 若本地缓存已有用户信息，则直接跳转到首页
      wx.navigateTo({
        url: '/pages/index/index',
      });
    }
  },
  // 处理用户输入手机号
  handlePhoneNumberInput: function (phoneNumber) {
    // 根据实际情况，可以在这里添加对手机号的合法性验证逻辑

    // 调用后端接口，传递用户手机号等信息
    request({
      url: '/wxLoginByPhone',
      method: 'POST',
      data: {
        phoneNumber: phoneNumber,
      },
    }).then(res => {
      // 后端返回用户信息
      const userData = res.data;
      console.log("userData::",userData);
      // 存储用户信息到本地缓存
      wx.setStorageSync('userData', userData);
      // 跳转到首页
      wx.navigateTo({
        url: '/pages/index/index',
      });
    }).catch(err => {
      console.error('获取用户手机号失败：', err);
      // 处理获取用户手机号失败的情况
    });
  },
  //商店跳转
  toMarket: function(){
    wx.navigateTo({
      url: '/pages/market/market',
    });
  },
  showInstructions: function () {
    wx.showModal({
      title: '使用说明',
      content: 'English-Players旨在通过游戏化的闯关模式提升用户的学习兴趣和效果，充分利用用户的碎片化时间，提供了便捷而有效的英语学习途径。English-Players通过一个个关卡，寓教于乐，提高了英语水平，收获了人生财富',
      showCancel: false
    });
  }  
});
