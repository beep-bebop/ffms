<template>
    <d2-container>
      <template slot="header">
        <el-input slot="header" v-model="input" placeholder="请输入加/减仓数量" style="width: 200px;margin-right: 10px">
        </el-input>
        <el-input slot="header" v-model="perPrice" placeholder="输入交易的个股单价" style="width: 200px;margin-right: 10px">
        </el-input>
        <el-button shadow="hover" slot="header" type="primary" @click="addIn">加仓</el-button>
        <el-button shadow="hover" slot="header" type="info" style="margin-right: 15px" @click="addOut">减仓</el-button>
        <el-card style="background-color: #DFDFBD;float: right;width: 400px;height: 40px;padding-bottom: 16px;text-align: center">
          该股票
          <d2-count-up style="font-size: 29px;" :end=this.$route.query.total :decimals="2"/>
        </el-card>
      </template>
      <div id="app2">
        <div id="kline" ref='kline'></div>
      </div>
    </d2-container>
</template>

<script>
import HQChart from 'hqchart'
import { mapState } from 'vuex'

function DefaultData () {}

DefaultData.GetKLineOption = function () {
  const data =
      {
        Type: '历史K线图',

        Windows: // 窗口指标
          [
            { Index: 'MA', Modify: false, Change: false },
            { Index: 'VOL', Modify: false, Change: false }
          ],

        IsShowCorssCursorInfo: true,

        Border: // 边框
          {
            Left: 1,
            Right: 1, // 右边间距
            Top: 25,
            Bottom: 25
          },

        KLine:
          {
            Right: 1, // 复权 0 不复权 1 前复权 2 后复权
            Period: 0, // 周期: 0 日线 1 周线 2 月线 3 年线
            PageSize: 70,
            IsShowTooltip: true
          }

      }

  return data
}

export default
{
  computed: {
    ...mapState('d2admin/user', [
      'info'
    ])
  },
  data () {
    return {
      input: '',
      perPrice: '',
      Symbol: '000001.sz',
      KLine:
        {
          JSChart: null,
          Option: DefaultData.GetKLineOption()
        }
    }
  },

  created () {

  },

  mounted () {
    this.OnSize()
    window.onresize = () => { this.OnSize() }
    if (this.$route.query.code != null) {
      this.Symbol = this.$route.query.code.substring(2, 8) + '.' + this.$route.query.code.substring(0, 2)
    }
    this.CreateKLineChart()
  },

  methods:
      {
        OnSize () {
          // var chartHeight = window.innerHeight - 30
          var chartHeight = 700
          // var chartWidth = window.innerWidth - 30
          var chartWidth = 1660

          var kline = this.$refs.kline
          kline.style.width = chartWidth + 'px'
          kline.style.height = chartHeight + 'px'
          if (this.KLine.JSChart) this.KLine.JSChart.OnSize()
        },
        async addIn () {
          const res = this.$api.UPDATE_STOCK({ userid: this.info.username, stockid: this.$route.query.code, amount: this.input, price: this.perPrice })
          if (res.status_code === 0) {
            this.$message({
              message: '已追加',
              type: 'success'
            })
          }
        },
        async addOut () {
          const res = this.$api.UPDATE_STOCK({ userid: this.info.username, stockid: this.$route.query.code, amount: -this.input, price: this.perPrice })
          if (res.status_code === 0) {
            this.$message({
              message: '已抛出',
              type: 'success'
            })
          }
        },
        CreateKLineChart () // 创建K线图
        // eslint-disable-next-line brace-style
        {
          if (this.KLine.JSChart) return
          this.KLine.Option.Symbol = this.Symbol
          const chart = HQChart.Chart.JSChart.Init(this.$refs.kline)
          chart.SetOption(this.KLine.Option)
          this.KLine.JSChart = chart
        }

      }
}
</script>

<style scoped>

</style>
