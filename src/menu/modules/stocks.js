export default {
  path: '/stocks',
  title: '股票',
  icon: 'line-chart',
  children: (pre => [
    { path: `${pre}index`, title: '概况', icon: 'list' },
    { path: `${pre}market`, title: '大盘', icon: 'bar-chart' },
    { path: `${pre}detail`, title: '详情', icon: 'area-chart' }
  ])('/stocks/')
}
