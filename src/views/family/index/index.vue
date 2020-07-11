<template>
  <d2-container type="card">
    <template slot="header">
      <el-button shadow="hover" slot="header" type="primary">加入家庭</el-button>
      <el-button shadow="hover" slot="header" type="info" style="margin-right: 15px">退出家庭</el-button>
<!--      <el-input slot="header" placeholder="请输入内容" style="width: 300px">-->
<!--        <template slot="prepend"></template>-->
<!--      </el-input>-->
<!--      <el-button slot="header" style="margin-bottom: 5px">搜索</el-button>-->
      <el-card shadow="hover" style="background-color: #DFDFBD;float: right;width: 200px;height: 40px;padding-bottom: 16px">
        总资产
        <d2-count-up style="font-size: 30px;" :end="100" :decimals="2"/>
      </el-card>
    </template>
    <div style="height: 800px; margin: -16px;">
      <SplitPane :min-percent='20' :default-percent='30' split="vertical">
        <template slot="paneL" style="width: 55%">
          <div :key="item.i" v-for="(item) in member" style="margin: 20px">
            <el-card shadow="hover" class="page_card" style="background-color: #ebf1f6">
              <el-avatar shape="circle" src="https://v1.alapi.cn/api/avatar?email=weify369@gmail.com&size=200" ></el-avatar>
              {{item.i}}
            </el-card>
          </div>
          <img src="./image/family.svg" style="width: 500px">
        </template>
        <template slot="paneR">
          <SplitPane split="horizontal">
            <template slot="paneL">
              <div style="margin: 10px;">
                <ve-line :data="chartData1" :settings="chartSettings1"></ve-line>
              </div>
            </template>
            <template slot="paneR">
              <div style="margin: 10px;vertical-align: middle">
                <d2-crud
                  ref="d2Crud"
                  :columns="columns"
                  :data="data"
                  :options="options">
                </d2-crud>
              </div>
            </template>
          </SplitPane>
        </template>
      </SplitPane>
    </div>
  </d2-container>
</template>

<script>
export default {
  data () {
    this.chartSettings1 = {
      axisSite: { right: ['下单率'] },
      yAxisType: ['KMB', 'percent'],
      yAxisName: ['数值', '比率']
    }
    return {
      columns: [
        {
          title: '日期',
          key: 'time',
          width: '100'
        },
        {
          title: '成员',
          key: 'userId',
          width: '100'
        },
        {
          title: '描述',
          key: 'description',
          width: '180'
        },
        {
          title: '类型',
          key: 'type',
          filters: [
            { text: '饮食', value: 'eat' },
            { text: '购物', value: 'shopping' },
            { text: '游玩', value: 'play' },
            { text: '学习', value: 'learn' },
            { text: '其他', value: 'etc' }
          ],
          filterMethod (value, row) {
            return row.type === value
          },
          filterPlacement: 'bottom-end',
          width: '100'
        },
        {
          title: '金额',
          key: 'amount_paid',
          width: '100'
        }
      ],
      data: [
        {
          time: '2016-05-02',
          userId: '王小虎',
          amount_paid: '123',
          description: 'lalala',
          type: 'eat'
        },
        {
          time: '2016-05-02',
          userId: '王小虎',
          amount_paid: '123',
          description: 'lalala',
          type: 'eat'
        },
        {
          time: '2016-05-02',
          userId: '王小虎',
          amount_paid: '123',
          description: 'lalala',
          type: 'eat'
        },
        {
          time: '2016-05-02',
          userId: '王小虎',
          amount_paid: '-123',
          description: 'lalala',
          type: 'eat'
        }
      ],
      member: [
        { i: '0', color: '#EACACA', type: '股票', amount: '100' },
        { i: '1', color: '#F0DFB6', type: '股票', amount: '100' },
        { i: '2', color: '#A7C3D7', type: '股票', amount: '100' }
      ],
      chartData1: {
        columns: ['日期', '访问用户', '下单用户', '下单率'],
        rows: [
          { 日期: '1/1', 访问用户: 1393, 下单用户: 1093, 下单率: 0.32 },
          { 日期: '1/2', 访问用户: 3530, 下单用户: 3230, 下单率: 0.26 },
          { 日期: '1/3', 访问用户: 2923, 下单用户: 2623, 下单率: 0.76 },
          { 日期: '1/4', 访问用户: 1723, 下单用户: 1423, 下单率: 0.49 },
          { 日期: '1/5', 访问用户: 3792, 下单用户: 3492, 下单率: 0.323 },
          { 日期: '1/6', 访问用户: 4593, 下单用户: 4293, 下单率: 0.78 }
        ]
      }
    }
  }
}
</script>

<style scoped>
  .el-button--primary {
    color: #fff;
    background-color: #19d4ae;
    border-color: #ffffff;
  }
  .el-button--info {
    color: #fff;
    background-color: #5ab1ef;
    border-color: #ffffff;
  }
</style>
