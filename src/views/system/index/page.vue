<template>
  <d2-container class="page">
    <div style="height: 800px; margin: -16px;">
      <SplitPane :min-percent='20' :default-percent='30' split="vertical">
        <template slot="paneL" style="width: 80%">
          <div :key="item.i" v-for="(item) in member" style="margin: 20px">
            <el-card shadow="hover" :style="{'background-color': item.color, 'height': item.height}">
              {{item.type}}
              <d2-count-up style="font-size: 50px" :end=item.amount :decimals="2"/>
            </el-card>
          </div>
          <img src="./image/Finance.svg" style="width: 500px">
        </template>
        <template slot="paneR">
          <SplitPane :min-percent='20' :default-percent='70' split="vertical">
            <template slot="paneL" style="width: 55%">
              <SplitPane split="horizontal">
                <template slot="paneL">
                  <div style="margin: 10px">
                    <el-row :gutter="20">
                      <el-col :span="16"><div ><ve-pie style="float: inherit" :data="chartData0" :settings="chartSettings"></ve-pie></div></el-col>
                      <el-col :span="7"><div>
                        <el-card>
                          个人高风险资产较多，请谨慎投资
                        </el-card>
                      </div></el-col>
                    </el-row>
                  </div>
                </template>
                <template slot="paneR">
                  <div style="margin: 10px">
                    <el-row :gutter="20">
                      <el-col :span="7"><div>
                        <el-card>
                          家庭高风险资产较多，请谨慎投资
                        </el-card>
                      </div></el-col>
                      <el-col :span="16"><div ><ve-pie style="float: inherit" :data="chartData" :settings="chartSettings"></ve-pie></div></el-col>
                    </el-row>
                  </div>
                </template>
              </SplitPane>
            </template>
            <template slot="paneR">
              <ve-liquidfill :data="chartData1" :settings="chartSettings1"></ve-liquidfill>
<!--              <el-col :span="8" v-for="(o, index) in 1" :key="o" :offset="index > 0 ? 2 : 0">-->
                <el-card :body-style="{ padding: '0px' }">
                  <img :src="newsData[indexX].img" class="image" style="width: 400px;height: 300px">
                  <div style="padding: 14px;">
                    <span>{{ newsData[indexX].title }}</span>
                    <div class="bottom clearfix">
                      <time class="time">{{ newsData[indexX].date }}</time>
                      <el-link type="primary" :href="newsData[indexX].url" target="_blank">详情</el-link>
                      <el-button type="text" class="button" @click="nextOne">下一条</el-button>
                    </div>
                  </div>
                </el-card>
<!--              </el-col>-->
            </template>
          </SplitPane>
        </template>
      </SplitPane>
    </div>
  </d2-container>

</template>

