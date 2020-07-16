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
  },
  FAMILY_LINE (data) {
    // 接口请求
    return request({
      url: '/family/getFamilyProperty',
      method: 'post',
      data
    })
  },
  HAVE_FAMILY (data) {
    return request({
      url: '/family/findFamily',
      method: 'post',
      data
    })
  },
  QUIT_FAMILY (data) {
    return request({
      url: '/family/quitFamily',
      method: 'post',
      data
    })
  },
  JOIN_FAMILY (data) {
    return request({
      url: '/family/joinFamily',
      method: 'post',
      data
    })
  }
})
