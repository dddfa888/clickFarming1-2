<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item :label="$t('userPage.account')" prop="loginAccount">
        <el-input
          v-model="queryParams.loginAccount"
          placeholder="请输入账号"
          clearable
          @keyup.enter.native="handleQuery"
          @input="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">{{ $t("pageCommon.search") }}</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">{{ $t("pageCommon.reset") }}</el-button>
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
          v-hasPermi="['system:user:add']"
        >{{ $t("pageCommon.add") }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:user:edit']"
        >{{ $t("pageCommon.update") }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:user:remove']"
        >{{ $t("pageCommon.delete") }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-edit"
          size="mini"
          @click="handleGotoWithdraw('staff')"
          v-hasPermi="['system:user:remove']"
        >{{ $t('userPage.withdrawCustomerBtn') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-edit"
          size="mini"
           @click="handleGotoWithdraw('customer')"
          v-hasPermi="['system:user:remove']"
        >{{ $t('userPage.withdrawEmployeeBtn') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="userList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="88" align="center" />
      <el-table-column :label="$t('userPage.column.invitationCode')" align="center" prop="invitationCode" >
       <template slot-scope="scope">
    <div>
      <p>{{ scope.row.invitationCode }}</p>
      <p>
       {{ scope.row.levelName }}
      </p>
    </div>
  </template>
  </el-table-column>
      <el-table-column
  :label="$t('userPage.column.loginAccount')"
  align="center"
  prop="loginAccount"
  :min-width="200"
  :show-overflow-tooltip="false"
>
  <template slot-scope="scope">
    <div style="white-space: nowrap;">
      {{ scope.row.loginAccount }}
      <span :style="{ color: scope.row.registerType === '0' ? 'red' : 'green', marginLeft: '4px' }">
        {{ scope.row.registerType == '0' ? $t('userPage.typeEmployee') : scope.row.registerType == '1' ? $t('userPage.typeGuest') : $t('userPage.typeUnknown') }}
      </span>
    </div>
  </template>
</el-table-column>
       <el-table-column :label="$t('userPage.column.SETTING_ORDER')" align="center" width="150px" >
        <template slot-scope="scope">
          <el-button @click="handleOpenOrederSet(scope.row)" style="cursor: pointer;color: #1890ff;border:none;">
            {{ $t("userPage.column.settingOrder") }}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column :label="$t('userPage.column.action')" :width="$t('userPage.colAct.width')" align="center">
        <template slot-scope="scope">
          <div class="action-buttons">
            <el-button
              @click="handleUpdateBalance(scope.row)"
              :style="{
                backgroundColor: '#00CFE8',
                borderColor: '#00CFE8',
                color: '#fff'
              }"
              size="small"
            >
              <!--更改账户余额-->
              {{ $t('userPage.colAct.changeBalence') }}
            </el-button>

            <el-button
              @click="handleOpenBank(scope.row)"
              size="small"
              :style="{
                backgroundColor: '#00CFE8',
                borderColor: '#00CFE8',
                color: '#fff'
              }"
            >
              <!--银行修改-->
              {{ $t('userPage.colAct.changeBank') }}
            </el-button>

            <el-button
              @click="handleRegisterType(scope.row)"
              type="primary"
              size="small"
              :style="{
                backgroundColor: scope.row.registerType === '0' ? 'red' : 'green',
                borderColor: scope.row.registerType === '0' ? 'red' : 'green'
              }"
            >
              <!--{{ scope.row.registerType === '0' ? '成为客人' : '成为员工' }}-->
              {{ scope.row.registerType === '0' ? $t('userPage.colAct.switchGuest') : $t('userPage.colAct.switchEmp') }}
            </el-button>
          </div>

          <div class="action-buttons">
            <el-button
              @click="handleOpenUserOrderList(scope.row)"
              size="small"
              :style="{
                backgroundColor: '#FF9F43',
                borderColor: '#FF9F43',
                color: '#fff'
              }"
            >
              <!--订单接收历史记录-->
              {{ $t('userPage.colAct.orderHistory') }}
            </el-button>


            <el-button
              @click="handleStatusChange(scope.row)"
              type="primary"
              size="small"
              :style="{
                backgroundColor: scope.row.status === 1 ? 'red' : 'green',
                borderColor: scope.row.status === 1 ? 'red' : 'green'
              }"
            >
              <!--{{ scope.row.status === 1 ? '账户锁定' : '解锁账户' }}-->
              {{ scope.row.status === 1 ? $t('userPage.colAct.lockAccount') : $t('userPage.colAct.unlockAccount') }}
            </el-button>

            <el-button
              @click="handleDelete(scope.row)"
              type="primary"
              size="small"
              :style="{
                backgroundColor: 'red',
                borderColor: 'red'
              }"
            >
              <!--删除账户-->
              {{ $t('userPage.colAct.deleteAccount') }}
            </el-button>
          </div>

          <div class="action-buttons">
            <el-button
              @click="handleListGroupInformation(scope.row)"
              :style="{
                backgroundColor: '#00CFE8',
                borderColor: '#00CFE8',
                color: '#fff'
              }"
              size="small"
            >
              <!--集团信息-->
              {{ $t('userPage.colAct.info') }}
            </el-button>

            <el-button
              @click="handleUpdate(scope.row)"
              size="small"
              :style="{
                backgroundColor: '#00CFE8',
                borderColor: '#00CFE8',
                color: '#fff'
              }"
            >
              <!--账户信息-->
              {{ $t('userPage.colAct.changeInfo') }}
            </el-button>

            <!--<el-button
              @click="handleOpenSetOrderNum(scope.row)"
              type="primary"
              size="small"
            >
              设置连单数量
            </el-button>-->

            <el-button
              @click="notifyOpenop(scope.row)"
              :style="{
                backgroundColor: '#00CFE8',
                borderColor: '#00CFE8',
                color: '#fff'
              }"
              size="small">
              <!--发信息-->
              {{ $t('userPage.colAct.sendMsg') }}
            </el-button>

          </div>
        </template>
      </el-table-column>
      <el-table-column
  :label="$t('userPage.column.inviterCode')"
  align="center"
  :min-width="200"
