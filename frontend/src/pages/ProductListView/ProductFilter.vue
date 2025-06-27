<template>
  <div class="mb-4">
    <input
        v-model="keyword"
        @input="$emit('filter-changed', keyword)"
        type="text"
        placeholder="🔍 搜尋商品名稱"
        class="border rounded px-3 py-2 w-full"
    />
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const keyword = ref('')
</script>