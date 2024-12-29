import { request } from '../../request/index.js';

Page({
  /**
   * 页面的初始数据
   */
  data: {
    userData: {},   //用户信息
    levelInfo: {},        // Level 或 Grade 选项列表
    selectedGrade: '',  // 当前选择的级别
    showPicker: false,  // 控制模态框显示隐藏
    gradeList: [],      // 存储级别列表
    selectedGradeIndex: 0,  // 存储选择的级别索引
    index: 0,
    navButtonStates: [false, false, false], // 按钮的可点击状态，默认为可点击
  },
   // 页面显示时触发
   onShow: function() {
    // 调用刷新数据的函数
    this.refreshData();
  },

  // 刷新数据函数
  refreshData: function() {
    this.onLoad();
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function () {
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
     this.checkAndUpdateRank();
    } else {
      // 如果没有用户信息，则跳转到登录页
      wx.navigateTo({
        url: '/pages/login/login',
      });
    }
    /**
     * 从后端获取levelInfo
     */
    request({
      url: '/LevelInfo/getAllLevels',
      method: 'GET',
    }).then(res => {
      //console.log("res:", res);
      const levelInfo = res;
      //console.log("levelInfo:", levelInfo);
      this.setData({ levelInfo });  // 存储到页面数据
      wx.setStorageSync('levelInfo', levelInfo);
      console.log("levelInfo:", levelInfo);
    }).catch(err => {
      console.error('获取英语水平失败：', err);
    });
    const index = userData.userInfo.level-1;
    console.log("index",index);
     this.setData({ index });

    //设置按钮可点击状态
    const rank = userData.userInfo.rank;
    const navButtonStates = this.data.navButtonStates; // 按钮的方法名数组
    const rankArray = rank.split('-'); // 将 rank 拆分为数组
    //console.log("rankArray",rankArray);
    for (let i = 0; i < rankArray.length; i++) {
      if (rankArray[i] === "0") {
        this.setData({
          [`navButtonStates[${i}]`]: true, // 设置按钮不可点击
        });
        //console.log("navButtonStates ",navButtonStates );
      }else{
        //console.log("rankArray[i] ",rankArray[i] );
      }
    }
  },
//更新rank
  checkAndUpdateRank: function () {
    let userData = this.data.userData;
    let rankArray = userData.userInfo.rank.split('-').map(Number);

    if (rankArray[0] === 11 && rankArray[1] === 0 && rankArray[2] === 0 && rankArray[3] === 0) {
      rankArray[1] = 1;
    } else if (rankArray[1] === 6 && rankArray[2] === 0 && rankArray[3] === 0) {
      rankArray[2] = 1;
    } else if (rankArray[2] === 3 && rankArray[3] === 0) {
      rankArray[3] = 1;
    }

    userData.userInfo.rank = rankArray.join('-');
    this.setData({ userData });
    wx.setStorageSync('userData', userData);

    // 向后端发送请求，更新数据库
    request({
      url: '/UserInfo/updateUser',
      method: 'POST',
      data: userData,
    }).then(res => {
      console.log("userData 更新成功", res);
    }).catch(err => {
      console.error('userData 更新失败：', err);
    });
  },


  // 监听单词按钮点击事件
  navigateToWord: function () {
    const userData = this.data.userData;
    if ((userData.userInfo.level && userData.userInfo.level !== 15) || 
    (userData.levelInfo.grade && userData.levelInfo.grade !== "无")) {
      // 如果用户已经选择了 Level 或 Grade，直接导航到单词页面
      wx.navigateTo({
        url: '/pages/word/word',
      });
    } else{
      const levelInfo = this.data.levelInfo;
      const gradeList = levelInfo.map(info => `${info.grade}`);  // 使用 levelInfo 的 grade 创建选项列表
      console.log("gradeList",gradeList);
      this.setData(gradeList);
      wx.setStorageSync('gradeList', gradeList);
      // 如果用户等级或级别不存在，则弹出选择框
      //取前六个
      let itemList = gradeList.slice(0, 6); // 取前六个选项
      wx.showActionSheet({
        itemList: itemList,
        success: (res) => {
          const selectedGradeIndex = res.tapIndex;
          const selectedGrade = itemList[selectedGradeIndex];
          // 输出所选的序号和内容
          console.log("用户选择的序号：", selectedGradeIndex);
          console.log("用户选择的等级：", selectedGrade);
          // 更新userData
          let userData = this.data.userData;
          userData.userInfo.level = selectedGradeIndex + 1; // +1 因为索引是从0开始的，而等级是从1开始的
          userData.levelInfo.level = selectedGradeIndex + 1;
          userData.levelInfo.grade = selectedGrade;
          userData.userInfo.head = "user\\my.png";
         // 更新本地缓存
          wx.setStorageSync('userData', userData);

          // 向后端发送请求，更新数据库
          request({
            url: '/UserInfo/updateUser',
            method: 'POST',
            data: userData,
          }).then(res => {
            console.log("userData 更新成功", res);
            wx.navigateTo({
              url: '/pages/word/word',
            });
          }).catch(err => {
            console.error('userData 更新失败：', err);
          });
          this.setData({ userData });
        },
        fail: (res) => {
          console.error('showActionSheet fail', res);
        }
      });
      
    }
  },
 
  navigateToReading: function () {
    wx.navigateTo({
      url: '/pages/read/read',
    });
  },
  navigateToWordChoice:function () {
    wx.navigateTo({
      url: '/pages/wordChoice/wordChoice',
    });
  },
  navigateToListening: function () {
    wx.navigateTo({
      url: '/pages/listen/listen',
    });
  },
   // 点击用户信息跳转到用户页面
  navigateToUser: function () {
    // 跳转到用户页面的路径
    wx.navigateTo({
      url: '/pages/user/user',
    });
  },
  
    bindPickerChange: function (e) {
    console.log('picker发送选择改变，携带值为', e.detail.value);
    this.setData({
      index: e.detail.value
    });
     // 弹出确认弹窗
     wx.showModal({
      title: '重置关卡',
      content: '游戏进度将丢失，是否继续操作？',
      success: (res) => {
        if (res.confirm) {
          console.log('用户点击确定');
          let userData = this.data.userData;
          let levelInfo = this.data.levelInfo;
          console.log("e.detail.value",e.detail.value);
          console.log("levelInfo",levelInfo);
          console.log("levelInfo[e.detail.value].grade",levelInfo[e.detail.value].grade);
          userData.userInfo.level = parseInt(e.detail.value) + 1;
          userData.levelInfo.level = parseInt(e.detail.value) + 1;
          userData.levelInfo.grade = levelInfo[e.detail.value].grade;
          userData.userInfo.rank = '1-0-0';
          wx.setStorageSync('userData', userData);
          console.log("userData____",userData);
          // 向后端发送请求，更新数据库
          request({
            url: '/UserInfo/updateUser',
            method: 'POST',
            data: userData,
          }).then(res => {
            console.log("userData 更新成功", res);
            //刷新页面
            wx.reLaunch({
              url: '/pages/index/index', // 将此处替换为你要刷新的页面路径
            });
          }).catch(err => {
            console.error('userData 更新失败：', err);
          });
        } else if (res.cancel) {
          wx.reLaunch({
            url: '/pages/index/index', // 将此处替换为你要刷新的页面路径
          });
          console.log('用户点击取消');
        }
      }
    });
  }
});