export default {
  path: '/cash',
  title: '现金',
  icon: 'flask',
  children: (pre => [
    { path: `${pre}index`, title: '现金概况', icon: 'home' },
    { path: `${pre}plan`, title: '计划收支', icon: 'home' }
  ])('/cash/')
}
