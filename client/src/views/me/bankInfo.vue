<template>
  <div class="company-intro">
    <HeaderBar :title="t('银行信息')" />
    <!-- 选择银行：支持输入 + 下拉 -->
    <div class="form-group select-wrapper">
      <label :class="{ floated: bankName }">{{ t("选择银行") }}</label>
      <div class="input-select-combo">
        <!-- <input v-model="accountName" type="text" class="input" placeholder="" /> -->
        <BankSelect
          v-model="bankName"
          :options="bankOptions"
          :placeholder="t('请输入或选择银行')"
          :show="showinput"
        />
      </div>
    </div>

    <div class="form-group">
      <label :class="{ floated: accountName }">{{ t("账号名称") }}</label>
      <input v-model="accountName" type="text" class="input" :disabled="showinput" />
    </div>
    <div class="form-group">
      <label :class="{ floated: accountNumber }">{{ t("账号") }}</label>
      <input v-model="accountNumber" type="text" class="input" :disabled="showinput" />
    </div>
    <div class="form-group" v-if="showBank">
      <label :class="{ floated: fundPassword }">{{ t("提款密码") }}</label>
      <input v-model="fundPassword" type="text" class="input" :disabled="showinput" />
    </div>

    <button class="submit-btn" @click="submit" v-if="showBank">{{ t("确认") }}</button>

    <!--银行选择弹窗 -->
    <van-popup v-model="showBankPicker" position="bottom">
      <van-picker
        :columns="bankOptions"
        @confirm="onBankConfirm"
        @cancel="showBankPicker = false"
        show-toolbar
        :title="t('请选择银行')"
      />
    </van-popup>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import HeaderBar from "../../components/HeaderBar.vue";
import BankSelect from "../../components/BankSelect.vue"; // 组件路径按实际改
import { getUserInfo, updateUserInfo } from "../../api/index";
import { useI18n } from "vue-i18n";
import { notify } from "../../utils/notify.js";

// onMounted(() => {

// });

const { t } = useI18n();

const accountName = ref("");
const accountNumber = ref("");
const bankName = ref("");
const fundPassword = ref("");
const showinput = ref(false);
const showBank = ref(true);

const bankOptions = [
  "UOB",
  "PVCOMBANK",
  "VIET BANK",
  "BANVIET BANK",
  "THE SHANGHAI COMMERCIAL & SAVINGS BANK LTD",
  "MONETA MONEY BANK",
  "KIEN LONG BANK",
  "NGÂN HÀNG YUANTA",
  "KIOPBANK",
  "BANK OF TAIWAN",
  "郵政",
  "FIRST BANK",
  "ABBANK",
  "EXIMBANK",
  "SCB NGÂN HÀNG SÀI GÒN",
  "HIGOBANK",
  "SHB BANK",
  "FIRST BANK",
  "THẺ NGÂN HÀNG SEABANK",
  "DONGA BANK",
  "TAIWAN BUSINESS BANK",
  "CHANG HWA BANK",
  "TP BANK",
  "NCB BANK",
  "VIET CAPITAL BANK",
  "SHINHAN BANK",
  "PG BANK",
  "MSB BANK",
  "HDBANK",
  "NAMABANK",
  "POSB",
  "OCBC",
  "CITIBANK",
  "MAYBANK",
  "PUBLIC BANK",
  "CIMB BANK",
  "HONG LEONG BANK",
  "HSBC",
  "BANK RAKYT",
  "WOORI BANK",
  "UNION BANK",
  "DBS",
  "LIENVIETPOSTBANK",
  "REMITTANCE CARD",
  "OCB BANK",
  "MARITIME BANK",
  "BANK CENTRAL ASIA",
  "株式会社みずほ銀行",
  "株式会社三菱東京UFJ銀行",
  "株式会社三井住友銀行",
  "株式会社りそな銀行",
  "株式会社埼玉りそな銀行",
  "日本銀行",
  "みなと銀行",
  "北おおさか信用金庫",
  "大地みらい信用金庫",
  "株式会社ジャパンネット銀行",
  "ソニー銀行株式会社",
  "ABN AMRO BANK",
  "BANK CITY OSAKA",
  "SHINKIN OSAKA",
  "SBI NET BANK",
  "ITS SEVEN BANK",
  "YUCHO BANK",
  "南洋商業銀行",
  "FUKUOKA BANK",
  "USDT",
  "SAITAMAKEN SHINKIN BANK",
  "AGRIBANK",
  "MB BANK",
  "PG BANK",
  "HANABANK",
  "TECHCOMBANK",
  "BSN",
  "SIAM COMMERCIAL BANK PCL.",
  "SACOMBANK",
  "TPBANK",
  "南都銀行",
  "広島銀行",
  "ゆうちょ银行",
  "VIB BANK",
  "GMO AOZORA NET BANK",
  "BIDV",
  "HOKURIKU BANK",
  "VIETTIN BANK",
  "TOMATO BANK",
  "SEVENBANK",
  "HIROSHIMA BANK",
  "VP BANK",
  "ACB BANK",
  "VIETCOMBANK",
  "AND",
  "WESTPAC",
  "NAB",
  "SUNCORP",
  "NAZ",
  "AND",
  "WESTPAC",
  "NAB",
  "SUNCORP",
  "NAZ",
  "ANZ",
  "CBA",
  "INDOVINA BANK",
  "VIETA BANK",
  "CAKE BY VPBANK",
  "BAC A BANK",
  "LIOBANK (OCB)",
  "COOPBANK",
  "LP Bank"
];

