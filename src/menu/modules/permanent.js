export default {
  path: '/permanent',
  title: '固定资产',
  icon: 'flask',
  children: (pre => [
    { path: `${pre}index`, title: '概况', icon: 'home' }
  ])('/permanent/')
}
