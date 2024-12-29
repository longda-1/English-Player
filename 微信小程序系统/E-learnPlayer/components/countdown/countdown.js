// components/countdown/countdown.js
//有bug
Component({
  data: {
    countdown: 3, // 初始倒计时值
    countdownText: "3", // 当前显示的倒计时文本
    animationData: {} // 保存动画数据
  },
  methods: {
    startCountdown() {
      const createAnimation = () => {
        const animation = wx.createAnimation({
          duration: 4000, // 调整动画持续时间为4秒
          timingFunction: 'ease-out',
        });

        // 重置动画状态
        animation.opacity(1).step(); // 重置透明度
        return animation;
      };
      let animation = createAnimation(); // 创建初始动画
      const countdownSequence = ["3", "2", "1", "GO!"];
      let index = 0;
      const countdownInterval = setInterval(() => {
        if (index < countdownSequence.length) { // 确保不超出数组边界
          const currentText = countdownSequence[index];
          index++; // 移动到下一个
          console.log("index:", index);
          console.log("currentText:", currentText);
          // 更新倒计时文本并应用动画
          animation.opacity(0).step(); // 设置透明度为0，实现淡出效果
          console.log(currentText);
          this.setData({
            countdownText: currentText, // 更新倒计时文本
            animationData: animation.export() // 导出动画
          });
          console.log("countdownText:", this.data.countdownText);
          console.log("animationData:", this.data.animationData);
          // 重建动画对象，确保状态重置
          animation = createAnimation(); // 重置动画
        } else {
          clearInterval(countdownInterval); // 如果超过了数组长度，清除间隔
          this.triggerEvent('countdownEnd'); // 触发倒计时结束事件
        }
      }, 1000); // 每秒执行一次
    }
  },
  lifetimes: {
    attached() {
      this.startCountdown(); // 组件加载时开始倒计时
    }
  }
});