function querySearch(queryString, cb) {
  const results = bankOptions
    .filter(bank => bank.includes(queryString))
    .map(item => ({ value: item }));
  cb(results);
}

function onSelectBank(event) {
  accountName.value = event.target.value;
}

function onBankConfirm(value) {
  accountName.value = value;
  showBankPicker.value = false;
}

// 获取用户信息
getUserInfo().then(res => {
  accountName.value = res.data.bankAccountName || "";
  accountNumber.value = formatBankCard(res.data.bankAccountNumber) || "";
  bankName.value = res.data.bankName || "";
  if (
    !res.data.bankAccountName &&
    !res.data.bankAccountNumber &&
    !res.data.bankName
  ) {
    showBank.value = true;
    showinput.value = false;
  } else {
    showBank.value = false;
    showinput.value = true;
  }
});

function formatBankCard(cardNo) {
  if (!cardNo) return "";
  const clean = cardNo.replace(/\s+/g, "");
  if (clean.length <= 8) return clean;
  const start = clean.slice(0, 4);
  const end = clean.slice(-4);
  return `${start} **** **** ${end}`;
}

function submit() {
  console.log(
    accountName.value,
    accountNumber.value,
    bankName.value,
    fundPassword.value
  );
  updateUserInfo({
    bankAccountName: accountName.value,
    bankAccountNumber: accountNumber.value,
    bankName: bankName.value,
    fundPassword: fundPassword.value
  }).then(res => {
    console.log(res);
    if (res.code == 200) {
      globalThis.$notify({
        message: t("操作成功"),
        type: "success",
        duration: 2000
      });
      // 刷新页面
      window.location.reload();
    } else {
      globalThis.$notify({
        message: t(res.msg),
        type: "error",
        duration: 2000
      });
    }
  });
  // 示例提交逻辑
  if (
    !accountName.value ||
    !accountNumber.value ||
    !bankName.value ||
    !fundPassword.value
  ) {
    // notify(t("请填写所有字段"));
    return;
  }
}
</script>

<style scoped>
.company-intro {
  background: url("../../assets/img/background-D7o_xTde.png") no-repeat center
    center;
  background-size: cover;
  height: 100vh;
  overflow-y: auto;
}
.form-group {
  position: relative;
  margin: 25px;
  border: 1px solid #ccc;
  padding: 16px 10px 6px;
  background-color: transparent;
  font-family: sans-serif;
  overflow: visible; /* 避免 label 超出边框被裁剪 */
}

.submit-btn {
  width: 80%;
  color: #fff;
  margin-left: 10%;
  border: 1px solid #6b6f6f;
  border-radius: 4px;
  text-align: center;
  min-height: 12vw;
  background-image: linear-gradient(to bottom, #3a405c, #365354);
  box-shadow: 5px 8px 10px #272c3e, 0 0 10px #272c3e;
}

.input {
  width: 100%;
  border: none;
  outline: none;
  font-size: 16px;
  background: transparent;
  color: #fff;
  padding: 0;
  z-index: 2;
}

label {
  position: absolute;
  left: 10px;
  top: -13px;
  color: #aaa;
  font-size: 14px;
  pointer-events: none;
  transition: 0.2s ease all;
  background-color: #011727; /* 默认透明 */
  padding: 0 4px;
}

.input:focus + label,
.floated {
  top: -8px; /* 上移多一点 */
  font-size: 12px;
  color: #42b983;
  background-color: white; /* 💡 或改成页面背景色 */
}

@media screen and (min-width: 768px) {
  .company-intro {
    background: url("../../assets/img/background-D7o_xTde.png") no-repeat center
      center;
    background-size: cover;
    height: 100vh;
    width: 540px;
    margin: 0 auto;
    overflow-y: auto;
  }
  .form-group {
    position: relative;
    margin: 25px;
    border: 1px solid #ccc;
    padding: 16px 10px 6px;
    background-color: transparent;
    font-family: sans-serif;
    overflow: visible; /* 避免 label 超出边框被裁剪 */
  }

  .submit-btn {
    width: 80%;
    color: #fff;
    margin-left: 10%;
    border: 1px solid #6b6f6f;
    border-radius: 4px;
    text-align: center;
    min-height: 46px;
    background-image: linear-gradient(to bottom, #3a405c, #365354);
    box-shadow: 5px 8px 10px #272c3e, 0 0 10px #272c3e;
  }

  .input {
    width: 100%;
    border: none;
    outline: none;
    font-size: 16px;
    background: transparent;
    color: #fff;
    padding: 0;
    z-index: 2;
  }

  label {
    position: absolute;
    left: 10px;
    top: -13px;
    color: #aaa;
    font-size: 14px;
    pointer-events: none;
    transition: 0.2s ease all;
    background-color: #011727; /* 默认透明 */
    padding: 0 4px;
  }

  .input:focus + label,
  .floated {
    top: -8px; /* 上移多一点 */
    font-size: 12px;
    color: #42b983;
    background-color: white; /* 💡 或改成页面背景色 */
  }
}
</style>
