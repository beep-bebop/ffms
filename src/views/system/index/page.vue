<template>
  <d2-container class="page">
    <div style="height: 800px; margin: -16px;">
      <SplitPane :min-percent='20' :default-percent='30' split="vertical">
        <template slot="paneL" style="width: 80%">
          <div :key="item.i" v-for="(item) in member" style="margin: 20px">
            <el-card shadow="hover" :style="{'background-color': item.color, 'height': item.height}">
              {{item.i}}
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
                  <div style="margin: 10px;">
                  </div>
                </template>
                <template slot="paneR">
                  <div style="margin: 10px">
                    <el-row :gutter="20">
                      <el-col :span="7"><div>
                        <el-card>
                          aaaa
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
            </template>
          </SplitPane>
        </template>
      </SplitPane>
    </div>
<!--    <div style="height: 700px; margin: -16px;">-->
<!--      <SplitPane :min-percent='20' :default-percent='80' split="vertical">-->
<!--        <template slot="paneL">-->
<!--          <div class="inner">-->
<!--            <d2-grid-layout-->
<!--              v-bind="layout"-->
<!--              @layout-updated="layoutUpdatedHandler">-->
<!--              <d2-grid-item-->
<!--                v-for="(item, index) in layout.layout"-->
<!--                :key="index"-->
<!--                v-bind="item"-->
<!--                @resize="resizeHandler"-->
<!--                @move="moveHandler"-->
<!--                @resized="resizedHandler"-->
<!--                @moved="movedHandler">-->
<!--                <template v-if="item.i === '4'">-->
<!--                  <el-card shadow="hover" class="page_card">-->
<!--                  </el-card>-->
<!--                </template>-->
<!--                <template v-if="item.i === '5'">-->
<!--                  <el-card shadow="hover" class="page_card">-->
<!--                  </el-card>-->
<!--                </template>-->
<!--                <template v-if="item.i === '6'">-->
<!--                  <el-card shadow="hover" class="page_card">-->
<!--                    &lt;!&ndash;            资产分配建议&ndash;&gt;-->
<!--                    <ve-pie :data="chartData" :settings="chartSettings"></ve-pie>-->
<!--                  </el-card>-->
<!--                </template>-->
<!--                <template v-if="item.i < '4'">-->
<!--                  <el-card shadow="hover" class="page_card" :style="{'background-color': item.color}">-->
<!--                    {{item.type}}-->
<!--                    <div class="group">-->
<!--                      <d2-count-up style="font-size: 50px" :end=item.amount :decimals="2"/>-->
<!--                    </div>-->
<!--                  </el-card>-->
<!--                </template>-->
<!--              </d2-grid-item>-->
<!--            </d2-grid-layout>-->
<!--          </div>-->
<!--        </template>-->
<!--        <template slot="paneR">-->
<!--          <div>-->
<!--            <ve-liquidfill :data="chartData1" :settings="chartSettings1"></ve-liquidfill>-->
<!--          </div>-->
<!--        </template>-->
<!--      </SplitPane>-->

<!--    <ve-liquidfill :data="chartData1"></ve-liquidfill>-->
  </d2-container>

</template>

<script>
import Vue from 'vue'
import SplitPane from 'vue-splitpane'
Vue.component('SplitPane', SplitPane)
export default {
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
      roseType: 'radius',
      radius: 150
    }
    return {
      member: [
        { i: '0', color: '#EACACA', type: '股票', amount: '100', height: '50' },
        { i: '1', color: '#F0DFB6', type: '股票', amount: '100', height: '20' },
        { i: '2', color: '#A7C3D7', type: '股票', amount: '100', height: '20' },
        { i: '3', color: '#DFDFBD', type: '股票', amount: '100', height: '20' }
      ],
      chartData1: {
        columns: ['risk', 'percent'],
        rows: [{
          risk: '风控值',
          percent: 0.6
        }]
      },
      chartData: {
        columns: ['日期', '访问用户'],
        rows: [
          { 日期: '1/1', 访问用户: 1393 },
          { 日期: '1/2', 访问用户: 3530 },
          { 日期: '1/3', 访问用户: 2923 },
          { 日期: '1/4', 访问用户: 1723 },
          { 日期: '1/5', 访问用户: 3792 },
          { 日期: '1/6', 访问用户: 4593 }
        ]
      }
    }
  },
  mounted () {
    // 加载完成后显示提示
    this.showInfo()
  },
  methods: {
    // 显示提示
    showInfo () {
      this.$notify({
        title: '提示',
        message: '你可以按住卡片拖拽改变位置；或者在每个卡片的右下角拖动，调整卡片大小'
      })
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