>
      <template slot-scope="scope">
        <div v-if="scope.row.inviterName">
          <div>{{ scope.row.inviterCode }}</div>
          <div>{{ scope.row.inviterName }}</div>
        </div>
        <div v-else>/</div>
      </template>
</el-table-column>


      <el-table-column style="white-space: nowrap;" :label="$t('userPage.column.phoneNumber')" align="center" :min-width="200" prop="phoneNumber" />
      <el-table-column :label="$t('userPage.column.accountBalance')" align="center" prop="accountBalance" />
       <el-table-column :label="$t('userPage.column.regsterTime')" align="center" prop="createTime" width="160" />
      <el-table-column :label="$t('userPage.column.lastLoginIp')" align="center" :min-width="100" >
        <template slot-scope="scope">
          <div v-if="scope.row.lastLoginIp" >
            <div>{{ scope.row.lastLoginIp }}  {{ scope.row.lastLoginIpAddress }}</div>
          </div>
          <div v-else>没有数据</div>
        </template>
      </el-table-column>
      <el-table-column :label="$t('userPage.column.userStatus')" align="center" prop="status" width="80">
        <template slot-scope="scope">
          <el-tag
            :type="scope.row.status === 1 ? 'success' : 'danger'"
          >
            <!--{{ scope.row.status === 1 ? '正常' : '已锁定' }}-->
            {{ scope.row.status === 1 ? $t('userPage.statusUnlock') : $t('userPage.statusLock') }}
          </el-tag>
        </template>
      </el-table-column>
      <!--<el-table-column style="white-space: nowrap;" :label="$t('userPage.column.userLevel')" align="center" :min-width="200" prop="levelName"  />-->



<!--      <el-table-column label="提现地址" align="center" prop="withdrawalAddress">-->
<!--        <template slot-scope="scope">-->
<!--          {{ scope.row.withdrawalAddress || '/' }}-->
<!--        </template>-->
<!--      </el-table-column>-->

<!--      <el-table-column label="银行名称" align="center" prop="bankName">-->
<!--        <template slot-scope="scope">-->
<!--          {{ scope.row.bankName || '/' }}-->
<!--        </template>-->
<!--      </el-table-column>-->

<!--      <el-table-column label="银行账户名称" align="center" prop="bankAccountName">-->
<!--        <template slot-scope="scope">-->
<!--          {{ scope.row.bankAccountName || '/' }}-->
<!--        </template>-->
<!--      </el-table-column>-->

