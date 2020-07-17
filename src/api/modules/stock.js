export default ({ request }) => ({
  /**
   * @description 股票
   * @param {Object} data 请求携带的信息
   */
  FAMILY_STOCK (data) {
    // 接口请求
    return request({
      url: '/stock/total',
      method: 'post',
      data
    })
  },
  STOCK_TABLE (data) {
    return request({
      url: '/stock/table',
      method: 'post',
      data
    })
  },
  UPDATE_STOCK (data) {
    console.log('updateeeeee' + data.price)
    return request({
      url: '/stock/updateStock',
      method: 'put',
      data
    })
  },
  DEL_STOCK (data) {
    return request({
      url: '/stock/deleteStock',
      method: 'delete',
      data
    })
  }
})
