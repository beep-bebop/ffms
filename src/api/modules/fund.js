export default ({ request, formRequest }) => ({
  /**
   * @description 基金详情
   * @param {Object} data 请求携带的信息
   */
  FETCH_FUND (data = {}) {
    // 接口请求
    return formRequest({
      url: 'https://api.doctorxiong.club/v1/fund/detail?code=' + data,
      method: 'get'
    })
  },
  FUND_FAMILY (data) {
    return request({
      url: '/family/searchFamily',
      method: 'post',
      data
    })
  },
  FUND_TABLE (data) {
    return request({
      url: 'http://localhost:8080/fund/table',
      method: 'post',
      data
    })
  },
  DEL_FUND (data) {
    return request({
      url: '/fund/deleteFund',
      method: 'delete',
      data
    })
  },
  UPDATE_FUND (data) {
    return request({
      url: '/fund/updateFund',
      method: 'put',
      data
    })
  }
})
