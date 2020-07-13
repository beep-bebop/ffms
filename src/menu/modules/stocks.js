export default {
  path: '/stocks',
  title: '股票',
  icon: 'flask',
  children: (pre => [
    { path: `${pre}index`, title: '概况', icon: 'home' },
    { path: `${pre}market`, title: '大盘', icon: 'home' },
    { path: `${pre}detail`, title: '详情', icon: 'home' }
  ])('/stocks/')
}
