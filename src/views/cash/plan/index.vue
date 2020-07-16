<template>
  <d2-container type="card">
    <div style="height: 800px; margin: -16px;">
      <SplitPane :min-percent='20' :default-percent='35' split="vertical">
        <template slot="paneL" style="width: 35%">
          <el-card shadow="hover" style="width: 580px;height: 60px;text-align: center">
            我的支出类型
          </el-card>
          <div>
            <ve-pie :data="chartData1" :settings="chartSettings"></ve-pie>
          </div>
          <img src="./image/plan.svg" style="width: 500px;height: 400px">
        </template>
        <template slot="paneR">
              <div style="margin: 10px;">
                <div id="chart" :style="{width: '1100px', height: '600px'}"></div>
              </div>
            </template>
<!--            <template slot="paneR">-->
<!--              <div style="margin: 10px;vertical-align: middle">-->
<!--                -->
<!--              </div>-->
<!--            </template>-->
      </SplitPane>
    </div>
  </d2-container>
</template>

<script>
import echarts from 'echarts'
import { mapState } from 'vuex'
export default {
  computed: {
    ...mapState('d2admin/user', [
      'info'
    ])
  },
  data () {
    this.chartSettings = {
      roseType: 'radius',
      radius: 150
    }
    return {
      chartData1: {
        columns: ['type', 'amount_paid'],
        rows: [
          { type: '1/1', amount_paid: 1393 },
          { type: '1/2', amount_paid: 3530 },
          { type: '1/3', amount_paid: 2923 },
          { type: '1/4', amount_paid: 1723 },
          { type: '1/5', amount_paid: 3792 },
          { type: '1/6', amount_paid: 4593 }
        ]
      },
      columns: [
      ],
      data: [],
      options: {
      }
    }
  },
  methods: {
    async getPercent () {
      const res = await this.$api.OUT_TYPE(this.info.username)
      this.chartData1.rows = res.data
    },
    async getChartData () {
      try {
        const res = await this.$api.WEEK_TOTAL(this.info.username)
        setTimeout(function () {}, 20000)
        this.chartData = res.data
        this.dates = this.chartData.map(function (item) {
          return [item[0]]
        })
        this.myChart.setOption({
          xAxis: {
            type: 'category',
            boundaryGap: false,
            data: this.dates
          },
          series: [
            {
              name: '现金总额',
              type: 'line',
              smooth: false,
              symbol: 'none',
              sampling: 'average',
              itemStyle: {
                color: 'rgb(25, 212, 274)'
              },
              areaStyle: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                  offset: 0,
                  color: 'rgba(90, 177, 239, 0.5)'
                }, {
                  offset: 1,
                  color: 'rgba(25, 212, 174, 0.5)'
                }])
              },
              data: this.chartData
            }
          ]
        })
        this.getCardData(res)
      } catch (error) {
        console.log(error)
      }
    },
    drawChart () {
      this.getChartData()
      this.myChart = this.$echarts.init(document.getElementById('chart'))
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
          text: '家庭现金变化'
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: []
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
          start: 90,
          end: 100,
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
            name: '现金',
            type: 'line',
            smooth: false,
            symbol: 'none',
            sampling: 'average',
            itemStyle: {
              color: 'rgb(25, 212, 274)'
            },
            areaStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                offset: 0,
                color: 'rgba(90, 177, 239, 0.5)'
              }, {
                offset: 1,
                color: 'rgba(25, 212, 174, 0.5)'
              }])
            },
            data: []
          }
        ]
      }
      this.myChart.setOption(option)
    }
  },
  mounted () {
    this.drawChart()
    this.getPercent()
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
