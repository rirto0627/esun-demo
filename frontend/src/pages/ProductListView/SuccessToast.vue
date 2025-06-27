<template>
  <Transition
      enter-active-class="transition duration-300 ease-out"
      enter-from-class="transform translate-x-full opacity-0"
      enter-to-class="transform translate-x-0 opacity-100"
      leave-active-class="transition duration-200 ease-in"
      leave-from-class="transform translate-x-0 opacity-100"
      leave-to-class="transform translate-x-full opacity-0"
  >
    <div
        v-if="show"
        class="fixed top-4 right-4 bg-green-500 text-white px-6 py-3 rounded-lg shadow-lg flex items-center space-x-2 z-50"
    >
      <Check class="w-5 h-5" />
      <span>{{ message }}</span>
      <button
          @click="handleHide"
          class="ml-2 hover:bg-green-600 rounded p-1 transition-colors"
      >
        <X class="w-4 h-4" />
      </button>
    </div>
  </Transition>
</template>

<script setup lang="ts">
import { Check, X } from 'lucide-vue-next'


defineProps<{
  show: boolean
  message: string
}>()


const emit = defineEmits<{
  hide: []
}>()

const handleHide = () => {
  emit('hide')
}
</script>

<style scoped>
.fixed {
  z-index: 9999;
}
</style>