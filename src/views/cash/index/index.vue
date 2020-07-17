<template>
  <d2-container type="card">
    <template slot="header">
      <el-button shadow="hover" slot="header" type="primary" @click="addInRow">新增收入</el-button>
      <el-button shadow="hover" slot="header" type="info" style="margin-right: 15px" @click="addOutRow">新增支出</el-button>
      <el-input v-model="searchInput" slot="header" placeholder="请输入内容" style="width: 300px">
        <template slot="prepend"></template>
      </el-input>
      <el-button slot="header" style="margin-bottom: 5px" @click="searchData">搜索</el-button>
      <el-button type="primary" @click="exportExcel">
        <d2-icon name="download"/>
        导出 Excel
      </el-button>
      <el-card shadow="hover" style="background-color: #DFDFBD;float: right;width: 400px;height: 40px;padding-bottom: 16px;text-align: center">
        我的现金
        <d2-count-up v-model=this.cash style="font-size: 28px;" :end=this.cash :decimals="2"/>
      </el-card>
    </template>
    <div style="height: 900px; margin: -16px;">
      <SplitPane :min-percent='20' :default-percent='40' split="vertical">
        <template slot="paneL" style="width: 400px;">
              <div class="inner">
                <d2-crud
                  ref="d2Crud"
                  add-title="新增"
                  :columns="columns1"
                  :data="data1"
                  :options="options"
                  :form-options="formOptions"
                  :pagination="pagination1"
                  @pagination-current-change="paginationCurrentChange1"
                  :loading="loading1"
                  @row-add="handleRowAdd"
                  @dialog-cancel="handleDialogCancel">
                </d2-crud>
              </div>
            </template>
        <template slot="paneR">
          <SplitPane split="horizontal">
            <template slot="paneL">
              <div class="inner">
                <ve-bar :data="chartData" v-bind="pubSetting" :settings="chartSettings"></ve-bar>
              </div>
            </template>
            <template slot="paneR">
              <div style="margin: 10px">
                <d2-crud
                  ref="d2Crud"
                  add-title="新增"
                  :columns="columns"
                  :data="data"
                  :options="options"
                  :form-options="formOptions"
                  :pagination="pagination"
                  @pagination-current-change="paginationCurrentChange"
                  :loading="loading"
                  @row-add="handleRowAdd"
                  @dialog-cancel="handleDialogCancel">
                </d2-crud>
              </div>
            </template>
          </SplitPane>
        </template>
      </SplitPane>
    </div>
  </d2-container>
</template>

