<template>
  <d2-container type="card">
    <template slot="header">
      <el-button shadow="hover" slot="header" type="primary" @click="addInRow">买入基金</el-button>
      <el-input slot="header" placeholder="请输入内容" style="width: 300px">
        <template slot="prepend"></template>
      </el-input>
      <el-button slot="header" style="margin-bottom: 5px">搜索</el-button>
      <el-button type="primary" @click="exportExcel">
        <d2-icon name="download"/>
        导出 Excel
      </el-button>
      <el-card shadow="hover" style="background-color: #DFDFBD;float: right;width: 200px;height: 40px;padding-bottom: 16px">
        我的基金
        <d2-count-up style="font-size: 29px;" :end="100" :decimals="2"/>
      </el-card>
    </template>
    <div style="height: 400px; margin: -16px;">
      <el-table
        :data="data"
        style="width: 100%">
        <el-table-column
          prop="code"
          label="代码"
          width="150">
        </el-table-column>
        <el-table-column
          prop="name"
          label="名称"
          width="120">
        </el-table-column>
        <el-table-column
          prop="userid"
          label="购买人"
          width="120">
        </el-table-column>
        <el-table-column
          prop="quantity"
          label="认购份数"
          width="120">
        </el-table-column>
        <el-table-column
          prop="netWorth"
          label="当前单位净值"
          width="300">
        </el-table-column>
        <el-table-column
          prop="currentValue"
          label="净值"
          width="120">
        </el-table-column>
        <el-table-column
          prop="dayGrowth"
          label="日涨跌幅"
          width="120">
        </el-table-column>
        <el-table-column
          label="操作"
          width="100">
          <template slot-scope="scope">
            <el-button @click="handleRowRemove(scope.row)" type="text" size="small">清仓</el-button>
            <el-button type="text" size="small" @click="getInfo(scope.row)">详情</el-button>
          </template>
        </el-table-column>
      </el-table>
<!--      <d2-crud-->
<!--        :columns="columns"-->
<!--        :data="data"-->
<!--        :rowHandle="rowHandle"-->
<!--        @row-dbclick="getInfo"-->
<!--        @row-remove="handleRowRemove"/>-->
    </div>
    </d2-container>
</template>

<script>
import { mapState } from 'vuex'

export default {
  computed: {
    ...mapState('d2admin/user', [
      'info'
    ])
  },
  data () {
    return {
      columns: [
        {
          title: '代码',
          key: 'code'
        },
        {
          title: '名称',
          key: 'name'
        },
        {
          title: '购买人',
          key: 'userid'
        },
        {
          title: '认购份数',
          key: 'quantity'
        },
        {
          title: '当前单位净值',
          key: 'netWorth'
        },
        {
          title: '净值',
          key: 'currentValue'
        },
        {
          title: '日涨跌幅',
          key: 'dayGrowth'
        }
      ],
      data: [],
      rowHandle: {
        remove: {
          icon: 'el-icon-upload',
          style: 'background-color: #5ab1ef; border-color: #5ab1ef',
          text: '清仓',
          size: 'small',
          confirm: true
        }
        // custom: [
        //   {
        //     text: '详情',
        //     icon: 'el-icon-info',
        //     size: 'small',
        //     emit: 'getInfo'
        //   }
        // ]
      }
    }
  },
  methods: {
    getInfo (row) {
      console.log('checkkkkkkkk' + row.code)
      this.$router.push({
        path: '/fund/details',
        params: { code: row.code }
      })
    },
    async getTable () {
      console.log('aaaaaaa' + this.info.username)
      const res = await this.$api.FUND_TABLE({ userid: this.info.username, queryid: '' })
      this.data = res
    },
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
    handleRowRemove (row) {
      this.$confirm('确认清仓?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        setTimeout(() => {
          this.removeRow(row)
          console.log(row)
          this.$message({
            message: '已清仓',
            type: 'success'
          })
        }, 300)
        this.getTable()
      }).catch(() => {
      })
    },
    async removeRow (row) {
      const res = this.$api.DEL_FUND({ userid: row.userid, fundcode: row.code })
      console.log(res)
    }
  },
  mounted () {
    this.getTable()
  }
}
</script>

<style scoped>

</style>
