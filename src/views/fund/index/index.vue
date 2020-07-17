<template>
  <d2-container type="card">
    <template slot="header">
      <el-button shadow="hover" slot="header" type="primary" @click="dialogFormVisible = true">买入基金</el-button>
      <el-input v-model="searchInput" slot="header" placeholder="请输入内容" style="width: 300px">
        <template slot="prepend"></template>
      </el-input>
      <el-button slot="header" style="margin-bottom: 5px" @click="searchTable">搜索</el-button>
      <el-button type="primary" @click="exportExcel">
        <d2-icon name="download"/>
        导出 Excel
      </el-button>
      <el-card shadow="hover" style="background-color: #F0DFB6;float: right;width: 400px;height: 40px;padding-bottom: 16px;text-align: center">
        我的基金
        <d2-count-up style="font-size: 29px;" :end="1663.78" :decimals="2"/>
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
    <el-dialog title="买入基金" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="基金ID" :label-width="formLabelWidth">
          <el-input v-model="form.fundid" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="买入总价" :label-width="formLabelWidth">
          <el-input v-model="form.quantity" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="买入时净值" :label-width="formLabelWidth">
          <el-input v-model="form.price" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addFund">确 定</el-button>
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
        fundid: '',
        quantity: '',
        price: ''
      },
      formLabelWidth: '120px',
      searchInput: '',
      data: [{
        code: 0,
        currentValue: 0
      }],
      rowHandle: {
        remove: {
          icon: 'el-icon-upload',
          style: 'background-color: #5ab1ef; border-color: #5ab1ef',
          text: '清仓',
          size: 'small',
          confirm: true
        }
      },
      exportColumn: [
        { label: '代码', prop: 'code' },
        { label: '名称', prop: 'name' },
        { label: '购买人', prop: 'userid' },
        { label: '认购份数', prop: 'quantity' },
        { label: '净值', prop: 'currentValue' }
      ]
    }
  },
  methods: {
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
    getInfo (row) {
      this.$router.push({
        path: '/fund/details',
        query: { code: row.code, total: row.currentValue }
      })
    },
    async getTable () {
      const res = await this.$api.FUND_TABLE({ userid: this.info.username, queryid: '' })
      this.data = res
    },
    async addFund () {
      this.dialogFormVisible = false
      const res = this.$api.UPDATE_FUND({
        userid: this.info.username,
        fundid: this.form.fundid,
        quantity: this.form.quantity,
        price: this.form.price
      })
      if (res.status_code === '0') {
        this.$message({
          message: '已买入，请刷新后查看~',
          type: 'success'
        })
      }
      this.getTable()
      this.$forceUpdate()
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
      const res = this.$api.DEL_FUND({ userid: row.userid, fundcode: row.code, price: row.netWorth })
      console.log(res)
    },
    async searchTable () {
      const res = await this.$api.FUND_TABLE({ userid: this.info.username, queryid: this.searchInput })
      this.data = res
    }
  },
  mounted () {
    this.getTable()
  }
}
</script>

<style scoped>

</style>
