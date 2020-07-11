export default {
  path: '/family',
  title: '家庭组',
  icon: 'flask',
  children: (pre => [
    { path: `${pre}index`, title: '成员', icon: 'home' }
    // { path: `${pre}index`, title: '概况', icon: 'home' }
  ])('/family/')
}
