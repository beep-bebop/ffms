<template>
  <d2-container type="card">
    <template slot="header">
      <el-button shadow="hover" slot="header" type="primary" @click="addInRow">新增收入</el-button>
      <el-button shadow="hover" slot="header" type="info" style="margin-right: 15px" @click="addOutRow">新增支出</el-button>
      <el-input slot="header" placeholder="请输入内容" style="width: 300px">
        <template slot="prepend"></template>
      </el-input>
      <el-button slot="header" style="margin-bottom: 5px" @click="getChartData">搜索</el-button>
      <el-button type="primary" @click="exportExcel">
        <d2-icon name="download"/>
        导出 Excel
      </el-button>
      <el-card shadow="hover" style="background-color: #DFDFBD;float: right;width: 200px;height: 40px;padding-bottom: 16px">
        总金额
        <d2-count-up style="font-size: 30px;" :end="100" :decimals="2"/>
      </el-card>
    </template>
    <div style="height: 800px; margin: -16px;">
      <SplitPane :min-percent='20' :default-percent='20' split="vertical">
        <template slot="paneL" style="width: 200px;">
          <div class="inner">
            <d2-crud
              ref="d2Crud"
              add-title="新增"
              :columns="columns"
              :data="data"
              :options="options"
              :form-options="formOptions"
              @row-add="handleRowAdd"
              @dialog-cancel="handleDialogCancel">
            </d2-crud>
          </div>
        </template>
        <template slot="paneR">
          <div style="margin: 10px;">
<!--              <ve-line :data="chartData" :settings="chartSettings"></ve-line>-->
              <div id="chart" :style="{width: '1200px', height: '700px'}"></div>
          </div>
        </template>
      </SplitPane>
    </div>
  </d2-container>
</template>

