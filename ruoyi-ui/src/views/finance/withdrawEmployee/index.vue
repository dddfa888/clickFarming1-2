<template>
  <div class="app-container">
    <!-- 搜索区域 -->
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="用户姓名" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入用户姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="订单id" prop="orderId">
        <el-input
          v-model="queryParams.orderId"
          placeholder="请输入订单id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" @click="oneClick('0')"
          >一键同意员工提现</el-button
        >
      </el-col>
    </el-row>

    <!-- 表格区域 -->
    <el-table
      v-loading="loading"
      :data="withdrawList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column label="订单id" align="center" prop="orderId" />
      <el-table-column label="用户ID" align="center" prop="userId" />
      <!--      <el-table-column label="用户姓名" align="center" prop="userName" />-->
      <el-table-column label="用户姓名" align="center" prop="userName">
        <template slot-scope="scope">
          {{ scope.row.userName }}<br />
          <span
            :style="{ color: scope.row.userType === '0' ? 'red' : 'green' }"
          >
            {{
              scope.row.userType == "0"
                ? "虚拟的"
                : scope.row.userType == "1"
                ? "真实的"
                : "未知"
            }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="金额" align="center" prop="amount" />
      <el-table-column label="银行名称" align="center" prop="bankName" />
      <el-table-column
        label="银行账户名称"
        align="center"
        prop="bankAccountName"
      />
      <el-table-column
        label="银行账号"
        align="center"
        prop="bankAccountNumber"
      />
      <el-table-column label="类型" align="center" prop="type">
        <template slot-scope="scope">
          <el-tag
            :type="scope.row.type === 0 ? 'danger' : 'success'"
            disable-transitions
          >
            {{ scope.row.type == 0 ? "提现" : "充值" }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-tag
            :type="getStatusTagType(scope.row.status)"
            disable-transitions
          >
            {{ getStatusLabel(scope.row.status) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
        label="驳回原因"
        align="center"
        prop="reasonForRejection"
      />
      <el-table-column align="center" label="提现时间" prop="createTime" width="150" />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <template v-if="scope.row.status === 0">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-check"
              @click="handleApprove(scope.row)"
              v-hasPermi="['system:withdraw:edit']"
              >同意</el-button
            >
            <el-button
              size="mini"
              type="text"
              icon="el-icon-close"
              @click="handleReject(scope.row)"
              v-hasPermi="['system:withdraw:remove']"
              >驳回</el-button
            >
          </template>
          <template v-else>
            <span style="color: #999">无操作</span>
          </template>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 驳回原因弹窗 -->
    <el-dialog
      title="请输入驳回原因"
      :visible.sync="rejectDialogVisible"
      width="400px"
    >
      <el-input
        type="textarea"
        v-model="rejectReason"
        placeholder="请输入驳回原因"
        rows="4"
      />
      <div slot="footer" class="dialog-footer">
        <el-button @click="rejectDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitReject">确认驳回</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  oneClickAgree,
  agree,
  reject,
  listWithdraw,
} from "@/api/finance/withdraw";

export default {
  name: "Withdraw",
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      withdrawList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        userName: null,
        userType: '0', // 内部员工
        orderId: null,
      },
      rejectDialogVisible: false,
      rejectReason: "",
      currentRejectRow: null,
    };
  },
  created() {
    this.getList();
  },
  methods: {
    oneClick(e) {
      // 弹出确认框
      this.$confirm("是否确认一键通过？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          // 用户点击 "确定" 后执行请求
          oneClickAgree(e)
            .then((res) => {
              console.log(res);
              // 判断状态码是否为 200
              if (res.code === 200) {
                this.getList(); // 如果是200，调用 getList 方法
              }
            })
            .catch((error) => {
              console.error("请求失败:", error);
            });
        })
        .catch(() => {
          // 用户点击 "取消" 时的处理
          console.log("用户取消了一键通过");
        });
    },

    getStatusLabel(status) {
      switch (status) {
        case 0:
          return "审核中";
        case 1:
          return "成功";
        case 2:
          return "驳回";
        default:
          return "未知";
      }
    },
    getStatusTagType(status) {
      switch (status) {
        case 0:
          return "warning"; // 黄色
        case 1:
          return "success"; // 绿色
        case 2:
          return "danger"; // 红色
        default:
          return "info"; // 灰色
      }
    },
    /** 获取列表数据 */
    getList() {
      this.loading = true;
      listWithdraw(this.queryParams)
        .then((response) => {
          console.log(response, "response");
          this.withdrawList = response.rows;
          this.total = response.total;
          this.loading = false;
        })
        .catch(() => {
          this.loading = false;
        });
    },

    /** 搜索 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },

    /** 重置搜索 */
    resetQuery() {
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        userName: null,
        orderId: null,
      };
      this.getList();
    },

    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },

    /** 同意提现 */
    handleApprove(row) {
      this.$modal.confirm("确定同意该提现申请吗？").then(() => {
        agree({ id: row.id }).then(() => {
          this.$modal.msgSuccess("操作成功");
          this.getList();
        });
      });
    },

    /** 弹出驳回弹窗 */
    handleReject(row) {
      this.currentRejectRow = row;
      this.rejectReason = "";
      this.rejectDialogVisible = true;
    },

    /** 提交驳回原因 */
    submitReject() {
      if (!this.rejectReason) {
        this.$modal.msgError("请输入驳回原因");
        return;
      }

      reject({
        id: this.currentRejectRow.id,
        notes: this.rejectReason,
      }).then(() => {
        this.$modal.msgSuccess("驳回成功");
        this.rejectDialogVisible = false;
        this.getList();
      });
    },
  },
};
</script>
