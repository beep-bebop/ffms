export default {
  path: '/stocks',
  title: '股票',
  icon: 'flask',
  children: (pre => [
    { path: `${pre}index`, title: '概况', icon: 'home' },
    { path: `${pre}index`, title: '概况', icon: 'home' }
  ])('/stocks/')
}