<script>
import Vue from 'vue'
import pluginExport from '@d2-projects/vue-table-export'
import echarts from 'echarts'
import { mapState } from 'vuex'
Vue.use(pluginExport)
export default {
  mounted () {
    this.drawChart()
  },
  computed: {
    ...mapState('d2admin/user', [
      'info'
    ])
  },
  data () {
    this.chartSettings = {
      axisSite: { right: ['下单率'] },
      yAxisType: ['KMB', 'percent'],
      yAxisName: ['数值', '比率']
    }
    return {
      myChart: null,
      chartData: [],
      dates: [],
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
      exportColumn: [
        { label: '日期', prop: 'time' },
        { label: '姓名', prop: 'userId' },
        { label: '金额', prop: 'amount_paid' },
        { label: '描述', prop: 'description' },
        { label: '类型', prop: 'type' }
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
      formOptions: {
        saveLoading: false
      },
      options: {
        rowClassName ({ row }) {
          if (row.amount_paid < 0) {
            return 'warning-row'
          } else if (row.amount_paid > 0) {
            return 'success-row'
          }
          return ''
        }
      }
    }
  },
  methods: {
    // addRow () {
    //   this.$refs.d2Crud.showDialog({
    //     mode: 'add'
    //   })
    // },
    async getChartData () {
      try {
        const res = await this.$api.FETCH_FUND(202015)
        setTimeout(function () { alert('Hello world') }, 20000)
        this.chartData = res.data.netWorthData
        this.dates = this.chartData.map(function (item) {
          return [item[0]]
        })
        console.log(this.chartData)
        console.log(this.dates)
      } catch (error) {
        console.log(error)
      }
    },
    drawChart () {
      this.getChartData()
      // var rawData = [
      //   [
      //     '2019-01-24',
      //     1.1891,
      //     0.5411,
      //     ''
      //   ],
      //   [
      //     '2019-01-25',
      //     1.1983,
      //     0.7737,
      //     ''
      //   ],
      //   [
      //     '2019-01-28',
      //     1.198,
      //     -0.025,
      //     ''
      //   ],
      //   [
      //     '2019-01-29',
      //     1.2017,
      //     0.3088,
      //     ''
      //   ],
      //   [
      //     '2019-01-30',
      //     1.1926,
      //     -0.7573,
      //     ''
      //   ],
      //   [
      //     '2019-01-31',
      //     1.2043,
      //     0.981,
      //     ''
      //   ],
      //   [
      //     '2019-02-01',
      //     1.2207,
      //     1.3618,
      //     ''
      //   ],
      //   [
      //     '2019-02-11',
      //     1.2416,
      //     1.7121,
      //     ''
      //   ],
      //   [
      //     '2019-02-12',
      //     1.25,
      //     0.6765,
      //     ''
      //   ],
      //   [
      //     '2019-02-13',
      //     1.2738,
      //     1.904,
      //     ''
      //   ],
      //   [
      //     '2019-02-14',
      //     1.2757,
      //     0.1492,
      //     ''
      //   ],
      //   [
      //     '2019-02-15',
      //     1.2531,
      //     -1.7716,
      //     ''
      //   ],
      //   [
      //     '2019-02-18',
      //     1.2913,
      //     3.0484,
      //     ''
      //   ],
      //   [
      //     '2019-02-19',
      //     1.2891,
      //     -0.1704,
      //     ''
      //   ],
      //   [
      //     '2019-02-20',
      //     1.2938,
      //     0.3646,
      //     ''
      //   ],
      //   [
      //     '2019-02-21',
      //     1.2905,
      //     -0.2551,
      //     ''
      //   ],
      //   [
      //     '2019-02-22',
      //     1.3181,
      //     2.1387,
      //     ''
      //   ],
      //   [
      //     '2019-02-25',
      //     1.392,
      //     5.6066,
      //     ''
      //   ],
      //   [
      //     '2019-02-26',
      //     1.376,
      //     -1.1494,
      //     ''
      //   ],
      //   [
      //     '2019-02-27',
      //     1.3736,
      //     -0.1744,
      //     ''
      //   ],
      //   [
      //     '2019-02-28',
      //     1.3704,
      //     -0.233,
      //     ''
      //   ],
      //   [
      //     '2019-03-01',
      //     1.399,
      //     2.087,
      //     ''
      //   ],
      //   [
      //     '2019-03-04',
      //     1.4148,
      //     1.1294,
      //     ''
      //   ],
      //   [
      //     '2019-03-05',
      //     1.4226,
      //     0.5513,
      //     ''
      //   ],
      //   [
      //     '2019-03-06',
      //     1.4339,
      //     0.7943,
      //     ''
      //   ],
      //   [
      //     '2019-03-07',
      //     1.4199,
      //     -0.9764,
      //     ''
      //   ],
      //   [
      //     '2019-03-08',
      //     1.3656,
      //     -3.8242,
      //     ''
      //   ],
      //   [
      //     '2019-03-11',
      //     1.3912,
      //     1.8746,
      //     ''
      //   ],
      //   [
      //     '2019-03-12',
      //     1.4002,
      //     0.6469,
      //     ''
      //   ],
      //   [
      //     '2019-03-13',
      //     1.3891,
      //     -0.7927,
      //     ''
      //   ],
      //   [
      //     '2019-03-14',
      //     1.38,
      //     -0.6551,
      //     ''
      //   ],
      //   [
      //     '2019-03-15',
      //     1.3965,
      //     1.1957,
      //     ''
      //   ],
      //   [
      //     '2019-03-18',
      //     1.4343,
      //     2.7068,
      //     ''
      //   ],
      //   [
      //     '2019-03-19',
      //     1.428,
      //     -0.4392,
      //     ''
      //   ],
      //   [
      //     '2019-03-20',
      //     1.4286,
      //     0.042,
      //     ''
      //   ]
      // ]
      this.myChart = this.$echarts.init(document.getElementById('chart'))
      // var data = rawData.map(function (item) {
      //   return [item[0]]
      // })
      // console.log(rawData)
      // console.log(data)
      this.myChart.hideLoading()
      var option = {
        tooltip: {
          trigger: 'axis',
          position: function (pt) {
            return [pt[0], '10%']
          }
        },
        title: {
          left: 'center',
          text: '大数据量面积图'
        },
        toolbox: {
          feature: {
            dataZoom: {
              yAxisIndex: 'none'
            },
            restore: {},
            saveAsImage: {}
          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: this.dates
        },
        yAxis: {
          type: 'value',
          boundaryGap: [0, '100%']
        },
        dataZoom: [{
          type: 'inside',
          start: 0,
          end: 10
        }, {
          start: 0,
          end: 10,
          handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
          handleSize: '80%',
          handleStyle: {
            color: '#fff',
            shadowBlur: 3,
            shadowColor: 'rgba(0, 0, 0, 0.6)',
            shadowOffsetX: 2,
            shadowOffsetY: 2
          }
        }],
        series: [
          {
            name: '模拟数据',
            type: 'line',
            smooth: true,
            symbol: 'none',
            sampling: 'average',
            itemStyle: {
              color: 'rgb(255, 70, 131)'
            },
            areaStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                offset: 0,
                color: 'rgb(255, 158, 68)'
              }, {
                offset: 1,
                color: 'rgb(255, 70, 131)'
              }])
            },
            data: this.chartData
          }
        ]
      }
      this.myChart.setOption(option)
    },
    exportExcel () {
      this.$export.excel({
        columns: this.exportColumn,
        data: this.data
        // header: '导出 Excel',
        // merges: ['A1', 'E1']
      })
        .then(() => {
          this.$message('导出表格成功')
        })
    },
    addInRow () {
      this.$refs.d2Crud.showDialog({
        mode: 'add',
        template: {
          userId: {
            title: '姓名',
            value: this.info.username
          },
          time: {
            title: '日期',
            value: '2020-7-15'
          },
          description: {
            title: '描述',
            value: ''
          },
          type: {
            title: '类型',
            value: ''
          },
          amount_paid: {
            title: '金额',
            value: ''
          }
        }
      })
    },
    addOutRow () {
      this.$refs.d2Crud.showDialog({
        mode: 'add',
        template: {
          userId: {
            title: '姓名',
            value: this.info.username
          },
          time: {
            title: '日期',
            value: '2020-7-15'
          },
          description: {
            title: '描述',
            value: ''
          },
          type: {
            title: '类型',
            value: ''
          },
          amount_paid: {
            title: '金额',
            value: '-'
          }
        }
      })
    },
    handleRowAdd (row, done) {
      this.formOptions.saveLoading = true
      setTimeout(() => {
        console.log(row)
        this.$message({
          message: '保存成功',
          type: 'success'
        })
        done({
        })
        this.formOptions.saveLoading = false
      }, 300)
    },
    handleDialogCancel (done) {
      this.$message({
        message: '取消保存',
        type: 'warning'
      })
      done()
    }
  }
}
</script>

<style>
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
    background: rgba(90, 177, 239, 0.5);
  }
  .el-table .success-row {
    background: rgba(25, 212, 174, 0.5);
  }
</style>
