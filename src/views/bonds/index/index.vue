<template>
  <d2-container type="card">
    <template slot="header">区域划分</template>
    <div style="height: 400px; margin: -16px;">
      <SplitPane :min-percent='20' :default-percent='30' split="vertical">
        <template slot="paneL">
          <div style="margin: 10px;">左</div>
        </template>
        <template slot="paneR">
          <div style="margin: 10px;">右</div>
        </template>
      </SplitPane>
    </div>
  </d2-container>
</template>

<script>
import menu from '@/menu/modules/bonds'
export default {
  menu
}
</script>

<style scoped>

</style>
