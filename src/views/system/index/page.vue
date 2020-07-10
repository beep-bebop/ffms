<template>
  <d2-container type="card" class="page">
    <template slot="header">区域划分</template>
    <div style="height: 800px; margin: -16px;">
      <SplitPane :min-percent='20' :default-percent='30' split="vertical">
        <template slot="paneL">
          <div style="margin: 20px;">
            <el-card shadow="hover" class="d2-card" style="background-color: #EACACA;margin: 20px">
              <el-row>
                <el-col>
                  <div>
                    <span style="font-size: 23px">股票</span>
                  </div>
                </el-col>
                <el-col>
                  <div class="group">
                    <d2-count-up style="font-size: 50px" :end="100" :decimals="2"/>
                  </div>
                </el-col>
              </el-row>
            </el-card>
            <el-card shadow="hover" class="d2-card" style="background-color: #F0DFB6;margin: 20px">
              <el-row>
                <el-col>
                  <div>
                    <span style="font-size: 23px">股票</span>
                  </div>
                </el-col>
                <el-col>
                  <div class="group">
                    <d2-count-up style="font-size: 50px" :end="100" :decimals="2"/>
                  </div>
                </el-col>
              </el-row>
            </el-card>
            <el-card shadow="hover" class="d2-card" style="background-color: #A7C3D7;margin: 20px">
              <el-row>
                <el-col>
                  <div>
                    <span style="font-size: 23px">股票</span>
                  </div>
                </el-col>
                <el-col>
                  <div class="group">
                    <d2-count-up style="font-size: 50px" :end="100" :decimals="2"/>
                  </div>
                </el-col>
              </el-row>
            </el-card>
            <el-card shadow="hover" class="d2-card" style="background-color: #DFDFBD;margin: 20px">
              <el-row>
                <el-col>
                  <div>
                    <span style="font-size: 23px">股票</span>
                  </div>
                </el-col>
                <el-col>
                  <div class="group">
                    <d2-count-up style="font-size: 50px" :end="100" :decimals="2"/>
                  </div>
                </el-col>
              </el-row>
            </el-card>
          </div>
        </template>
        <template slot="paneR">
          <SplitPane split="horizontal">
            <template slot="paneL"><div style="margin: 10px;">右上</div></template>
            <template slot="paneR"><div style="margin: 10px;">右下</div></template>
          </SplitPane>
        </template>
      </SplitPane>
    </div>
  </d2-container>
</template>

<script>
import Vue from 'vue'
import SplitPane from 'vue-splitpane'
Vue.component('SplitPane', SplitPane)
export default {
}
</script>

<style lang="scss" scoped>
.page {
  .logo {
    width: 120px;
  }
  .btn-group {
    color: $color-text-placehoder;
    font-size: 12px;
    line-height: 12px;
    margin-top: 0px;
    margin-bottom: 20px;
    .btn-group__btn {
      color: $color-text-sub;
      &:hover {
        color: $color-text-main;
      }
      &.btn-group__btn--link {
        color: $color-primary;
      }
    }
  }
}
</style>
