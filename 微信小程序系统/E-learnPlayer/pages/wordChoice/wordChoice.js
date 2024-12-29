// pages/wordChoice/wordChoice.js
import { request } from '../../request/index.js';
Page({
  /**
    * 页面的初始数据
    */
   data: {
     userData: {},   //用户信息
     ranks: [], // 关卡列表
   },
 
   /**
    * 生命周期函数--监听页面加载
    */
   onLoad: function (options) {
     // 从本地缓存中获取用户信息
     const userData = wx.getStorageSync('userData');
     console.log("userData",userData);
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
     }
 
     // 解析闯关等级，获取关卡数组
     const rank = userData.userInfo.rank;
     console.log("rank:",rank);
     const currentRank = parseInt(rank.split('-')[1]); 
     console.log("currentRank:",currentRank);
     const ranks = [];
     console.log("ranks:",ranks);
     for (let i = 1; i <= currentRank + 20; i++) {
       let clickable = false;
       let bgColor = '';
       if(i <= currentRank){
         clickable = true;
         bgColor = 'yellow';
       }
       if (i === currentRank + 1) {
         clickable = true;
         bgColor = 'orange';
       }
       ranks.push({
         level: i,
         clickable: clickable,
         bgColor: bgColor
       });
     }
     console.log("ranks1:",ranks);
     this.setData({ ranks });
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
   // 点击选择关卡
   selectRank: function (event) {
     console.log("dataset:",event.currentTarget.dataset);
     const selectedRank = event.currentTarget.dataset.level;
     console.log("selectedRank:",selectedRank);
     console.log("selectedRank.clickable:",selectedRank.clickable);
 
     if (!selectedRank.clickable) {
       wx.showToast({
         title: "关卡未解锁",//提示文字
         duration:1000,//显示时长
         mask:true,//是否显示透明蒙层，防止触摸穿透，默认：false  
         icon:'none', //图标，支持"success"、"loading"  
         success:function(){ },//接口调用成功
         fail: function () { },  //接口调用失败的回调函数  
         complete: function () { } //接口调用结束的回调函数  
      })
     } else {
       // 保存选择的关卡到本地缓存
     wx.setStorageSync('selectedRank', selectedRank);
     //金币减一
     const userData = wx.getStorageSync('userData');
      userData.userInfo.gold = userData.userInfo.gold-1;
      wx.setStorageSync('userData', userData);
      this.setData({ userData });
      console.log("userData",userData);
      console.log("selectedRank",selectedRank);
      this.sendUserData(userData);
     // 跳转到 wordList 页面
         wx.navigateTo({
           url: '/pages/wordChoiceList/wordChoiceList'
         });
     }
   }
 });