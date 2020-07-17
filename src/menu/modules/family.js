export default {
  path: '/family',
  title: '家庭组',
  icon: 'group',
  children: (pre => [
    { path: `${pre}index`, title: '成员', icon: 'group' }
    // { path: `${pre}index`, title: '概况', icon: 'home' }
  ])('/family/')
}
