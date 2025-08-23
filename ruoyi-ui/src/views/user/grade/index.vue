<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="等级名称" prop="gradeName">
        <el-input
          v-model="queryParams.gradeName"
          placeholder="请输入等级名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:grade:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:grade:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:grade:remove']"
        >删除</el-button>
      </el-col>

      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="gradeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!--      <el-table-column label="ID" align="center" prop="id" />-->
      <el-table-column label="等级编号" align="center" prop="sortNum">
        <template slot-scope="scope">{{ scope.row.sortNum }}级</template>
      </el-table-column>
      <el-table-column label="等级名称" align="center" prop="gradeName" />
      <el-table-column label="参加费" align="center" prop="joinCost" />
      <el-table-column label="最低余额" align="center" prop="minBalance" />
      <el-table-column label="每天购买的产品数量" align="center" prop="buyProdNum" />
      <el-table-column label="% 最小奖金" align="center" prop="minBonus" />
      <el-table-column label="% 最大奖金" align="center" prop="maxBonus" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:grade:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:grade:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改用户等级对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="等级编号" prop="sortNum">
          <el-input v-model="form.sortNum" placeholder="请输入等级编号" disabled />
        </el-form-item>
        <el-form-item label="等级名称" prop="gradeName">
          <el-input v-model="form.gradeName" placeholder="请输入等级名称" disabled />
        </el-form-item>
        <el-form-item label="参加费" prop="joinCost">
          <el-input v-model="form.joinCost" placeholder="请输入参加费" />
        </el-form-item>
        <el-form-item label="最低余额" prop="minBalance">
          <el-input v-model="form.minBalance" placeholder="请输入最低余额" />
        </el-form-item>
        <el-form-item label="每天购买的产品数量" prop="buyProdNum">
          <el-input v-model="form.buyProdNum" placeholder="请输入每天购买的产品数量" />
        </el-form-item>
        <el-form-item label="最小奖金" prop="minBonus">
          <el-input v-model="form.minBonus" placeholder="请输入最小奖金" />
        </el-form-item>
        <el-form-item label="最大奖金" prop="maxBonus">
          <el-input v-model="form.maxBonus" placeholder="请输入最大奖金" />
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
import {
  listGrade,
  getGrade,
  delGrade,
  addGrade,
  updateGrade
} from "@/api/user/grade";

export default {
  name: "Grade",
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
      // 用户等级表格数据
      gradeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        sortNum: null,
        gradeName: null,
        joinCost: null,
        minBalance: null,
        buyProdNum: null,
        minBonus: null,
        maxBonus: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        gradeName: [
          { required: true, message: "等级名称不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询用户等级列表 */
    getList() {
      this.loading = true;
      listGrade(this.queryParams).then(response => {
        this.gradeList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        sortNum: null,
        gradeName: null,
        joinCost: null,
        minBalance: null,
        buyProdNum: null,
        minBonus: null,
        maxBonus: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加用户等级";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getGrade(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改用户等级";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateGrade(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addGrade(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm('是否确认删除用户等级编号为"' + ids + '"的数据项？')
        .then(function() {
          return delGrade(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "system/grade/export",
        {
          ...this.queryParams
        },
        `grade_${new Date().getTime()}.xlsx`
      );
    }
  }
};
</script>
