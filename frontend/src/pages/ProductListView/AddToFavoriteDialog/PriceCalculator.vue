<template>
  <div class="bg-blue-50 rounded-lg p-4 space-y-2">
    <div class="flex justify-between text-sm">
      <span class="text-gray-600">商品金額：</span>
      <span>${{ Number(productAmount).toFixed(2) }}</span>
    </div>
    <div class="flex justify-between text-sm">
      <span class="text-gray-600">手續費：</span>
      <span>${{ (Number(fee)*100).toFixed(2) }}</span>
    </div>
    <div class="border-t border-blue-200 pt-2">
      <div class="flex justify-between font-medium">
        <span>總計：</span>
        <span class="text-blue-600">${{ Number(total).toFixed(2) }}</span>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'

const props = defineProps<{
  product: {
    id: number
    name: string
    price: number
    feeRate: number
  } | null
  quantity: number
}>()

const productAmount = computed(() => {
  if (!props.product) return 0
  return props.product.price * props.quantity
})

const fee = computed(() => {
  if (!props.product) return 0
  return productAmount.value * (props.product.feeRate)
})

const total = computed(() => {
  return productAmount.value + fee.value
})
</script>