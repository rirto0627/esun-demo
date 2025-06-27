<template>
  <div class="space-y-2">
    <label class="text-sm font-medium text-gray-700">購買數量</label>
    <div class="flex items-center space-x-3">
      <button
          type="button"
          @click="decrease"
          class="w-8 h-8 rounded-md border border-gray-300 flex items-center justify-center hover:bg-gray-50 transition-colors"
          :disabled="count <= 1"
      >
        <Minus class="w-4 h-4" />
      </button>
      <input
          v-model.number="count"
          type="number"
          min="1"
          class="flex-1 px-3 py-2 border border-gray-300 rounded-md text-center focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
          required
      />
      <button
          type="button"
          @click="increase"
          class="w-8 h-8 rounded-md border border-gray-300 flex items-center justify-center hover:bg-gray-50 transition-colors"
      >
        <Plus class="w-4 h-4" />
      </button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, watch, onMounted } from 'vue'
import { Minus, Plus } from 'lucide-vue-next'

const props = defineProps<{
  modelValue: number
  isLikeList: boolean
  product: any
}>()

const emit = defineEmits<{
  (e: 'update:modelValue', value: number): void
}>()

const count = ref(props.modelValue)

watch(count, val => emit('update:modelValue', val))

onMounted(() => {
  if (props.isLikeList && props.product?.orderNum) {
    count.value = props.product.orderNum
    emit('update:modelValue', props.product.orderNum)
  }
})

const increase = () => {
  count.value += 1
}

const decrease = () => {
  if (count.value > 1) count.value -= 1
}
</script>

<style scoped>
button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}
</style>
