<template>
  <d2-container type="card">
    <template slot="header">
      <el-button shadow="hover" slot="header" type="primary">新增收入</el-button>
      <el-button shadow="hover" slot="header" type="info" style="margin-right: 15px">新增支出</el-button>
      <el-input slot="header" placeholder="请输入内容" style="width: 300px">
        <template slot="prepend"></template>
      </el-input>
      <el-button slot="header" style="margin-bottom: 5px">搜索</el-button>
      <el-card shadow="hover" style="background-color: #DFDFBD;float: right;width: 200px;height: 40px;padding-bottom: 16px">
        总金额
        <d2-count-up style="font-size: 30px;" :end="100" :decimals="2"/>
      </el-card>
    </template>
    <div style="height: 800px; margin: -16px;">
      <SplitPane :min-percent='20' :default-percent='40' split="vertical">
        <template slot="paneL" style="width: 400px">
          <div class="inner">
            <d2-crud
              ref="d2Crud"
              :columns="columns"
              :data="data"
              :options="options">
            </d2-crud>
          </div>
        </template>
        <template slot="paneR">
          <div style="margin: 10px;">
            <div class="inner">
              <ve-bar :data="chartData" v-bind="pubSetting" :settings="chartSettings"></ve-bar>
            </div>
          </div>
        </template>
      </SplitPane>
    </div>
  </d2-container>
</template>

<script>
export default {
  data () {
    this.chartSettings = {
      labelMap: {
        date: '日期',
        in: '收入',
        out: '支出',
        profit: '净值'
      }
    }
    return {
      chartData: {
        columns: ['date', 'in', 'out', 'profit'],
        rows: [
          { date: '1/1', in: 1393, out: 1093, profit: 0.32 },
          { date: '1/2', in: 3530, out: 3230, profit: 0.26 },
          { date: '1/3', in: 2923, out: 2623, profit: 0.76 },
          { date: '1/4', in: 1723, out: 1423, profit: 0.49 },
          { date: '1/5', in: 3792, out: 3492, profit: 0.323 },
          { date: '1/6', in: 4593, out: 4293, profit: 0.78 }
        ]
      },
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
      options: {
        // eslint-disable-next-line camelcase
        rowClassName ({ row, amount_paid }) {
          // eslint-disable-next-line camelcase
          if (amount_paid < 0) {
            return 'warning-row'
            // eslint-disable-next-line camelcase
          } else if (amount_paid > 0) {
            console.log('判断！！！')
            return 'success-row'
          }
          return ''
        }
      }
    }
  }
}
</script>

<style scoped>
  .inner {
    position: absolute;
    top: 0;
    right:  20px;
    bottom: 20px;
    left: 20px;
  }
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
  .el-table .warning-row {
    background: #5ab1ef;
  }

  .el-table .success-row {
    background: #19d4ae;
  }
</style>
