App({
  globalData: {
    innerAudioContext: null,
    bgmAudioUrl: './bgm/Menu Theme.mp3',
  },

  // 初始化背景音频上下文
  initBackgroundAudio() {
    if (!this.globalData.innerAudioContext) {
      this.globalData.innerAudioContext = wx.createInnerAudioContext();
      this.globalData.innerAudioContext.src = this.globalData.bgmAudioUrl;
      this.globalData.innerAudioContext.loop = true;
      this.globalData.innerAudioContext.play();
    } else {
      // 确保播放状态一致性
      if (this.globalData.innerAudioContext.paused) {
        this.globalData.innerAudioContext.play();
      }
    }
  },

  // 停止背景音频播放
  stopBackgroundAudio() {
    const innerAudioContext = this.globalData.innerAudioContext;
    if (innerAudioContext) {
      innerAudioContext.stop();
    }
  },

  // 设置新的背景音乐 URL
  setBgmAudioUrl(url) {
    this.globalData.bgmAudioUrl = url;
    const innerAudioContext = this.globalData.innerAudioContext;
    if (innerAudioContext) {
      innerAudioContext.src = url;
      innerAudioContext.play(); // 确保新音乐播放
    }
  },
  /**
   * 当小程序启动，或从后台进入前台显示，会触发 onShow
   */
  onShow: function (options) {
    console.log('onShow')
  },

  /**
   * 当小程序从前台进入后台，会触发 onHide
   */
  onHide: function () {
    console.log('onHide')
  },

  /**
   * 当小程序发生脚本错误，或者 api 调用失败时，会触发 onError 并带上错误信息
   */
  onError: function (msg) {
    
  }
})
