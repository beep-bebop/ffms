export default ({ request }) => ({
  /**
   * @description 新闻
   */
  FETCH_NEWS () {
    // 接口请求
    return request({
      url: 'https://3g.163.com/touch/reconstruct/article/list/BA8EE5GMwangning/1-6.html',
      method: 'get'
    })
  }
})
