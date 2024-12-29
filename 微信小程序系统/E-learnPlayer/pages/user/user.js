import { request } from "../../request/index";
// user.js
Page({
  data:{
    userData: {},
    wordCollection: [], // 存放单词收藏夹数据
  },
  onLoad: function(){
      const userData =wx.getStorageSync('userData');
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
       this.setData({ userData });
      // 调用函数获取单词收藏夹数据
      this.getWordCollection(userData.userInfo.userId);
      }
      
  },
// 获取单词收藏夹数据
getWordCollection: function(userId) {
  console.log("userId",userId);
  // 向后端发送请求
  request({
    url: '/wordCollection/getWordCollection',
    method:'POST',
    data: userId,
  }).then(res => {
    console.log("res:", res);
    const wordCollection = res.data;
    console.log("wordCollection:", wordCollection);
    this.setData({wordCollection});
  })
},



// 点击头像选择新头像
onChooseAvatar: function() {
  wx.chooseImage({
    count: 1, // 只允许选择一张图片
    sizeType: ['compressed'], // 压缩图片
    sourceType: ['album', 'camera'], // 从相册或相机选择
    success: res => {
      const tempFilePath = res.tempFilePaths[0];
      // 更新本地缓存中的头像信息
      const userInfoWithGrade = this.data.userInfoWithGrade;
      userInfoWithGrade.head = tempFilePath;
      // 更新页面数据
      this.setData({ userInfoWithGrade });
      // 将新头像信息存入本地缓存
      wx.setStorageSync('userInfoWithGrade', userInfoWithGrade);
    },
    fail: error => {
      console.error('选择头像失败:', error);
    }
  });
},

  // 修改昵称
  modifyNickname: function () {
    // 添加逻辑代码以修改昵称
  },
 
  // 修改水平
  modifyLevel: function () {
    // 添加逻辑代码以修改水平
  },

  // 查看单词本
  viewCollection: function () {
    // 添加逻辑代码以查看单词本
  },

  // 查看个人资料
  viewProfile: function () {
    // 添加逻辑代码以查看个人资料
  },

  // 账号管理
  manageAccount: function () {
    // 添加逻辑代码以管理账号
  },

  // 关于我们
  aboutUs: function () {
    // 添加逻辑代码以显示关于我们页面
  },

  // 退出登录
  logout: function () {
    // 添加逻辑代码以退出登录
  },

  // 其他代码...
});

