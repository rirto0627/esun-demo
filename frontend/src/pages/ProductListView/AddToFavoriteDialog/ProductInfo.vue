<template>
  <div class="bg-gray-50 rounded-lg p-4 space-y-2">
    <h3 class="font-medium text-gray-900">{{ product?.name }}</h3>
    <div class="text-sm text-gray-600 space-y-1">
      <p>價格：${{ Number(product?.price).toFixed(2) }}</p>
      <p>手續費率：{{ (Number(product?.feeRate)*100).toFixed(2) }}%</p>
    </div>
  </div>
</template>

<script setup lang="ts">

const props = defineProps<{
  product: {
    id: number
    name: string
    price: number
    feeRate: number
  } | null
}>()

// console.log(props)
</script>