<script>
import Vue from 'vue'
import pluginExport from '@d2-projects/vue-table-export'
import { mapState } from 'vuex'
Vue.use(pluginExport)
export default {
  computed: {
    ...mapState('d2admin/user', [
      'info'
    ])
  },
  data () {
    this.chartSettings = {
      labelMap: {
        date: '日期',
        in: '收入',
        out: '支出',
        profit: '净值'
      }
    }
    return {
      searchInput: '',
      loading1: false,
      loading: false,
      cash: '0',
      pagination1: {
        currentPage: 1,
        pageSize: 12,
        total: 0
      },
      pagination: {
        currentPage: 1,
        pageSize: 5,
        total: 0
      },
      chartData: {
        columns: ['date', 'in', 'out', 'profit'],
        rows: [
          { date: '1', in: 1393, out: 1093, profit: 0.32 },
          { date: '2', in: 3530, out: 3230, profit: 0.26 },
          { date: '3', in: 2923, out: 2623, profit: 0.76 },
          { date: '4', in: 1723, out: 1423, profit: 0.49 },
          { date: '5', in: 3792, out: 3492, profit: 0.323 },
          { date: '6', in: 4593, out: 4293, profit: 0.78 },
          { date: '7', in: 4593, out: 4293, profit: 0.78 }
        ]
      },
      columns1: [
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
            { text: '饮食', value: '饮食' },
            { text: '购物', value: '购物' },
            { text: '游玩', value: '游玩' },
            { text: '学习', value: '学习' },
            { text: '其他', value: '其他' },
            { text: '股票', value: '股票' },
            { text: '基金', value: '基金' }
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
            { text: '工资', value: '工资' },
            { text: '奖金', value: '奖金' },
            { text: '股票', value: '股票' },
            { text: '基金', value: '基金' },
            { text: '其他', value: '其他' }
          ],
          filterMethod (value, row) {
            return row.type === value
          },
          filterPlacement: 'bottom-end',
          width: '100'
        },
        {
          title: '金额',
          key: 'income',
          width: '100'
        }
      ],
      exportColumn1: [
        { label: '日期', prop: 'time' },
        { label: '姓名', prop: 'userId' },
        { label: '金额', prop: 'amount_paid' },
        { label: '描述', prop: 'description' },
        { label: '类型', prop: 'type' }
      ],
      exportColumn: [
        { label: '日期', prop: 'time' },
        { label: '姓名', prop: 'userId' },
        { label: '金额', prop: 'income' },
        { label: '描述', prop: 'description' },
        { label: '类型', prop: 'type' }
      ],
      data1: [{
        code: 0,
        currentValue: 0
      }],
      data: [{
        code: 0,
        currentValue: 0
      }],
      formOptions: {
        saveLoading: false
      },
      options: {
        rowClassName ({ row }) {
          if (row.disburseId > 0) {
            return 'warning-row'
          } else if (row.income > 0) {
            return 'success-row'
          }
          return ''
        }
      }
    }
  },
  methods: {
    // addRow () {
    //   this.$refs.d2Crud.showDialog({
    //     mode: 'add'
    //   })
    // },
    paginationCurrentChange1 (currentPage) {
      this.pagination1.currentPage = currentPage
      this.fetchData1()
    },
    paginationCurrentChange (currentPage) {
      this.pagination.currentPage = currentPage
      this.fetchData()
    },
    fetchData () {
      this.loading = true
      this.$api.FAMILY_INCOME(this.info.username).then(res => {
        this.data = res.data.reverse()
        this.pagination.total = res.data.length
        this.loading = false
      }).catch(err => {
        console.log('err', err)
        this.loading = false
      })
    },
    fetchData1 () {
      this.loading = true
      this.$api.FAMILY_OUTCOME(this.info.username).then(res => {
        this.data1 = res.data.reverse()
        this.pagination1.total = res.data.length
        this.loading1 = false
      }).catch(err => {
        console.log('err', err)
        this.loading = false
      })
    },
    async totalCash () {
      const res = await this.$api.CASH_TOTAL(this.info.username)
      this.cash = res.user
      console.log('cashhhhhhh' + this.cash)
    },
    searchData () {

    },
    // async getIncome () {
    //   this.loading = true
    //   const res = await this.$api.FAMILY_INCOME(this.info.username)
    //   this.data = res.data.reverse()
    //   this.pagination.total = res.data.length
    //   this.loading = false
    // },
    // async getOutcome () {
    //   this.loading1 = true
    //   const res = await this.$api.FAMILY_OUTCOME(this.info.username)
    //   this.data1 = res.data.reverse()
    //   console.log('outttttttt' + res.data)
    //   this.pagination1.total = res.data.length
    //   this.loading1 = false
    // },
    async getChart () {
      const res = await this.$api.DAYS_CASH(this.info.username)
      for (var i = 0; i < this.chartData.rows.length; i++) {
        this.chartData.rows[i].out = res.data[i][1]
        this.chartData.rows[i].in = res.data[i][2]
        this.chartData.rows[i].profit = res.data[i][3]
      }
    },
    exportExcel () {
      this.$export.excel({
        columns: this.exportColumn1,
        data: this.data1
        // header: '导出 Excel',
        // merges: ['A1', 'E1']
      })
        .then(() => {
          this.$message('导出表格成功')
        })
      this.$export.excel({
        columns: this.exportColumn,
        data: this.data
        // header: '导出 Excel',
        // merges: ['A1', 'E1']
      })
        .then(() => {
          this.$message('导出表格成功')
        })
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
            value: '2020-07-15'
          },
          description: {
            title: '描述',
            value: ''
          },
          type: {
            title: '类型(工资|奖金|股票|基金|其他)',
            value: ''
          },
          income: {
            title: '金额',
            value: ''
          }
        }
      })
    },
    addOutRow () {
      this.$refs.d2Crud.showDialog({
        mode: 'add',
        template: {
          userId: {
            title: '姓名',
            value: this.info.username
          },
          time: {
            title: '日期',
            value: '2020-07-15'
          },
          description: {
            title: '描述',
            value: ''
          },
          type: {
            title: '类型(饮食|购物|游玩|学习|其他)',
            value: ''
          },
          amount_paid: {
            title: '金额',
            value: ''
          }
        }
      })
    },
    async addIncome (row) {
      const res = await this.$api.ADD_INCOME({
        income: row.income,
        userId: this.info.username,
        time: row.time,
        description: row.description,
        type: row.type
      })
      console.log(res)
    },
    async addOutcome (row) {
      const res = await this.$api.ADD_OUTCOME({
        amount_paid: row.amount_paid,
        userId: this.info.username,
        time: row.time,
        description: row.description,
        type: row.type
      })
      console.log('out' + res)
    },
    handleRowAdd (row, done) {
      this.formOptions.saveLoading = true
      setTimeout(() => {
        if (row.income != null) {
          console.log('添加收入')
          this.addIncome(row)
        }
        if (row.amount_paid != null) {
          console.log('添加支出')
          this.addOutcome(row)
        }
        this.$message({
          message: '保存成功',
          type: 'success'
        })
        this.getChart()
        // this.getIncome()
        // this.getOutcome()
        this.fetchData()
        this.fetchData1()
        done({
        })
        this.formOptions.saveLoading = false
      }, 300)
    },
    handleDialogCancel (done) {
      this.$message({
        message: '取消保存',
        type: 'warning'
      })
      done()
    }
  },
  mounted () {
    this.getChart()
    // this.getIncome()
    // this.getOutcome()
    this.fetchData()
    this.fetchData1()
    this.totalCash()
  }
}
</script>

<style>
  .inner {
    position: absolute;
    top: 0;
    right:  20px;
    bottom: 20px;
    left: 20px;
  }
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
  .el-table .warning-row {
    background: rgba(90, 177, 239, 0.5);
  }
  .el-table .success-row {
    background: rgba(25, 212, 174, 0.5);
  }
</style>
