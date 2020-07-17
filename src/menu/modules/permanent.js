export default {
  path: '/permanent',
  title: '固定资产',
  icon: 'automobile',
  children: (pre => [
    { path: `${pre}index`, title: '概况', icon: 'automobile' }
  ])('/permanent/')
}
