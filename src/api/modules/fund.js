export default ({ request }) => ({
  /**
   * @description 基金详情
   * @param {Object} data 请求携带的信息
   */
  FETCH_FUND (data = {}) {
    // 接口请求
    return request({
      url: 'https://api.doctorxiong.club/v1/fund/detail?code=' + data,
      method: 'get'
    })
  }
})
