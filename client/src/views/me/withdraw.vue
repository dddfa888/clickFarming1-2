<template>
  <div class="withdraw-page">
    <div class="withdraw-container">
      <div class="header">
        <div @click="toback">
          <van-icon name="arrow-left" />
          <span class="back">{{ t("取款") }}</span>
        </div>
        <span class="balance">
          <span>{{ balance }} $</span>
          <span>{{ t("剩余") }}($)</span>
        </span>
      </div>

      <div class="form" v-if="bankAccountNumber && bankName">
        <div class="form-group">
          <label>{{ t("银行名") }}</label>
          <input type="text" v-model="bankName" disabled />
        </div>

        <div class="form-group">
          <label>{{ t("账号") }}</label>
          <input type="text" v-model="bankAccountNumber" disabled />
        </div>

        <div class="form-group amount-group">
          <label>{{ t("金钱数额") }}</label>
          <input
            style="border: 1px solid #e5e7eb"
            type="text"
            v-model="amount"
            :placeholder="t('金钱数额')"
          />
          <button @click="fillAll">{{ t("全部") }}</button>
        </div>

        <div class="form-group password-group">
          <label>{{ t("提现密码") }}</label>
          <input
            style="border: 1px solid #e5e7eb"
            :type="showPassword ? 'text' : 'password'"
            v-model="password"
            :placeholder="t('提现密码')"
          />
          <van-icon
            :name="showPassword ? 'eye-o' : 'closed-eye'"
            class="eye-icon"
            @click="togglePassword"
          />
        </div>

        <!-- 按钮增加 :disabled 和 loading 状态显示 -->
        <button
          class="submit-btn"
          :disabled="loading"
          @click="submit"
        >{{ loading ? t("处理中...") : t("取款") }}</button>
      </div>
      <div v-else>
        {{ t("您尚未填写银行信息，请点击留言。") }}
        <span
          @click="
            () => {
              router.push('/bankInfo');
            }
          "
          style="color: #20a53a"
        >{{ t('这里') }}</span>
        {{ t("填写信息") }}
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { withdraw, getUserInfo } from "../../api/index.js";
import { useI18n } from "vue-i18n";
import { notify } from "../../utils/notify.js";

const { t } = useI18n();

const balance = ref("");
const bankAccountNumber = ref("");
const bankName = ref("");
const amount = ref("");
const password = ref("");
const showPassword = ref(false);
const loading = ref(false); // ✅ 新增 loading 状态
const router = useRouter();

function togglePassword() {
  showPassword.value = !showPassword.value;
}

function fillAll() {
  amount.value = balance.value;
}

function toback() {
  router.go(-1);
}

// 防抖函数
function debounce(fn, delay) {
  let timer = null;
  return function() {
    const context = this;
    const args = arguments;
    if (timer) clearTimeout(timer);
    timer = setTimeout(() => {
      fn.apply(context, args);
    }, delay);
  };
}
const submit = debounce(function() {
  if (loading.value) return; // ✅ 防止重复提交
  loading.value = true;

  withdraw({ amount: amount.value, fundPassword: password.value })
    .then(res => {
      if (res.code === 200) {
        globalThis.$notify({
          message: t("操作成功"),
          type: "success",
          duration: 2000
        });

        amount.value = "";
        password.value = "";

        setTimeout(() => {
          router.push("/withdrawHistory");
        }, 2000);

        getUserInfo().then(res => {
          balance.value = res.data.accountBalance;
        });
      } else {
        globalThis.$notify({
          message: t(res.msg),
          type: "error",
          duration: 2000
        });
        amount.value = "";
        password.value = "";
      }
    })
    .catch(err => {
      globalThis.$notify({
        message: t("网络错误，请稍后重试"),
        type: "error",
        duration: 2000
      });
      console.error("withdraw error:", err);
    })
    .finally(() => {
      loading.value = false;
    });
}, 2000);

getUserInfo().then(res => {
  bankAccountNumber.value = formatBankCard(res.data.bankAccountNumber);
  bankName.value = res.data.bankName;
  balance.value = res.data.accountBalance;
});

function formatBankCard(cardNo) {
  if (!cardNo) return "";
  const clean = cardNo.replace(/\s+/g, "");
  if (clean.length <= 6) return clean;
  const start = clean.slice(0, 4);
  const end = clean.slice(-4);
  return `${start} **** **** ${end}`;
}
</script>

<style scoped>
.withdraw-page {
  background: url("../../assets/img/background-D7o_xTde.png") no-repeat center
    center;
  height: 100vh;
  padding: 20px;
  color: white;
  font-family: Arial, sans-serif;
}

.withdraw-container {
  max-width: 400px;
  margin: auto;
  padding-top: 40px;
}

.header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 30px;
}

.form {
  padding: 20px;
  border-radius: 8px;
}

.form-group {
  margin-bottom: 15px;
  position: relative;
}

label {
  display: block;
  margin-bottom: 5px;
}

input {
  width: 100%;
  background-color: transparent !important;
  border: 1px solid #e5e7eb;
  padding: 10px;
  border: none;
  color: #fff;
  border-radius: 4px;
}

input[disabled] {
  background-color: none;
  border: 1px solid #e5e7eb;
  color: #fff;
}

.amount-group button,
.password-group button {
  position: absolute;
  right: 10px;
  top: 32px;
  background: none;
  border: none;
  color: #00f;
  cursor: pointer;
}

.submit-btn {
  width: 100%;
  padding: 12px;
  background-color: #3b4d63;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 16px;
  margin-top: 10px;
  cursor: pointer;
}

.password-group {
  position: relative;
}

.eye-icon {
  position: absolute;
  right: 10px;
  top: 34px;
  font-size: 20px;
  color: #fff;
  cursor: pointer;
}

.balance {
  display: flex;
  flex-direction: column;
  align-items: center;
}
@media screen and (min-width: 768px) {
  .withdraw-page {
    background: url("../../assets/img/background-D7o_xTde.png") no-repeat center
      center;
    height: 100vh;
    padding: 20px;
    width: 500px;
    margin: 0 auto;
    color: white;
    font-family: Arial, sans-serif;
  }

  .withdraw-container {
    max-width: 400px;
    margin: auto;
    padding-top: 40px;
  }

  .header {
    display: flex;
    justify-content: space-between;
    margin-bottom: 30px;
  }

  .form {
    padding: 20px;
    border-radius: 8px;
  }

  .form-group {
    margin-bottom: 15px;
    position: relative;
  }

  label {
    display: block;
    margin-bottom: 5px;
  }

  input {
    width: 100%;
    background-color: transparent !important;
    border: 1px solid #e5e7eb;
    padding: 10px;
    border: none;
    color: #fff;
    border-radius: 4px;
  }

  input[disabled] {
    background-color: none;
    border: 1px solid #e5e7eb;
    color: #fff;
  }

  .amount-group button,
  .password-group button {
    position: absolute;
    right: 10px;
    top: 32px;
    background: none;
    border: none;
    color: #00f;
    cursor: pointer;
  }

  .submit-btn {
    width: 100%;
    padding: 12px;
    background-color: #3b4d63;
    color: white;
    border: none;
    border-radius: 8px;
    font-size: 16px;
    margin-top: 10px;
    cursor: pointer;
  }

  .password-group {
    position: relative;
  }

  .eye-icon {
    position: absolute;
    right: 10px;
    top: 34px;
    font-size: 20px;
    color: #fff;
    cursor: pointer;
  }

  .balance {
    display: flex;
    flex-direction: column;
    align-items: center;
  }
}
</style>
