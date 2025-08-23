<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="标题" prop="titleName">
        <el-input
          v-model="queryParams.titleName"
          placeholder="请输入标题"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="语言" prop="language">
        <el-select v-model="queryParams.language" placeholder="请选择" @change="handleQuery">
          <el-option
            v-for="item in langList"
            :key="item.code"
            :label="item.name"
            :value="item.code">
          </el-option>
        </el-select>
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
          v-hasPermi="['system:global:add']"
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
          v-hasPermi="['system:global:edit']"
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
          v-hasPermi="['system:global:remove']"
        >删除</el-button>
      </el-col>
      <!--<el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:global:export']"
        >导出</el-button>
      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="globalList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="id" />
      <el-table-column label="标题" align="center" prop="titleName" />
      <el-table-column label="币种" align="center" prop="currencyType" />
      <el-table-column label="内容" align="left" prop="content" />
      <el-table-column label="标识图片" align="center" prop="iconImageUrl" >
        <template slot-scope="scope">
          <img class="settingListImg" :src="scope.row.iconImageUrl" alt="图片无法显示"></img>
        </template>
	  </el-table-column>
      <el-table-column label="链接支持" align="left" prop="linkSupport" />
      <el-table-column label="语言" align="center" prop="language" >
        <template slot-scope="scope">
          {{ langMap[scope.row.language] || scope.row.language }}
        </template>
	  </el-table-column>
      <el-table-column label="弹出图片" align="center" prop="ejectImageUrl" >
        <template slot-scope="scope">
          <img class="settingListImg" :src="scope.row.ejectImageUrl" alt="图片无法显示"></img>
        </template>
	  </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:global:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:global:remove']"
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

    <!-- 添加或修改设置全球化对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="标题" prop="titleName">
          <el-input v-model="form.titleName" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="币种" prop="currencyType">
          <el-select v-model="form.currencyType" placeholder="请选择">
            <el-option
                v-for="item in currencyTypeList"
                :key="item.code"
                :label="item.code"
                :value="item.code">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="内容">
          <el-input v-model="form.content" type="textarea" placeholder="请输入内容" :height="400" />
        </el-form-item>
        <el-form-item label="标识图片" prop="iconImageUrl">
          <ImageUpload ref="imageUploadIcon" :limit="fileNumLimitIcon" :fileSize="fileSizeMBIcon" :value="fileListInitIcon" @input="uploadSuccessIcon"></ImageUpload>
        </el-form-item>

        <el-form-item label="链接支持" prop="linkSupport">
          <el-input v-model="form.linkSupport" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="语言" prop="language">
          <el-select v-model="form.language" placeholder="请选择">
            <el-option
                v-for="item in langList"
                :key="item.code"
                :label="item.name"
                :value="item.code">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="弹出图片" prop="ejectImageUrl">
          <ImageUpload ref="imageUploadEject" :limit="fileNumLimitEject" :fileSize="fileSizeMBEject" :value="fileListInitEject" @input="uploadSuccessEject"></ImageUpload>
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
import { listGlobal, getGlobal, delGlobal, addGlobal, updateGlobal } from "@/api/setting/settingGlobal"

export default {
  name: "Global",
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
      // 设置全球化表格数据
      globalList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        titleName: null,
        currencyType: null,
        content: null,
        iconImageUrl: null,
        linkSupport: null,
        language: null,
        ejectImageUrl: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        titleName: [
          { required: true, message: "标题不能为空", trigger: "blur" }
        ],
        currencyType: [
          { required: true, message: "币种不能为空", trigger: "change" }
        ],
      },
      langList: [
        { code:'vi', name:'越南语' },
        { code:'zh', name:'中文' },
        { code:'en', name:'英语' },
        { code:'kr', name:'韩语' },
        { code:'jp', name:'日语' },
        { code:'rs', name:'俄语' },
        { code:'fr', name:'法语' }
      ],
      langMap: {
        'vi': '越南语',
        'zh': '中文',
        'en': '英语',
        'kr': '韩语',
        'jp': '日语',
        'rs': '俄语',
        'fr': '法语',
      },
      currencyTypeList: [
        { code:'USD' },
        { code:'EURO' }
      ],
      baseUrl: process.env.VUE_APP_BASE_API,
      //标识图片
      fileNumLimitIcon: 1, //文件数量限制
      fileSizeMBIcon: 1, //文件最大限制，1MB
      fileListInitIcon: '', //初始化文件列表
      //弹出图片
      fileNumLimitEject: 1, //文件数量限制
      fileSizeMBEject: 1, //文件最大限制，1MB
      fileListInitEject: '' //初始化文件列表
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询设置全球化列表 */
    getList() {
      this.loading = true
      listGlobal(this.queryParams).then(response => {
        this.globalList = response.rows
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
        id: null,
        titleName: null,
        currencyType: null,
        content: null,
        iconImageUrl: null,
        linkSupport: null,
        language: null,
        ejectImageUrl: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      }
      this.resetForm("form")
      this.fileListInitIcon = '';
      this.fileListInitEject = '';
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加设置全球化"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getGlobal(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改设置全球化"
        this.setFileListInitIcon();
        this.setFileListInitEject();
      })
    },

    //标识图片
    setFileListInitIcon(){
      this.fileListInitIcon = this.form.iconImageUrl || '';
    },
    // <ImageUpload> 上传图片成功后的回调函数，值是数组，内部元素是字符串（文件相对路径）
    uploadSuccessIcon(fileListStr){
      this.form.iconImageUrl = fileListStr;
    },
    //弹出图片
    setFileListInitEject(){
      this.fileListInitEject = this.form.ejectImageUrl || '';
    },
    // <ImageUpload> 上传图片成功后的回调函数，值是数组，内部元素是字符串（文件相对路径）
    uploadSuccessEject(fileListStr){
      this.form.ejectImageUrl = fileListStr;
    },

    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateGlobal(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addGlobal(this.form).then(response => {
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
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除设置全球化编号为"' + ids + '"的数据项？').then(function() {
        return delGlobal(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/global/export', {
        ...this.queryParams
      }, `global_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
<style>
	.settingListImg {
		width: 3rem;
		height: 3rem;
	}
</style>
