<template>
  <d2-container type="card" class="page">
    <d2-grid-layout
      v-bind="layout"
      @layout-updated="layoutUpdatedHandler">
      <d2-grid-item
        v-for="(item, index) in layout.layout"
        :key="index"
        v-bind="item"
        @resize="resizeHandler"
        @move="moveHandler"
        @resized="resizedHandler"
        @moved="movedHandler">
        <template v-if="item.i === '4'">
          <el-card shadow="hover" class="page_card">
            {{item.i}}
          </el-card>
        </template>
        <template v-if="item.i === '5'">
          <el-card shadow="hover" class="page_card">
            {{item.i}}
          </el-card>
        </template>
        <template v-if="item.i === '6'">
          <el-row>
            <el-col :span="8" v-for="(o, index) in 1" :key="o" :offset="index > 0 ? 2 : 0">
              <el-card :body-style="{ padding: '0px' }">
                <img :src="newsData[indexX].img" class="image">
                <div style="padding: 14px;">
                  <span>{{ newsData[indexX].title }}</span>
                  <div class="bottom clearfix">
                    <time class="time">{{ newsData[indexX].date }}</time>
                     <el-link type="primary" :href="newsData[indexX].url" target="_blank">详情</el-link>
                    <el-button type="text" class="button" @click="nextOne">下一条</el-button>
                  </div>
                </div>
              </el-card>
            </el-col>
          </el-row>
        </template>
        <template v-if="item.i < '4'">
          <el-card shadow="hover" class="page_card" :style="{'background-color': item.color}">
            {{item.type}}
            <div class="group">
              <d2-count-up style="font-size: 50px" :end=item.amount :decimals="2"/>
            </div>
          </el-card>
        </template>
      </d2-grid-item>
    </d2-grid-layout>
  </d2-container>
</template>

<script>
import Vue from 'vue'
import SplitPane from 'vue-splitpane'
import { GridLayout, GridItem } from 'vue-grid-layout'
Vue.component('d2-grid-layout', GridLayout)
Vue.component('d2-grid-item', GridItem)
Vue.component('SplitPane', SplitPane)
export default {
  data () {
    return {
      layout: {
        layout: [
          { x: 0, y: 0, w: 4, h: 3, i: '0', color: '#EACACA', type: '股票', amount: '100' },
          { x: 0, y: 3, w: 4, h: 3, i: '1', color: '#F0DFB6', type: '股票', amount: '100' },
          { x: 0, y: 6, w: 4, h: 3, i: '2', color: '#A7C3D7', type: '股票', amount: '100' },
          { x: 0, y: 9, w: 4, h: 3, i: '3', color: '#DFDFBD', type: '股票', amount: '100' },
          { x: 4, y: 0, w: 4, h: 6, i: '4' },
          { x: 8, y: 5, w: 2, h: 5, i: '5' },
          { x: 0, y: 10, w: 10, h: 10, i: '6' }
        ],
        colNum: 12,
        rowHeight: 30,
        isDraggable: true,
        isResizable: true,
        isMirrored: false,
        verticalCompact: true,
        margin: [20, 20],
        useCssTransforms: true
      },
      newsData: [],
      indexX: 0
    }
  },
  mounted () {
    // 加载完成后显示提示
    this.showInfo()
    this.getNews()
  },
  methods: {
    log (arg1 = 'log', ...logs) {
      if (logs.length === 0) {
        // console.log(arg1)
      } else {
        // console.group(arg1)
        logs.forEach(e => {
          // console.log(e)
        })
        // console.groupEnd()
      }
    },
    // 获取新闻json数据
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
    // 截取json字符串
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
      this.indexX = (this.indexX+1) % 20
    },
    // 显示提示
    showInfo () {
      this.$notify({
        title: '提示',
        message: '你可以按住卡片拖拽改变位置；或者在每个卡片的右下角拖动，调整卡片大小'
      })
    },
    // 测试代码
    layoutUpdatedHandler (newLayout) {
      console.group('layoutUpdatedHandler')
      newLayout.forEach(e => {
        // console.log(`{'x': ${e.x}, 'y': ${e.y}, 'w': ${e.w}, 'h': ${e.h}, 'i': '${e.i}'},`)
      })
      // console.groupEnd()
    },
    resizeHandler (i, newH, newW) {
      this.log('resizeHandler', `i: ${i}, newH: ${newH}, newW: ${newW}`)
    },
    moveHandler (i, newX, newY) {
      this.log('moveHandler', `i: ${i}, newX: ${newX}, newY: ${newY}`)
    },
    resizedHandler (i, newH, newW, newHPx, newWPx) {
      this.log('resizedHandler', `i: ${i}, newH: ${newH}, newW: ${newW}, newHPx: ${newHPx}, newWPx: ${newWPx}`)
    },
    movedHandler (i, newX, newY) {
      this.log('movedHandler', `i: ${i}, newX: ${newX}, newY: ${newY}`)
    }
  }
}
</script>

<style lang="scss" scoped>
  .page {
    .vue-grid-layout {
      background-color: $color-bg;
      border-radius: 4px;
      margin: -10px;
      .page_card {
        height: 100%;
        @extend %unable-select;
      }
      .vue-resizable-handle {
        opacity: .3;
        &:hover{
          opacity: 1;
        }
      }
    }
  }
</style>
