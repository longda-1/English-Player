// marketPage.js
import { request } from '../../request/index.js';
Page({
  data: {
    currentPage: 'market', // 当前页面，默认为集市
    userData: {}, // 用户数据
    medalInfo:{}
  },

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
   }

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
  // 切换到集市页面
  switchToMarket: function () {
    this.setData({ currentPage: 'market' });
  },

  // 切换到抽卡页面
  switchToDraw: function () {
    this.setData({ currentPage: 'draw' });
  },

  // 钻石兑换金币
  exchangeDiamond: function () {
    const { userData } = this.data;
    if (userData.userInfo.gold >= 10) {
      // 金币足够，执行兑换操作
      userData.userInfo.gold -= 10; // 扣除10个金币
      userData.userInfo.jewel += 1; // 增加1颗钻石
      // 更新用户数据到服务器
      this.sendUserData(userData);
      // 更新本地用户数据
      wx.setStorageSync('userData', userData);
      this.setData({ userData }); // 更新页面数据
      wx.showToast({ title: '兑换成功', icon: 'success' });
    } else {
      // 金币不足
      wx.showToast({ title: '金币不足', icon: 'none' });
    }
  },
 // 抽卡操作
 drawOneTime: function () {
  const { userData } = this.data;
  const medalInfo = this.data.medalInfo;
  if (userData.userInfo.jewel >= 1) {
    // 消耗1颗钻石
    userData.userInfo.jewel -= 1;
    // 发起请求到后端获取抽卡内容
    request({
      url: '/MedalInfo/getAllMedalInfo', // 假设这是抽卡的后端接口
      method: 'GET',
    }).then(res => {
      console.log("抽卡数据", res.data);
      // 假设返回结果包含抽到的卡牌信息
      this.setData({ medalInfo: res.data });
      const medalInfoList = res.data; // 假设返回的是一个奖牌列表
      const size = medalInfoList.length;
      const probability = Math.random() * 100; // 生成 0 到 100 之间的随机数
      let selectedMedalIndex = 0; // 默认选中第一个奖牌

      // 根据概率抽取奖牌
      if (probability < 80) {
        selectedMedalIndex = 0; // 80% 的概率抽取第一个奖牌
      } else if (probability < 90) {
        selectedMedalIndex = 1; // 10% 的概率抽取第二个奖牌
      } else if (probability < 99) {
        selectedMedalIndex = 2; // 9% 的概率抽取第三个奖牌
      } else {
        selectedMedalIndex = 3; // 1% 的概率抽取第四个奖牌
      }
      const selectedMedal = medalInfoList[selectedMedalIndex];
      wx.showToast({ title: '抽到: ' + selectedMedal.medalName, icon: 'success' });
      // 比较选中的勋章与用户当前勋章
      if (userData.userInfo.medalId === 5 || selectedMedal.medalId > userData.userInfo.medalId) {
        // 更新用户的勋章信息
        userData.userInfo.medalId = selectedMedal.medalId;

        // 发送请求更新用户数据到后端
        this.sendUserData(userData);

        // 更新本地用户数据
        wx.setStorageSync('userData', userData);
        this.setData({ userData }); // 更新页面数据
      }

    }).catch(err => {
      console.error('抽卡失败：', err);
      wx.showToast({ title: '抽卡失败', icon: 'none' });
    });
    // 更新用户数据到服务器
    this.sendUserData(userData);
    // 更新本地用户数据
    wx.setStorageSync('userData', userData);
    this.setData({ userData }); // 更新页面数据
  } else {
    wx.showToast({ title: '钻石不足', icon: 'none' });
  }
},
drawTenTimes: function () {
  const { userData } = this.data;
  if (userData.userInfo.jewel >= 10) {
    // 消耗10颗钻石
    userData.userInfo.jewel -= 10;
    
    // 创建一个数组，用于存储抽取的奖牌信息
    const drawResult = [];

    // 发起请求到后端获取抽卡内容
    request({
      url: '/MedalInfo/getAllMedalInfo', // 假设这是抽卡的后端接口
      method: 'GET',
    }).then(res => {
      console.log("抽卡数据", res.data);
      // 假设返回结果包含抽到的卡牌信息
      this.setData({ medalInfo: res.data });
      const medalInfoList = res.data; // 假设返回的是一个奖牌列表

      // 重复抽卡十次
      for (let i = 0; i < 10; i++) {
        const probability = Math.random() * 100; // 生成 0 到 100 之间的随机数
        let selectedMedalIndex = 0; // 默认选中第一个奖牌

        // 根据概率抽取奖牌
        if (probability < 80) {
          selectedMedalIndex = 0; // 80% 的概率抽取第一个奖牌
        } else if (probability < 90) {
          selectedMedalIndex = 1; // 10% 的概率抽取第二个奖牌
        } else if (probability < 99) {
          selectedMedalIndex = 2; // 9% 的概率抽取第三个奖牌
        } else {
          selectedMedalIndex = 3; // 1% 的概率抽取第四个奖牌
        }

        const selectedMedal = medalInfoList[selectedMedalIndex];
        drawResult.push(selectedMedal);
      }

      // 取出抽取结果中 medalId 最大的奖牌
      const maxMedal = drawResult.reduce((prev, current) => {
        return (prev.medalId > current.medalId) ? prev : current;
      });

      // 比较用户的 medalId 和抽取结果中 medalId 最大的奖牌的 medalId
      if (userData.userInfo.medalId === 5 || maxMedal.medalId > userData.userInfo.medalId) {
        // 进行相应的操作，例如更新用户的 medalId 或其他操作
        userData.userInfo.medalId = maxMedal.medalId;

        // 发送请求更新用户数据到后端
        this.sendUserData(userData);

        // 更新本地用户数据
        wx.setStorageSync('userData', userData);
        this.setData({ userData }); // 更新页面数据

        // 展示抽取结果
        const drawResultText = drawResult.map(item => item.medalName).join(', ');
        wx.showModal({
          title: '抽取结果',
          content: drawResultText,
          showCancel: false, // 不显示取消按钮
          confirmText: '确定', // 确认按钮的文本
          success(res) {
            if (res.confirm) {
              // 用户点击了确定按钮
              console.log('用户点击了确定按钮');
              // 进行相应的操作，例如更新用户数据等
            }
          }
        });
      } else {
        // 更新用户数据到服务器
        this.sendUserData(userData);
        const drawResultText = drawResult.map(item => item.medalName).join(', ');
        wx.showModal({
          title: '您没有抽到更高级别的奖牌,当前的奖牌是SSR',
          content: drawResultText,
          showCancel: false, // 不显示取消按钮
          confirmText: '确定', // 确认按钮的文本
          success(res) {
            if (res.confirm) {
              // 用户点击了确定按钮
              console.log('用户点击了确定按钮');
              // 进行相应的操作，例如更新用户数据等
            }
          }
        });
      }
    }).catch(err => {
      console.error('抽卡失败：', err);
      wx.showToast({ title: '抽卡失败', icon: 'none' });
    });
  } else {
    wx.showToast({ title: '钻石不足', icon: 'none' });
  }
   // 更新用户数据到服务器
   this.sendUserData(userData);
   // 更新本地用户数据
   wx.setStorageSync('userData', userData);
   this.setData({ userData }); // 更新页面数据
}
});
