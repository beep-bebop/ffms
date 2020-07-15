export default ({ request }) => ({
  /**
   * @description 基金详情
   * @param {Object} data 请求携带的信息
   */
  FAMILY_PERCENT (data) {
    // 接口请求
    console.log(data)
    return request({
      url: '/family/searchFamily',
      method: 'post',
      data
    })
  }
})
