<template>
  <d2-container type="card">
    <template slot="header">
      <el-input slot="header" placeholder="请输入加/减仓金额" style="width: 300px;margin-right: 10px">
        <template slot="prepend"></template>
      </el-input>
      <el-button shadow="hover" slot="header" type="primary" @click="addInRow">加仓</el-button>
      <el-button shadow="hover" slot="header" type="info" style="margin-right: 15px" @click="addOutRow">减仓</el-button>
      <el-card style="background-color: #DFDFBD;float: right;width: 200px;height: 40px;padding-bottom: 16px">
        持有金额
        <d2-count-up style="font-size: 29px;" :end="100" :decimals="2"/>
      </el-card>
    </template>
    <div style="height: 700px; margin: -16px;">
      <SplitPane :min-percent='20' :default-percent='25' split="vertical">
        <template slot="paneL">
          <div class="inner">
            <el-card>
              基金  <el-divider direction="vertical"></el-divider>
              <el-divider></el-divider>
              单位净值  <el-divider direction="vertical"></el-divider> {{ this.card.netWorth }}
              <el-divider></el-divider>
              日涨跌幅 <el-divider direction="vertical"></el-divider> {{ this.card.dayGrowth }}
              <el-divider></el-divider>
              净值估算  <el-divider direction="vertical"></el-divider> {{ this.card.expectWorth }}
              <el-divider></el-divider>
              近一个月  <el-divider direction="vertical"></el-divider> {{ this.card.lastMonthGrowth }}
              <el-divider></el-divider>
              近三个月  <el-divider direction="vertical"></el-divider> {{ this.card.lastThreeMonthsGrowth }}
              <el-divider></el-divider>
              近六个月  <el-divider direction="vertical"></el-divider> {{ this.card.lastSixMonthsGrowth }}
              <el-divider></el-divider>
              近一年  <el-divider direction="vertical"></el-divider> {{ this.card.lastYearGrowth }}
            </el-card>
          </div>
        </template>
        <template slot="paneR">
          <div style="margin: 5px;">
<!--              <ve-line :data="chartData" :settings="chartSettings"></ve-line>-->
              <div id="chart" :style="{width: '1250px', height: '700px'}"></div>
          </div>
        </template>
      </SplitPane>
    </div>
  </d2-container>
</template>

<script>
import echarts from 'echarts'
import { mapState } from 'vuex'
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
    return {
      myChart: null,
      chartData: [],
      dates: [],
      percent: [],
      card: {
        code: '0',
        name: '0',
        netWorth: '0',
        dayGrowth: '0',
        expectWorth: '0',
        lastMonthGrowth: '0',
        lastThreeMonthsGrowth: '0',
        lastSixMonthsGrowth: '0',
        lastYearGrowth: '0'
      }
    }
  },
  methods: {
    getCardData (res) {
      this.card.netWorth = res.data.netWorth
      this.card.dayGrowth = res.data.dayGrowth
      this.card.expectWorth = res.data.expectWorth
      this.card.lastMonthGrowth = res.data.lastMonthGrowth
      this.card.lastThreeMonthsGrowth = res.data.lastThreeMonthsGrowth
      this.card.lastSixMonthsGrowth = res.data.lastSixMonthsGrowth
      this.data.lastYearGrowth = res.data.lastYearGrowth
    },
    async getChartData () {
      try {
        const res = await this.$api.FETCH_FUND(202015)
        setTimeout(function () {}, 20000)
        this.chartData = res.data.netWorthData
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
              name: '净值',
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
          text: '基金净值'
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
          start: 90,
          end: 100
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
            name: '净值',
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
