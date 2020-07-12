<template>
  <d2-container type="card">
    <template slot="header">
      <el-button shadow="hover" slot="header" type="primary" @click="addInRow">买入股票</el-button>
      <el-input slot="header" placeholder="请输入内容" style="width: 300px">
        <template slot="prepend"></template>
      </el-input>
      <el-button slot="header" style="margin-bottom: 5px">搜索</el-button>
      <el-button type="primary" @click="exportExcel">
        <d2-icon name="download"/>
        导出 Excel
      </el-button>
      <el-card shadow="hover" style="background-color: #DFDFBD;float: right;width: 200px;height: 40px;padding-bottom: 16px">
        总金额
        <d2-count-up style="font-size: 30px;" :end="100" :decimals="2"/>
      </el-card>
    </template>
    <div style="height: 400px; margin: -16px;">
      <d2-crud
        :columns="columns"
        :data="data"
        :rowHandle="rowHandle"
        @row-remove="handleRowRemove"/>
    </div>
    </d2-container>
</template>

<script>
export default {
  data () {
    return {
      columns: [
        {
          title: '日期',
          key: 'date'
        },
        {
          title: '姓名',
          key: 'name'
        },
        {
          title: '地址',
          key: 'address'
        }
      ],
      data: [
        {
          date: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄',
          forbidRemove: true,
          showRemoveButton: true
        },
        {
          date: '2016-05-04',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1517 弄',
          forbidRemove: false,
          showRemoveButton: true
        },
        {
          date: '2016-05-01',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1519 弄',
          forbidRemove: false,
          showRemoveButton: false
        },
        {
          date: '2016-05-03',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1516 弄',
          forbidRemove: false,
          showRemoveButton: true
        }
      ],
      rowHandle: {
        remove: {
          icon: 'el-icon-upload',
          style: 'background-color: #5ab1ef; border-color: #5ab1ef',
          text: '卖出',
          size: 'small',
          fixed: 'right',
          confirm: true,
          show (index, row) {
            if (row.showRemoveButton) {
              return true
            }
            return false
          },
          disabled (index, row) {
            if (row.forbidRemove) {
              return true
            }
            return false
          }
        }
      }
    }
  },
  methods: {
    handleRowRemove ({ index, row }, done) {
      setTimeout(() => {
        console.log(index)
        console.log(row)
        this.$message({
          message: '卖出成功',
          type: 'success'
        })
        done()
      }, 300)
    }
  }
}
</script>

<style scoped>

</style>
