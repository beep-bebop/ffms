export default ({ request }) => ({
  /**
   * @description 基金详情
   * @param {Object} data 请求携带的信息
   */
  CASH_TOTAL (data) {
    // 接口请求
    return request({
      url: '/cash/total?userId=' + data,
      method: 'get'
    })
  },
  FAMILY_INCOME (data) {
    return request({
      url: '/income/familyList?userid=' + data,
      method: 'get'
    })
  },
  FAMILY_OUTCOME (data) {
    return request({
      url: '/disbursement/familyList?userid=' + data,
      method: 'get'
    })
  },
  DAYS_CASH (data) {
    return request({
      url: '/disbursement/total_paid?userid=' + data,
      method: 'get'
    })
  },
  ADD_INCOME (data) {
    console.log(data)
    return request({
      url: '/income/new',
      method: 'post',
      data
    })
  },
  ADD_OUTCOME (data) {
    console.log(data)
    return request({
      url: 'disbursement/insert',
      method: 'post',
      data
    })
  }
})
