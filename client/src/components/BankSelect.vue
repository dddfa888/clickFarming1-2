<template>
  <div class="bank-select">
    <input
      type="text"
      class="input"
      v-model="inputValue"
      @focus="onFocus"
      @input="filterList"
      @blur="onBlur"
      :placeholder="placeholder"
      :disabled="props.show"
    />
    <ul v-if="showDropdown" class="dropdown">
      <li
        v-for="(item, index) in filteredOptions"
        :key="index"
        @mousedown.prevent="selectItem(item)"
      >
        {{ item }}
      </li>
    </ul>
  </div>
</template>

<script setup>
import { ref, watch, onMounted } from "vue";

const props = defineProps({
  modelValue: String,
  options: {
    type: Array,
    required: true,
  },
  show: Boolean,
  placeholder: {
    type: String,
    default: "请选择银行",
  },
});

const emit = defineEmits(["update:modelValue"]);

const inputValue = ref(props.modelValue || "");
const showDropdown = ref(false);
const filteredOptions = ref([...props.options]);

onMounted(() => {
  // 只在首次初始化同步父组件（避免重复触发）
  if (inputValue.value && inputValue.value !== props.modelValue) {
    emit("update:modelValue", inputValue.value);
  }
});

watch(
  () => props.modelValue,
  (val) => {
    if (val !== inputValue.value) {
      inputValue.value = val || "";

      if (
        val &&
        !props.options.includes(val) &&
        !filteredOptions.value.includes(val)
      ) {
        filteredOptions.value = [val, ...props.options];
      }
    }
  }
);

function filterList() {
  // 这里调用 emit 同步父组件 inputValue
  emit("update:modelValue", inputValue.value);

  const keyword = inputValue.value?.toLowerCase() || "";
  filteredOptions.value = props.options.filter((item) =>
    item.toLowerCase().includes(keyword)
  );
}

function onFocus() {
  showDropdown.value = true;
  filterList();
}

function selectItem(item) {
  inputValue.value = item;
  emit("update:modelValue", item);
  showDropdown.value = false;
}

function onBlur() {
  // 延迟关闭以允许点击 li 元素
  setTimeout(() => {
    showDropdown.value = false;
  }, 100);
}
</script>

<style scoped>
.bank-select {
  position: relative;
  width: 100%;
}

.input {
  width: 100%;
  border: 1px solid transparent;
  border-radius: 6px;
  color: #fff;
  box-sizing: border-box;
  background: transparent;
}

.dropdown {
  position: absolute;
  top: 100%;
  left: 0;
  right: 0;
  max-height: 200px;
  overflow-y: auto;
  border: 1px solid #ccc;
  background: white;
  z-index: 10;
  border-radius: 6px;
  margin-top: 2px;
  padding: 0;
  list-style: none;
}

.dropdown li {
  padding: 10px;
  cursor: pointer;
}

.dropdown li:hover {
  background-color: #f5f5f5;
}

@media screen and (min-width: 768px) {
  .bank-select {
    position: relative;
    width: 100%;
  }

  .input {
    width: 100%;
    border: 1px solid transparent;
    border-radius: 6px;
    color: #fff;
    box-sizing: border-box;
    background: transparent;
  }

  .dropdown {
    position: absolute;
    top: 100%;
    left: 0;
    right: 0;
    max-height: 200px;
    overflow-y: auto;
    border: 1px solid #ccc;
    background: white;
    z-index: 10;
    border-radius: 6px;
    margin-top: 2px;
    padding: 0;
    list-style: none;
  }

  .dropdown li {
    padding: 10px;
    cursor: pointer;
  }

  .dropdown li:hover {
    background-color: #f5f5f5;
  }
}
</style>
