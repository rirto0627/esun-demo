<template>
  <div>
    <label class="text-sm font-medium text-gray-700">選擇帳號</label>
    <select
        v-model="selectedAccountInternal"
        class="w-full mt-1 px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
    >
      <option v-if="!isLikeList" disabled value="">請選擇帳號</option>

      <option
          v-for="account in accounts"
          :key="account.id"
          :value="account.number"
      >
        {{ account.name }}（{{ account.number }}）
      </option>
    </select>
  </div>
</template>
<script setup lang="ts">
import { ref, watch, onMounted } from 'vue'

const props = defineProps<{
  accounts: { id: number, name: string, number: string }[]
  isLikeList: boolean
  product: any
  modelValue: string
}>()

const emit = defineEmits<{
  (e: 'update:modelValue', value: string): void
}>()

const selectedAccountInternal = ref(props.modelValue)

watch(selectedAccountInternal, val => {
  emit('update:modelValue', val)
})

watch(() => props.modelValue, (val) => {
  selectedAccountInternal.value = val
})

onMounted(() => {
  if (props.isLikeList && props.product?.account) {
    selectedAccountInternal.value = props.product.account
    emit('update:modelValue', props.product.account)
  }
})
</script>
