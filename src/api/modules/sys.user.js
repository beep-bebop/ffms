// import { find, assign } from 'lodash'
//
// const users = [
//   { username: 'admin', password: 'admin', uuid: 'admin-uuid', name: 'Admin' },
//   { username: 'editor', password: 'editor', uuid: 'editor-uuid', name: 'Editor' },
//   { username: 'user1', password: 'user1', uuid: 'user1-uuid', name: 'User1' }
// ]
// eslint-disable-next-line no-unused-vars
export default ({ request }) => ({
  /**
   * @description 登录
   * @param {Object} data 登录携带的信息
   */
  SYS_USER_LOGIN (data) {
    // // 模拟数据
    // service
    //   .onAny('/login')
    //   .reply(config => {
    //     const user = find(users, tools.parse(config.data))
    //     return user
    //       ? tools.responseSuccess(assign({}, user, {}))
    //       : tools.responseError({}, '账号或密码不正确')
    //   })
    // 接口请求
    console.log(data)
    return request({
      url: '/account/signon',
      method: 'post',
      data
    })
  },
  SYS_USER_LOGON (data) {
    // // 模拟数据
    // service
    //   .onAny('/login')
    //   .reply(config => {
    //     const user = find(users, tools.parse(config.data))
    //     return user
    //       ? tools.responseSuccess(assign({}, user, {}))
    //       : tools.responseError({}, '账号或密码不正确')
    //   })
    // 接口请求
    console.log(data)
    return request({
      url: '/account/newAccount',
      method: 'post',
      data
    })
  },
  SYS_USER_CHANGEPWD (data = {}) {
    return request({
      url: '/forgetPwd',
      method: 'post',
      data
    })
  }
})
