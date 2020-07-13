<template>
    <d2-container>
      <div id="app2">
        <div id="kline" ref='kline'></div>
      </div>
    </d2-container>
</template>

<script>
import HQChart from 'hqchart'

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
  data () {
    var data =
        {
          Symbol: '600000.sh',
          KLine:
            {
              JSChart: null,
              Option: DefaultData.GetKLineOption()
            }

        }

    return data
  },

  created () {

  },

  mounted () {
    this.OnSize()

    window.onresize = () => { this.OnSize() }

    this.CreateKLineChart()
  },

  methods:
      {
        OnSize () {
          // var chartHeight = window.innerHeight - 30
          var chartHeight = 800
          // var chartWidth = window.innerWidth - 30
          var chartWidth = 1660

          var kline = this.$refs.kline
          kline.style.width = chartWidth + 'px'
          kline.style.height = chartHeight + 'px'
          if (this.KLine.JSChart) this.KLine.JSChart.OnSize()
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
