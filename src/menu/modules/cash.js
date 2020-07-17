export default {
  path: '/cash',
  title: '现金',
  icon: 'cny',
  children: (pre => [
    { path: `${pre}index`, title: '现金概况', icon: 'list' },
    { path: `${pre}plan`, title: '计划收支', icon: 'pie-chart' }
  ])('/cash/')
}
