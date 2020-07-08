export default {
  path: '/fund',
  title: '基金',
  icon: 'flask',
  children: (pre => [
    { path: `${pre}index`, title: '概况', icon: 'home' },
    { path: `${pre}index`, title: '概况', icon: 'home' }
  ])('/fund/')
}
