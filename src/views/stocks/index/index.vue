<template>
  <d2-container type="card">
    <template slot="header">
      <el-button shadow="hover" slot="header" type="primary" @click="dialogFormVisible = true">买入股票</el-button>
      <el-input v-model="searchInput" slot="header" placeholder="请输入内容" style="width: 300px">
        <template slot="prepend"></template>
      </el-input>
      <el-button slot="header" style="margin-bottom: 5px" @click="searchTable">搜索</el-button>
      <el-button type="primary" @click="exportExcel">
        <d2-icon name="download"/>
        导出 Excel
      </el-button>
      <el-card shadow="hover" style="background-color: #DFDFBD;float: right;width: 200px;height: 40px;padding-bottom: 16px;text-align: center">
        我的股票
        <d2-count-up style="font-size: 29px;" :end="208.45" :decimals="2"/>
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
          label="仓位"
          width="120">
        </el-table-column>
        <el-table-column
          prop="price"
          label="实时价格"
          width="300">
        </el-table-column>
        <el-table-column
          prop="currentValue"
          label="净值"
          width="120">
        </el-table-column>
        <el-table-column
          prop="changePercent"
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
<!--        @row-remove="handleRowRemove"/>-->
    </div>
    <el-dialog title="买入股票" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="股票ID" :label-width="formLabelWidth">
          <el-input v-model="form.stockid" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="买入份额" :label-width="formLabelWidth">
          <el-input v-model="form.amount" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="个股成本" :label-width="formLabelWidth">
          <el-input v-model="form.price" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addStock">确 定</el-button>
      </div>
    </el-dialog>
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
      dialogFormVisible: false,
      form: {
        stockid: '',
        amount: '',
        price: ''
      },
      searchInput: '',
      formLabelWidth: '120px',
      data: [{
        code: 0,
        currentValue: 0
      }],
      exportColumn: [
        { label: '代码', prop: 'code' },
        { label: '名称', prop: 'name' },
        { label: '购买人', prop: 'userid' },
        { label: '仓位', prop: 'quantity' },
        { label: '净值', prop: 'currentValue' }
      ]
    }
  },
  methods: {
    getInfo (row) {
      console.log('checkkkkkkkk' + row.code)
      this.$router.push({
        path: '/stocks/detail',
        query: { code: row.code }
      })
    },
    exportExcel () {
      this.$export.excel({
        columns: this.exportColumn,
        data: this.data
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
    handleRowRemove (row) {
      this.$confirm('确认清仓?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        setTimeout(() => {
          this.removeRow(row)
          console.log(row)
          this.getTable()
          this.$message({
            message: '已清仓',
            type: 'success'
          })
        }, 300)
      }).catch(() => {
      })
    },
    async addStock () {
      this.dialogFormVisible = false
      const res = this.$api.UPDATE_STOCK({
        userid: this.info.username,
        stockid: this.form.stockid,
        amount: this.form.amount,
        price: this.form.price
      })
      this.getTable()
      this.$forceUpdate()
      console.log(res)
    },
    async removeRow (row) {
      const res = this.$api.DEL_STOCK({
        userid: row.userid,
        code: row.code,
        price: row.price
      })
      console.log(res)
      this.getTable()
    },
    async searchTable () {
      const res = await this.$api.STOCK_TABLE({
        userid: this.info.username,
        queryid: this.searchInput
      })
      this.data = res
    },
    async getTable () {
      const res = await this.$api.STOCK_TABLE({ userid: this.info.username, queryid: '' })
      this.data = res
    }
  },
  created () {
    this.getTable()
  },
  mounted () {
    this.getTable()
  }
}
</script>

<style scoped>

</style>
