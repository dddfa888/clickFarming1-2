<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="用户名称" prop="uid">
        <el-input
          v-model="queryParams.loginAccount"
          placeholder="请输入用户名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>


      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
<!--        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>-->
      </el-form-item>
    </el-form>


    <el-table v-loading="loading" :data="recordsList" @selection-change="handleSelectionChange">
<!--      <el-table-column type="selection" width="55" align="center" />-->
      <el-table-column label="uuid" align="center" prop="uuid" />
      <el-table-column label="用户名称" align="center" prop="userName" />
      <el-table-column label="类型" align="center" prop="type">
        <template slot-scope="scope">
          <span :style="{ color: scope.row.type === 0 ? 'green' : 'red' }">
            {{ scope.row.type === 0 ? '收入' : '支出' }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="金额" align="center" prop="amount" />
      <el-table-column label="描述" align="center" prop="description" />
      <el-table-column label="账变前" align="center" prop="accountForward" />
      <el-table-column label="账变后" align="center" prop="accountBack" />
      <el-table-column label="账变后" align="center" prop="createTime" />

    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改账变记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户uid" prop="uid">
          <el-input v-model="form.uid" placeholder="请输入用户uid" />
        </el-form-item>
        <el-form-item label="金额" prop="amount">
          <el-input v-model="form.amount" placeholder="请输入金额" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" placeholder="请输入描述" />
        </el-form-item>
        <el-form-item label="关联id 比如 如果transaction_type为1 则此处应该存 服务器订单的id(m_service_order)" prop="relatedId">
          <el-input v-model="form.relatedId" placeholder="请输入关联id 比如 如果transaction_type为1 则此处应该存 服务器订单的id(m_service_order)" />
        </el-form-item>
        <el-form-item label="账变前" prop="accountForward">
          <el-input v-model="form.accountForward" placeholder="请输入账变前" />
        </el-form-item>
        <el-form-item label="账变后" prop="accountBack">
          <el-input v-model="form.accountBack" placeholder="请输入账变后" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listRecords, getRecords, delRecords, addRecords, updateRecords } from "@/api/log/records"

export default {
  name: "Records",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 账变记录表格数据
      recordsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        uid: null,
        type: null,
        amount: null,
        description: null,
        transactionType: null,
        relatedId: null,
        accountForward: null,
        accountBack: null,
        loginAccount: ""
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        uid: [
          { required: true, message: "用户uid不能为空", trigger: "blur" }
        ],
        type: [
          { required: true, message: "类型 0收入 1支出不能为空", trigger: "change" }
        ],
        amount: [
          { required: true, message: "金额不能为空", trigger: "blur" }
        ],
        createTime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询账变记录列表 */
    getList() {
      this.loading = true
      listRecords(this.queryParams).then(response => {
        this.recordsList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        uuid: null,
        uid: null,
        type: null,
        amount: null,
        description: null,
        createTime: null,
        updateTime: null,
        transactionType: null,
        relatedId: null,
        accountForward: null,
        accountBack: null
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.uuid)
      this.single = selection.length!==1
      this.multiple = !selection.length
  },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加账变记录"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const uuid = row.uuid || this.ids
      getRecords(uuid).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改账变记录"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.uuid != null) {
            updateRecords(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addRecords(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const uuids = row.uuid || this.ids
      this.$modal.confirm('是否确认删除账变记录编号为"' + uuids + '"的数据项？').then(function() {
        return delRecords(uuids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/records/export', {
        ...this.queryParams
      }, `records_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