<script>
import Vue from 'vue'
import SplitPane from 'vue-splitpane'
import { mapState } from 'vuex'
// import echarts from 'echarts'
Vue.component('SplitPane', SplitPane)
export default {
  computed: {
    ...mapState('d2admin/user', [
      'info'
    ])
  },
  data () {
    this.chartSettings1 = {
      seriesMap: {
        风控值: {
          color: ['#EACACA'],
          itemStyle: {
            opacity: 0.2
          },
          emphasis: {
            itemStyle: {
              opacity: 0.8
            }
          },
          backgroundStyle: {
            color: '#A7C3D7'
          },
          label: {
            formatter (options) {
              const {
                seriesName,
                value
              } = options
              return `${seriesName}\n${value * 100}%`
            },
            fontSize: 40
          }
        }
      }
    }
    this.chartSettings = {
      // roseType: 'radius',
      radius: 150
    }
    return {
      newsData: [],
      indexX: 0,
      member: [
        { i: '0', color: '#EACACA', type: '股票', amount: '100', height: '50' },
        { i: '1', color: '#F0DFB6', type: '基金', amount: '100', height: '20' },
        { i: '2', color: '#A7C3D7', type: '现金', amount: '100', height: '20' },
        { i: '3', color: '#DFDFBD', type: '家庭基金', amount: '100', height: '20' }
      ],
      chartData1: {
        columns: ['risk', 'percent'],
        rows: [{
          risk: '风控值',
          percent: 0.89
        }]
      },
      chartData: {
        columns: ['类型', '金额'],
        rows: [
          { 类型: '1/1', 金额: 1393 },
          { 类型: '1/2', 金额: 3530 },
          { 类型: '1/3', 金额: 2923 }
        ]
      },
      chartData0: {
        columns: ['类型', '金额'],
        rows: [
          { 类型: '1/1', 金额: 1393 },
          { 类型: '1/2', 金额: 3530 },
          { 类型: '1/3', 金额: 2923 }
        ]
      }
    }
  },
  mounted () {
    // 加载完成后显示提示
    // this.getAmount()
    this.getPercent()
    this.getNews()
    this.showInfo()
  },
  methods: {
    // 显示提示
    // async getAmount () {
    //   try {
    //     const res1 = await this.$api.CASH_TOTAL(this.info.username)
    //     this.member[2].amount = res1.family
    //     const res2 = await this.$api.FUND_FAMILY({ userid: this.info.username, queryid: this.info.username })
    //     this.member[1].amount = res2.family
    //     const res3 = await this.$api.FAMILY_STOCK({ userid: this.info.username, queryid: '' })
    //     this.member[0].amount = res3.family
    //   } catch (error) {
    //     console.log(error)
    //   }
    // },
    getJSON (url) {
      var promise = new Promise((resolve, reject) => {
        var xhr = new XMLHttpRequest()
        xhr.open('GET', url)
        xhr.onreadystatechange = handler
        xhr.responseType = 'json'
        xhr.setRequestHeader('Accept', 'application/json')
        xhr.send()
        function handler () {
          if (this.readyState !== 4) {
            return
          }
          if (this.status === 200) {
            resolve(this.response)
          } else {
            reject(new Error(this.statusText))
          }
        }
      })
      return promise
    },
    getNews () {
      var url = 'http://newsapi.org/v2/top-headlines?country=us&apiKey=1da76f0b521746b0858e68f270836910'
      this.getJSON(url).then((json) => {
        console.log(json.articles[0])
        for (var i = 0; i < json.articles.length; i++) {
          this.newsData.push({
            date: json.articles[i].publishedAt,
            title: json.articles[i].title,
            content: json.articles[i].content,
            url: json.articles[i].url,
            img: json.articles[i].urlToImage
          })
        }
        console.log(this.newsData)
      }, (error) => {
        console.log(error)
      })
    },
    // 新闻详情
    detail () {

    },
    // 下一条新闻
    nextOne () {
      this.indexX = (this.indexX + 1) % 20
    },
    async getPercent () {
      try {
        const res = await this.$api.FAMILY_PERCENT({
          userid: this.info.username,
          searchId: this.info.username
        })
        this.chartData.rows[0].类型 = res.family[0][0]
        this.chartData.rows[1].类型 = res.family[1][0]
        this.chartData.rows[2].类型 = res.family[2][0]
        this.chartData.rows[0].金额 = res.family[0][1]
        this.chartData.rows[1].金额 = res.family[1][1]
        this.chartData.rows[2].金额 = res.family[2][1]
        this.member[2].amount = res.family[0][1]
        this.member[1].amount = res.family[2][1]
        this.member[0].amount = res.family[1][1]
        this.chartData0.rows[0].类型 = res.user[0][0]
        this.chartData0.rows[1].类型 = res.user[1][0]
        this.chartData0.rows[2].类型 = res.user[2][0]
        this.chartData0.rows[0].金额 = res.user[0][1]
        this.chartData0.rows[1].金额 = res.user[1][1]
        this.chartData0.rows[2].金额 = res.user[2][1]
      } catch (error) {
        console.log(error)
      }
    },
    // async getNews () {
    //   try {
    //     const res = await this.$api.FETCH_NEWS()
    //     console.log(res)
    //   } catch (error) {
    //     console.log(error)
    //   }
    // },
    showInfo () {
      this.$notify({
        title: '欢迎',
        message: 'FFMS家庭理财系统提醒您：请理性投资'
      })
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
