<template>
  <div class="company-intro">
    <HeaderBar :title="t('地址')" />
    <div class="form">
      <div class="form-group">
        <label :class="{ floated: inputValue }">{{ t("地址") }}</label>
        <input
          v-model="withdrawalAddress"
          type="text"
          placeholder=" "
          class="input"
          :disabled="hasData"
        />
      </div>
      <button v-if="!hasData" class="submit-btn" @click="submit">
        {{ t("确认") }}
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import HeaderBar from "../../components/HeaderBar.vue";
import { getUserInfo, updateUserSimpleFront } from "../../api/index";
import { notify } from "../../utils/notify.js";
const inputValue = ref("");
import { useI18n } from "vue-i18n";
const { t } = useI18n();
const withdrawalAddress = ref("");
const hasData = ref(true);

getUserInfo().then((res) => {
  //console.log(res.data.withdrawalAddress);
  let data = res.data.withdrawalAddress;
  withdrawalAddress.value = data;
  hasData.value = data != null && data != "";
});

function submit() {
  updateUserSimpleFront({ withdrawalAddress: withdrawalAddress.value }).then(
    (res) => {
      if (res.code === 200) {
        globalThis.$notify({
          message: t("操作成功"),
          type: "success",
          duration: 2000,
        });
        hasData.value = true;
      } else {
        globalThis.$notify({
          message: t(res.msg),
          type: "error",
          duration: 2000,
        });
      }
    }
  );
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
.form {
  text-align: center;
  border-radius: 8px;
}
.form-group {
  position: relative;
  margin: 20px;
  border: 1px solid #ccc;
  border-radius: 6px;
  padding: 16px 10px 6px;
  background-color: transparent;
  font-family: sans-serif;
  overflow: visible; /* 避免 label 超出边框被裁剪 */
}

.submit-btn {
  width: 80%;
  color: #fff;
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
  font-size: 16px;
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
    margin: 20px;
    border: 1px solid #ccc;
    border-radius: 6px;
    padding: 16px 10px 6px;
    background-color: transparent;
    font-family: sans-serif;
    overflow: visible; /* 避免 label 超出边框被裁剪 */
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
    font-size: 16px;
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
  .submit-btn {
    width: 80%;
    color: #fff;
    border: 1px solid #6b6f6f;
    border-radius: 4px;
    text-align: center;
    min-height: 46px;
    background-image: linear-gradient(to bottom, #3a405c, #365354);
    box-shadow: 5px 8px 10px #272c3e, 0 0 10px #272c3e;
  }
}
</style>
