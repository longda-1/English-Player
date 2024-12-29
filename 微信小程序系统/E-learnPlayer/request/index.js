//请求后台
//同时发送异步请求的次数
let ajaxTimes = 0;
export const request = (params) => {
  ajaxTimes ++;
  wx.showLoading({
    title: '加载中',
    mask: true
  })
  const baseUrl = "http://localhost:8080";
  return new Promise((resolve,reject) => {
    wx.request({
      url: baseUrl + params.url,
      method:params.method,  //使用传入的请求方法，如果没有传入则默认为 "POST"
      data: params.data,
      success: (result) => {
        resolve(result.data);
      },
      fail: (err) => {
        reject(err);
      },
      complete: () => {
        ajaxTimes --;
        if(ajaxTimes === 0){
          wx.hideLoading();
        }
      }
    })

  })
}