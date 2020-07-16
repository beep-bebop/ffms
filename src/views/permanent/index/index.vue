<template>
  <d2-container type="card">
    <template slot="header">
      <el-button shadow="hover" slot="header" type="primary" @click="addInRow">添加固定资产</el-button>
      <el-input slot="header" placeholder="请输入内容" style="width: 300px">
        <template slot="prepend"></template>
      </el-input>
      <el-button slot="header" style="margin-bottom: 5px">搜索</el-button>
      <el-button type="primary" @click="exportExcel">
        <d2-icon name="download"/>
        导出 Excel
      </el-button>
      <el-card shadow="hover" style="background-color: #DFDFBD;float: right;width: 200px;height: 40px;padding-bottom: 16px;text-align: center">
        总金额
        <d2-count-up style="font-size: 30px;" :end="566" :decimals="2"/>
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
          title: '种类',
          key: 'type'
        },
        {
          title: '描述',
          key: 'description'
        },
        {
          title: '资产',
          key: 'value'
        }
      ],
      data: [
        {
          date: '2020-07-15',
          name: 'wfy',
          type: '房产',
          description: '长沙市岳麓区麓山南路932号',
          value: '123',
          forbidRemove: true,
          showRemoveButton: true
        },
        {
          date: '2020-07-13',
          name: 'wfy',
          type: '房产',
          description: '上海市普陀区金沙江路 1517 弄',
          value: '321',
          forbidRemove: false,
          showRemoveButton: true
        },
        {
          date: '2020-07-12',
          name: 'wfy',
          type: '车辆',
          description: '疾速斧头眼镜蛇',
          value: '111',
          forbidRemove: false,
          showRemoveButton: true
        }
      ],
      rowHandle: {
        remove: {
          icon: 'el-icon-upload',
          style: 'background-color: #5ab1ef; border-color: #5ab1ef',
          text: '折现',
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
    addInRow () {
      this.$refs.d2Crud.showDialog({
        mode: 'add',
        template: {
          userId: {
            title: '姓名',
            value: this.info.username
          },
          time: {
            title: '日期',
            value: '2020-7-15'
          },
          description: {
            title: '描述',
            value: ''
          },
          type: {
            title: '类型',
            value: ''
          },
          amount_paid: {
            title: '金额',
            value: ''
          }
        }
      })
    },
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
