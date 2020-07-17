export default {
  path: '/fund',
  title: '基金',
  icon: 'bank',
  children: (pre => [
    { path: `${pre}index`, title: '持有', icon: 'list' },
    { path: `${pre}details`, title: '详情', icon: 'area-chart' }
  ])('/fund/')
}
