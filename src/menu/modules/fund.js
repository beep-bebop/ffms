export default {
  path: '/fund',
  title: '基金',
  icon: 'flask',
  children: (pre => [
    { path: `${pre}index`, title: '持有', icon: 'home' },
    { path: `${pre}details`, title: '详情', icon: 'home' }
  ])('/fund/')
}