<!--      <el-table-column label="银行账号" align="center" prop="bankAccountNumber">-->
<!--        <template slot-scope="scope">-->
<!--          {{ scope.row.bankAccountNumber || '/' }}-->
<!--        </template>-->
<!--      </el-table-column>-->

      <el-table-column :label="$t('userPage.column.brushNumber')" align="center" prop="brushNumber" />
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 弹出窗口《更改账户余额》 -->
    <el-dialog
        :title="$t('userPage.balForm.title')"
        :visible.sync="dialogBalance"
        width="30%"
        :before-close="handleCloseBalance"
    >
    <el-form ref="balanceForm" :model="balanceForm" :rules="balanceRules" label-width="120px">
      <el-form-item :label="$t('userPage.balForm.account')">
        <el-input readonly v-model="balanceForm.loginAccount"></el-input>
      </el-form-item>
      <el-form-item :label="$t('userPage.balForm.phone')">
        <el-input readonly v-model="balanceForm.phoneNumber"></el-input>
      </el-form-item>
      <el-form-item :label="$t('userPage.balForm.origin')">
        <el-input readonly v-model="balanceForm.originalBalance"></el-input>
      </el-form-item>
      <el-form-item  :label="$t('userPage.balForm.newNum')" prop="balance">
        <el-input @keyup.enter.native="submitBalanceForm" v-model="balanceForm.balance" ref="balanceInput" ></el-input>
      </el-form-item>
       <el-form-item :label="$t('userPage.balForm.selectReason')">
      <el-select v-model="selectedReason" placeholder="" @change="changeReason">
        <el-option :label="$t('userPage.balForm.noReason')" :value="$t('userPage.balForm.noReason')"></el-option>
        <el-option :label="$t('userPage.balForm.adjust')" :value="$t('userPage.balForm.adjust')"></el-option>
      </el-select>
    </el-form-item>
    <el-form-item :label="$t('userPage.balForm.or')" prop="reason">
      <el-input v-model="balanceForm.reason"></el-input>
    </el-form-item>
      <el-form-item style="display: flex;align-items: center;justify-content: center;">
        <el-button @click="handleCloseBalance">{{ $t('userPage.balForm.cancel') }}</el-button>
         <el-button type="primary" @click="submitBalanceForm">{{ $t('userPage.balForm.save') }}</el-button>
      </el-form-item>
    </el-form>


      <!-- <el-form ref="form" :model="balanceForm" label-width="80px">
        <el-form-item label="">
          <el-switch
              style="display: block"
              v-model="balanceForm.increaseDecrease"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-text="增加"
              inactive-text="减少">
          </el-switch>
        </el-form-item>
        <el-form-item label="金额">
          <el-input v-model="balanceForm.balance"></el-input>
        </el-form-item>

      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleCloseBalance">取 消</el-button>
        <el-button type="primary" @click="submitBalanceForm">确 定</el-button>
      </span> -->
    </el-dialog>

    <!-- 编辑银行信息 -->
    <el-dialog title="编辑银行信息" :visible.sync="showUpdateBank" width="600px" append-to-body>
      <el-form ref="updateBankForm" :model="updateBankForm" label-width="100px">
        <el-form-item label="银行名称" prop="bankName">
          <el-input v-model="updateBankForm.bankName" placeholder="请输入银行名称" />
        </el-form-item>
        <el-form-item label="银行账户名称" prop="bankAccountName">
          <el-input v-model="updateBankForm.bankAccountName" placeholder="请输入银行账户名称" />
        </el-form-item>
        <el-form-item label="银行账号" prop="bankAccountNumber">
          <el-input v-model="updateBankForm.bankAccountNumber" placeholder="请输入银行账号" />
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="saveBank">确 定</el-button>
        <el-button @click="closeBank">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="orderSetTitle" :visible.sync="showOrderSet" width="750px" append-to-body>
      <el-form ref="orderSetForm" :model="orderSetForm" >
        <el-form-item v-for="(item, index) in orderSetList" :key="index">
          配置 命令
          <input class="orderListInput" placeholder="0" type="number" step="1" v-model="item.num" :min="0" :max="1000000" :precision="0"/>
          命令 (0 表示禁用)
           <input class="orderListInput" placeholder="0" type="number" step="1" v-model="item.min" :min="0" :controls="false"/>
          <span>-</span>
           <input class="orderListInput" placeholder="0" type="number" step="1" v-model="item.max" :min="0" :controls="false"/>
        </el-form-item>
        </el-form>
      <div slot="footer" class="dialog-footer" style="display: flex;justify-content: space-evenly;align-items: center;">
        <el-button @click="closeOrederSet">取消</el-button>
        <el-button @click="saveOrederSet" type="primary">创建新的</el-button>
      </div>
    </el-dialog>


    <el-dialog v-loading="loadingInviteTable"
        title="集团信息"
        :visible.sync="dialogGroupInformation"
        width="40%"
        :before-close="handleCloseGroupInformation"
    >
      <el-table
          :data="tableData"
          style="width: 100%">
        <el-table-column
            prop="loginAccount"
            label="账号"
            width="180">
        </el-table-column>
        <el-table-column
            prop="levelName"
            label="等级"
            width="150">
        </el-table-column>
        <el-table-column
            prop="accountBalance"
            label="余额">
        </el-table-column>
        <el-table-column
            prop="status"
            label="状态">
          <template slot-scope="scope">
            <el-tag
                :type="scope.row.status === 1 ? 'success' : 'info'"
                disable-transitions>
              {{ scope.row.status === 1 ? '启用' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="登记时间" align="center" prop="createTime" width="180" />
      </el-table>
      <span slot="footer" class="dialog-footer">
<!--        <el-button @click="handleCloseGroupInformation">取 消</el-button>-->
<!--        <el-button type="primary" @click="submitBalanceForm">确 定</el-button>-->
      <pagination
        v-show="invitationTableTotal>0"
        :total="invitationTableTotal"
        :page.sync="queryParamsInvite.pageNum"
        :limit.sync="queryParamsInvite.pageSize"
        @pagination="handleListGroupInformation(clickedRow)"
      />
      </span>
    </el-dialog>

    <el-dialog :title="notifyTitle" :visible.sync="notifyOpen" width="500px" append-to-body>
      <el-form ref="notifyForm" :model="notifyForm" :rules="notifyRules" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="notifyForm.title" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="内容">
          <editor v-model="notifyForm.content" :min-height="192"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitNotifyForm">确 定</el-button>
        <el-button @click="notifyCancel">取 消</el-button>
      </div>
    </el-dialog>


    <el-dialog
        title="查看订单"
        :visible.sync="dialogUserOrder"
        width="90%"
        :before-close="handleCloseUserOrderList"
    >
      <el-table
          :data="orderListTableData"
          style="width: 100%">
        <el-table-column label="产品名称" align="center" prop="productName" width="400" />
        <el-table-column label="产品图片" align="center" prop="productImageUrl" >
          <template slot-scope="scope">
            <img class="orderListProdImg" :src="scope.row.productImageUrl" alt="图片无法显示"></img>
          </template>
        </el-table-column>
        <el-table-column label="单价" align="center" prop="unitPrice" />
        <el-table-column label="数量" align="center" prop="number" />
        <el-table-column label="总金额" align="center" prop="totalAmount" />
        <el-table-column label="利润" align="center" prop="profit" />
        <el-table-column label="退款金额" align="center" prop="refundAmount" />
        <el-table-column label="过程状态" align="center" >
          <template slot-scope="scope">
            <div>{{ formatStatus(scope.row) }}</div>
            <div>{{ scope.row.numSeq }} / {{ scope.row.numTarget }}</div>
          </template>
	    </el-table-column>
        <el-table-column label="创建时间" align="center" prop="createTime" />
      </el-table>
      <span slot="footer" class="dialog-footer">
<!-- <el-button @click="">取 消</el-button>-->
<!-- <el-button @click="" type="primary">确 定</el-button>-->
      </span>

      <pagination
        v-show="orderTotal>0"
        :total="orderTotal"
        :page.sync="queryParamsOrder.pageNum"
        :limit.sync="queryParamsOrder.pageSize"
        @pagination="handleOpenUserOrderList(clickedRow)"
      />
    </el-dialog>

    <el-dialog
        title="设置连单数量"
        :visible.sync="dialogOrderNum"
        width="30%"
        :before-close="handleCloseOrderNum"
    >
      <el-form ref="form" :model="orderNumForm" label-width="80px">
        <el-form-item label="连单数量">
          <el-input-number v-model="orderNumForm.multiOrderNum" :min="1" :max="9999999" :precision="0" ></el-input-number>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleCloseOrderNum">取 消</el-button>
        <el-button type="primary" @click="submitOrderNumForm">确 定</el-button>
      </span>
    </el-dialog>


    <!-- 添加或修改用户对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="账号" prop="loginAccount">
          <el-input
              v-model="form.loginAccount"
              placeholder="请输入账号"
          />
        </el-form-item>
        <el-form-item label="等级" prop="level">
<!--          <el-input v-model="form.level" placeholder="请输入等级" />-->

          <el-select v-model="form.level" placeholder="请选择">
            <el-option
                v-for="item in gradeList"
                :key="item.id"
                :label="item.gradeName"
                :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>



        <el-form-item label="登录密码" >
          <el-input
              v-model="form.loginPassword"
              :type="JudgingStatus ? 'text' : 'password'"
              placeholder="请输入登录密码"
          />
        </el-form-item>

        <el-form-item label="资金密码" >
          <el-input
              v-model="form.fundPassword"
              :type="JudgingStatus ? 'text' : 'password'"
              placeholder="请输入资金密码"
          />
        </el-form-item>



        <el-form-item label="类型" prop="registerType">
          <!--          <el-input v-model="form.registerType" placeholder="请输入用户类型" />-->
          <el-select v-model="form.registerType" placeholder="请选择">
            <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="电话号码" prop="phoneNumber">
          <el-input v-model="form.phoneNumber" placeholder="请输入电话号码" />
        </el-form-item>
        <el-form-item label="邀请人代码" prop="inviterCode">
          <el-input v-model="form.inviterCode" placeholder="请输入邀请人代码" />
        </el-form-item>
        <el-form-item label="提现地址" prop="withdrawalAddress">
          <el-input v-model="form.withdrawalAddress" placeholder="请输入提现地址" />
        </el-form-item>



        <el-form-item label="银行名称" prop="bankName">
          <el-input v-model="form.bankName" placeholder="请输入银行名称" />
        </el-form-item>
        <el-form-item label="银行账户名称" prop="bankAccountName">
          <el-input v-model="form.bankAccountName" placeholder="请输入银行账户名称" />
        </el-form-item>
        <el-form-item label="银行账号" prop="bankAccountNumber">
          <el-input v-model="form.bankAccountNumber" placeholder="请输入银行账号" />
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
import {getAllSuperiorUids,changeBalance,setBalance,setStatus, listUser, getUser, delUser, addUser, updateUser, setRegisterType, getOrderList,updateMultiOrderNum,selectByUserId,updateMUserSimple,updateOrederSet} from "@/api/user/user"
import { listGrade } from "@/api/user/grade"
import {  addNotify } from "@/api/notify/notify"

export default {
  name: "User",
  data() {
    return {
      orderSetTitle:"个人配置",
      showOrderSet:false,
      orderSetForm: {
        userId: '',
        orderSetData: ''
      },
      orderSetList: [
          { num:undefined, min:undefined, max:undefined },
          { num:undefined, min:undefined, max:undefined },
          { num:undefined, min:undefined, max:undefined },
          { num:undefined, min:undefined, max:undefined },
          { num:undefined, min:undefined, max:undefined },
          { num:undefined, min:undefined, max:undefined },
          { num:undefined, min:undefined, max:undefined },
          { num:undefined, min:undefined, max:undefined },
          { num:undefined, min:undefined, max:undefined },
          { num:undefined, min:undefined, max:undefined }
      ],
      showUpdateBank: false,
      updateBankForm: {
        uid: '',
        bankName: '',
        bankAccountName: '',
        bankAccountNumber: ''
      },
      notifyOpen: false, // 弹框是否显示
      notifyTitle: "发送通知", // 弹框标题
      notifyForm: { // 表单数据
        userId: '',
        title: '',
        content: ''
      },
      notifyRules: { // 表单验证规则
        title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
        content: [{ required: true, message: '请输入内容', trigger: 'blur' }]
      },
      notifyOptions: [], // 用户列表选项
      tableData: [],
      orderListTableData: [],
      dialogGroupInformation: false,
      dialogUserOrder: false,
      dialogOrderNum: false,
      balanceForm:{
        uid:  "",
        loginAccount:"",
        phoneNumber: "",
        originalBalance:"",
        balance: '',
        reason: ''
        //increaseDecrease: true,
        //bankAccountNumber:"",
        //brushNumber:"",
      },
      selectedReason: '',
      balanceRules: { // 表单验证规则
        balance: [{ required: true, message: '请输入金钱数额', trigger: 'blur' }],
        reason: [{ required: true, message: '请输入理由', trigger: 'blur' }]
      },
      orderNumForm:{
        uid: '',
        multiOrderNum: 0
      },
      dialogBalance: false,
      //操作
      operate: "",
      operateTitle: "",
      operateOpen: false,
      operateForm: "",
      options: [{
        value: '1',
        label: '客人'
      }, {
        value: '0',
        label: '员工'
      }],
      // 遮罩层
      loading: true,
      // 《集团信息》表 加载状态
      loadingInviteTable: false,
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
      JudgingStatus:true,
      // 用户表格数据
      userList: [],
      gradeList: [],
      // 弹出层标题
      title: "",

      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        level: null,
        loginAccount: null,
        loginPassword: null,
        fundPassword: null,
        withdrawalAddress: null,
        registerType: null,
        phoneNumber: null,
        phoneNumberType: null,
        accountBalance: null,
        invitationCode: null,
        inviter: null,
        inviterCode: null,
        inviterName: null,
        status: null,
        bankName: null,
        bankAccountName: null,
        bankAccountNumber: null,
        higherUid: null,
        lastLoginIp: null,
        lastLoginIpAddress: null,
        deleteStatus: null,
        brushNumber: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        phoneNumber: [
          { required: true, message: "电话号码不能为空", trigger: "blur" }
        ],
        registerType: [
          { required: true, message: "用户类型不能为空", trigger: "blur" }
        ],

        level: [
          { required: true, message: "等级不能为空", trigger: "blur" }
        ],
        loginAccount: [
          { required: true, message: "账号不能为空", trigger: "blur" }
        ],
        loginPassword: [
          { required: true, message: "登录密码不能为空", trigger: "blur" }
        ],
        fundPassword: [
          { required: true, message: "资金密码不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "状态 1:启用 0:禁用不能为空", trigger: "change" }
        ],
      },
      queryParamsOrder: {
        pageNum: 1,
        pageSize: 10,
        userId: '',
      },
      orderTotal: 0,
      queryParamsInvite: {
        pageNum: 1,
        pageSize: 10,
        inviterCode: '',
      },
      invitationTableTotal:0,
      clickedRow: '',
      baseUrl: process.env.VUE_APP_BASE_API,
      processStatusMap: {
	    'Waiting': '未付',
	    'Success': '成功'
      }
    }
  },
  created() {
    this.getList()
    this.getGradeList()
  },
  mounted()
  {
 this.selectedReason = this.$t('userPage.balForm.noReason');
  },
  watch: {
  dialogBalance(val) {
    if (val) {
      this.$nextTick(() => {
        // 聚焦 el-input 组件内部的原生 input
        this.$refs.balanceInput.focus();
        // 或选中全部内容：
        // this.$refs.balanceInput.$el.querySelector('input').select();
      });
    }
  }
}
,
  methods: {
    //打开《订单设置》
    handleOpenOrederSet(row){
      let that = this;
      that.showOrderSet=true
      that.orderSetForm.userId = row.uid;
      that.orderSetForm.orderSetData = '';
      that.orderSetList.forEach(r => {
        r.num = undefined;
        r.min = undefined;
        r.max = undefined;
      })

      selectByUserId(row.uid).then(response => {
        //查询结果在 this.orderSetList 中回显
        let data = response.data;
        let diff = data.length-that.orderSetList.length;
        if(diff > 0){
          while(diff > 0){
            that.orderSetList.push({num:undefined, min:undefined, max:undefined });
            diff--;
          }
        }
        for(let i in data){
          that.orderSetList[i].num = data[i].orderNum;
          that.orderSetList[i].min = data[i].minNum;
          that.orderSetList[i].max = data[i].maxNum;
        }
      });
    },
    //关闭《订单设置》
    closeOrederSet(){
      this.showOrderSet = false;
    },
    //保存《订单设置》
    saveOrederSet(){
      let that = this;
      let str = '';
      for(let i in that.orderSetList){
        let row = that.orderSetList[i];
        if(row.num==undefined || row.num==null || row.num=='' || row.num=='0'){
	        continue ;
        }
        str = str + '#' + row.num + '_' + row.min + '_' + row.max;
      }
      if(str.length>0){
        str = str.substr(1);
      }
      that.orderSetForm.orderSetData = str;
      updateOrederSet(that.orderSetForm).then(response => {
        that.$modal.msgSuccess('保存成功');
        that.showOrderSet = false;
      }).catch(error => {
        that.$modal.msgError('保存失败');
      });
    },

    //打开《银行修改》
    handleOpenBank(row){
      this.showUpdateBank = true;
      this.updateBankForm.uid = row.uid
      this.updateBankForm.bankName = row.bankName;
      this.updateBankForm.bankAccountName = row.bankAccountName;
      this.updateBankForm.bankAccountNumber = row.bankAccountNumber;
    },
    //保存《银行修改》
    saveBank(){
        updateMUserSimple(this.updateBankForm).then(response => {
          this.$modal.msgSuccess('修改成功');
          this.showUpdateBank = false;
          this.getList();
        })
    },
    //关闭《银行修改》
    closeBank(){
      this.showUpdateBank = false;
    },

    //打开提现审核页面
    handleGotoWithdraw(row){
      if(row==="staff"){
        this.$router.push({path:"/user/withdrawCustomer"})
      }else {
        this.$router.push({path:"/user/withdrawEmployee"})
      }
    },
    notifyOpenop(e){
      console.log(e)
      this.notifyOpen = true
      this.notifyForm.userId = e.uid
    },
    // 发送通知的方法
    submitNotifyForm() {
      this.$refs.notifyForm.validate((valid) => {
        if (valid) {
          addNotify(this.notifyForm).then(response => {
            this.$modal.msgSuccess("新增成功");
            this.notifyOpen = false;
            this.getList();
          }).catch(error => {
            this.$modal.msgError("新增失败");
          });
        } else {
          this.$modal.msgError("请检查表单输入");
        }
      });
    },
    // 关闭弹框
    notifyCancel() {
      this.notifyOpen = false;
    },
    handleCloseGroupInformation(){
      this.dialogGroupInformation = false

    },
    handleListGroupInformation(row) {
      let that = this;
      this.dialogGroupInformation = true;

      // 调用封装好的 API，传入参数对象
      /*getAllSuperiorUids({ inviterCode: row.inviterCode }).then(res => {
        console.log('上级 UID 列表:', res.data);
        this.tableData = res.data
        // 如果你需要将数据赋值到页面上显示，例如：
      })*/
      that.clickedRow = row;
      that.loadingInviteTable = true
      that.queryParamsInvite.inviterCode = row.invitationCode;
      listUser(that.queryParamsInvite).then(response => {
        that.tableData = response.rows
        that.invitationTableTotal = response.total
        that.loadingInviteTable = false
      })
    },

    //用户订单列表
    handleCloseUserOrderList(){
      this.dialogUserOrder = false
    },
    handleOpenUserOrderList(row){
      let that = this;
      that.clickedRow = row;
      that.dialogUserOrder = true;
      that.queryParamsOrder.userId = row.uid;
      // 调用封装好的 API，传入参数对象
      getOrderList(that.queryParamsOrder).then(res => {
        that.orderListTableData = res.rows;
        that.orderTotal = res.total
      })
    },
    // 订单过程状态 转换值
    formatStatus(row){
      return this.processStatusMap[row.processStatus] || row.processStatus;
    },


    //关闭设置连单数量
    handleCloseOrderNum(){
      this.dialogOrderNum = false
    },
    //打开设置连单数量
    handleOpenSetOrderNum(row){
      this.orderNumForm.uid = row.uid;
      this.orderNumForm.multiOrderNum = row.multiOrderNum || 1;
      this.dialogOrderNum = true
    },
    //提交设置连单数量
    submitOrderNumForm(){
      updateMultiOrderNum(this.orderNumForm).then(res => {
        if(res.code != 200){
          this.$message.error('修改失败')
          return
        }
        this.handleCloseOrderNum()
        //this.getList()
        this.$message.success('修改成功')
      })
    },

    submitBalanceForm(){
      let that = this;
      this.$refs.balanceForm.validate(valid => {
        if (valid) {
          let form = {
            uid : that.balanceForm.uid,
            balance : that.balanceForm.balance,
            reason : that.balanceForm.reason
          };
          changeBalance(form).then(res => {
            if(res.code != 200){
              this.$message.error("修改失败")
              return
            }
            this.handleCloseBalance()
            this.getList()
            this.$message.success("修改成功")
          })
        }
      })
    },
    handleCloseBalance(){
      this.dialogBalance = false
      let m = this.balanceForm;
      for(let k in m){
        m[k] = ''
      }
      this.selectedReason = ''
    },
    handleUpdateBalance(row){
      this.balanceForm.uid = row.uid
      this.balanceForm.loginAccount=row.loginAccount
      this.balanceForm.phoneNumber=row.phoneNumber
      this.balanceForm.originalBalance=row.accountBalance
      this.balanceForm.balance=''
      this.balanceForm.reason=this.$t('userPage.balForm.noReason')
      //this.balanceForm.bankAccountNumber=row.bankAccountNumber
      this.selectedReason = this.$t('userPage.balForm.noReason')
      this.dialogBalance = true
    },
    changeReason(value){
      this.balanceForm.reason = this.balanceForm.reason+value;
    },
    /** 下拉列表操作 */
    handleCommand(command) {
      const { item, row } = command;
      this.operate = item.value;
      this.operateTitle = item.label;
      this.operateForm = row;

      // 如果方法存在，则执行
      if (typeof this[item.value] === 'function') {
        this[item.value](row);
      }
    },
    handleRegisterType(row) {
      const currentType = row.registerType === "0" ? "员工" : "客人";
      const targetType = row.registerType === "0" ? "客人" : "员工";
      const newTypeValue = row.registerType === "0" ? "1" : "0";

      this.$confirm(
          `是否确认将该用户从【${currentType}】改为【${targetType}】？`,
          '提示',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }
      ).then(() => {
        // 切换 registerType 的值
        row.registerType = newTypeValue;

        // 可选：发送 API 请求更新数据库
        this.updateRegisterType(row);

        this.$message.success(`已将用户类型修改为【${targetType}】`);
      }).catch(() => {
        this.$message.info('操作取消');
      });
    },
    updateRegisterType(e){
      setRegisterType(e.uid).then(response=>{
        if(response.code === 200){
          this.getList()
        }
      })
    },
    handleStatusChange(row) {
      // 弹出确认框，询问是否锁定或解锁账户
      this.$confirm(
        row.status === 1 ? '确定要解锁该账户吗？' : '确定要锁定该账户吗？',
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }
      )
        .then(() => {
          // 用户点击确定后执行
          this.toggleUserStatus(row);
        })
        .catch(() => {
          // 用户点击取消时的操作
          console.log('操作已取消');
        });
    },

    toggleUserStatus(row) {
      // 切换账户状态，1为启用，0为禁用
      const newStatus = row.status === 1 ? 0 : 1; // 启用状态为1，禁用状态为0
      setStatus(row.uid, newStatus).then(response => {
        if (response.code === 200) {
          this.getList(); // 刷新用户列表
        }
      }).catch((error) => {
        console.error('请求出错:', error);
      });
    },
    getGradeList() {
      this.loading = true
      listGrade().then(response => {
        this.gradeList = response.rows
      })
    },
    /** 查询用户列表 */
    getList() {
      this.loading = true
      listUser(this.queryParams).then(response => {
        this.userList = response.rows
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
      this.JudgingStatus = true
      this.form = {
        uid: null,
        level: null,
        loginAccount: null,
        loginPassword: null,
        fundPassword: null,
        withdrawalAddress: null,
        registerType: null,
        phoneNumber: null,
        phoneNumberType: null,
        accountBalance: null,
        invitationCode: null,
        inviter: null,
        inviterCode: null,
        inviterName: null,
        status: null,
        bankName: null,
        bankAccountName: null,
        bankAccountNumber: null,
        higherUid: null,
        lastLoginIp: null,
        lastLoginIpAddress: null,
        deleteStatus: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        brushNumber: null
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
      this.ids = selection.map(item => item.uid)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.JudgingStatus = true
      this.title = "添加用户"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const uid = row.uid || this.ids
      getUser(uid).then(response => {
        console.log(response.data)
         this.form = {
        uid: response.data.uid,
        level: response.data.level,
        loginAccount: response.data.loginAccount,
        loginPassword: null,
        fundPassword:null ,
        withdrawalAddress: response.data.withdrawalAddress,
        registerType: response.data.registerType,
        phoneNumber: response.data.phoneNumber,
        phoneNumberType: response.data.phoneNumberType,
        accountBalance: response.data.accountBalance,
        invitationCode: response.data.invitationCode,
        inviter: response.data.inviter,
        inviterCode: response.data.inviterCode,
        inviterName: response.data.inviterName,
        status: response.data.status,
        bankName: response.data.bankName,
        bankAccountName: response.data.bankAccountName,
        bankAccountNumber: response.data.bankAccountNumber,
        higherUid: response.data.higherUid,
        lastLoginIp: response.data.lastLoginIp,
        lastLoginIpAddress: response.data.lastLoginIpAddress,
        deleteStatus: response.data.deleteStatus,
        createBy: response.data.createBy,
        createTime: response.data.createTime,
        updateBy: response.data.updateBy,
        updateTime: response.data.updateTime,
        brushNumber: response.data.brushNumber
      }
        this.open = true
        this.JudgingStatus = false

        this.title = "修改用户"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.uid != null) {
            updateUser(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addUser(this.form).then(response => {
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
      const uids = row.uid || this.ids
      this.$modal.confirm('是否确认删除用户编号为"' + uids + '"的数据项？').then(function() {
        return delUser(uids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/user/export', {
        ...this.queryParams
      }, `user_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
<style>
.action-buttons {
  display: flex;
  flex-wrap: wrap;
  gap: 2px;
  justify-content: flex-start; /* 左对齐 */
  margin: 5px 0;
}


.orderListProdImg {
		width: 3rem;
		height: 3rem;
	}
.orderListInput
{
  width: 7rem;
  height: 2rem;
  margin: 0rem 0.5rem;
}

/* 覆盖 el-table 横向滚动条高度 */
.el-table__body-wrapper::-webkit-scrollbar {
  height: 26px; /* 设置滚动条高度 */
}

.el-table__body-wrapper::-webkit-scrollbar-track {
  background: #f5f5f5;
}

.el-table__body-wrapper::-webkit-scrollbar-thumb {
  background-color: #c1c1c1;
  border-radius: 8px;
  border: 4px solid transparent;
  background-clip: content-box;
}
</style>
