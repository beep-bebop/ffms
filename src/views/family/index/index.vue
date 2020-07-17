<template>
  <d2-container type="card">
    <template slot="header">
      <span v-if="isFamily">欢迎来到家庭组：{{this.familyId}}  </span>
      <el-button v-if="!isFamily" shadow="hover" slot="header" type="info" style="margin-right: 15px" @click="dialogFormVisible1 = true">创建家庭</el-button>
      <el-button v-if="!isFamily" shadow="hover" slot="header" type="primary" @click="dialogFormVisible = true">加入家庭</el-button>
      <el-button v-if="isFamily" shadow="hover" slot="header" type="info" style="margin-right: 15px" @click="quitFamily">退出家庭</el-button>
      <span v-if="isFamily">家庭组key：{{this.familyId}}  </span>
<!--      <el-input slot="header" placeholder="请输入内容" style="width: 300px">-->
<!--        <template slot="prepend"></template>-->
<!--      </el-input>-->
<!--      <el-button slot="header" style="margin-bottom: 5px">搜索</el-button>-->
      <el-card shadow="hover" style="background-color: #DFDFBD;float: right;width: 400px;height: 40px;padding-bottom: 16px">
        总资产
        <d2-count-up style="font-size: 30px;" :end=total :decimals="2"/>
      </el-card>
    </template>
    <div style="height: 800px; margin: -16px;">
      <SplitPane :min-percent='20' :default-percent='30' split="vertical">
        <template slot="paneL" style="width: 55%">
          <div :key="item.userid" v-for="(item) in member" style="margin: 20px">
            <el-card shadow="hover" class="page_card" style="background-color: #ebf1f6">
              <el-avatar shape="circle" src='https://v1.alapi.cn/api/avatar?email=1143432728@qq.com&size=300' ></el-avatar>
              用户名{{item.userid}}<el-divider direction="vertical"></el-divider>电话{{item.phone}}
            </el-card>
          </div>
          <img src="./image/family.svg" style="width: 500px">
        </template>
        <template slot="paneR">
          <SplitPane split="horizontal">
            <template slot="paneL">
              <div style="margin: 10px;">
<!--                <ve-line :data="chartData1" :settings="chartSettings1"></ve-line>-->
                <div id="chart" :style="{width: '1250px', height: '390px'}"></div>
              </div>
            </template>
            <template slot="paneR">
              <el-card>家庭基金</el-card>
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
    <el-dialog title="创建家庭" :visible.sync="dialogFormVisible1">
      <el-form :model="form1">
        <el-form-item label="家庭ID" :label-width="form1LabelWidth">
          <el-input v-model="form1.name" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogForm1Visible = false">取 消</el-button>
        <el-button type="primary" @click="create">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="加入家庭" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="家庭组ID" :label-width="formLabelWidth">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="家庭组KEY" :label-width="formLabelWidth">
          <el-input v-model="form.key" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="join">确 定</el-button>
      </div>
    </el-dialog>
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
  mounted () {
    this.drawChart()
    this.isHaveFamily()
    this.getPercent()
  },
  data () {
    this.chartSettings1 = {
      axisSite: { right: ['下单率'] },
      yAxisType: ['KMB', 'percent'],
      yAxisName: ['数值', '比率']
    }
    return {
      total: '0',
      isFamily: false,
      familyId: '',
      familyKey: '',
      dialogForm1Visible: false,
      form1: {
        name: '',
        key: ''
      },
      form1LabelWidth: '120px',
      dialogFormVisible: false,
      form: {
        name: '',
        key: ''
      },
      formLabelWidth: '120px',
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
      data: [],
      member: [
        { userid: ' ', color: '#EACACA', type: '股票', amount: '100' }
      ]
    }
  },
  methods: {
    async getPercent () {
      try {
        const res = await this.$api.FAMILY_PERCENT({
          userid: this.info.username,
          searchId: this.info.username
        })
        this.total = parseFloat(res.family[0][1]) + parseFloat(res.family[2][1]) + parseFloat(res.family[1][1])
      } catch (error) {
        console.log(error)
      }
    },
    async quitFamily () {
      const res = await this.$api.QUIT_FAMILY({ userid: this.info.username })
      console.log(res.status_code)
      this.isHaveFamily()
      this.$forceUpdate()
    },
    async join () {
      this.dialogFormVisible = false
      const res = await this.$api.JOIN_FAMILY({ userid: this.info.username, familyid: this.form.name, familykey: this.form.key })
      console.log(res.status_code)
      this.isHaveFamily()
    },
    async create () {
      this.dialogForm1Visible = false
    },
    async isHaveFamily () {
      const res = await this.$api.HAVE_FAMILY({ userid: this.info.username })
      if (res.status_code === 0) {
        this.isFamily = true
        this.familyId = res.data[0]
        this.familyKey = res.data[1]
        this.member = res.users
        this.getChartData()
      }
    },
    async getChartData () {
      try {
        const res = await this.$api.FAMILY_LINE({ userid: this.info.username })
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
          text: '过去一年家庭资产变化'
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
