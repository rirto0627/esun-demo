<template>
  <div class="space-y-6">
    <div class="flex items-center justify-between">
      <h1 class="text-2xl font-bold text-gray-900">金融商品清單</h1>
      <p v-if="!isLikeList" class="text-sm text-gray-500">點擊商品卡片可加入喜好清單</p>
    </div>

    <div class="grid gap-4 sm:grid-cols-2 md:grid-cols-3">
      <Card
          v-for="product in products"
          :key="product.id"
          class="rounded-2xl shadow-sm hover:shadow-md transition border cursor-pointer group"
          @click="openAddDialog(product)"
      >
        <CardContent class="p-4 space-y-3">
          <div class="space-y-2">
            <h3 class="text-lg font-semibold group-hover:text-blue-600 transition-colors">
              {{ product.name }}
            </h3>
            <div class="space-y-1">
              <template v-if="isLikeList">
                <p class="text-sm text-muted-foreground">總金額：${{ Number(product.totalAmount).toFixed(2) }}</p>
                <p class="text-sm text-muted-foreground">手續費：${{ Number(product.totalFee).toFixed(2) }}</p>
                <p class="text-sm text-muted-foreground">數量：{{ product.orderNum }}</p>
              </template>
              <template v-else>
                <p class="text-sm text-muted-foreground">價格：${{ Number(product.price).toFixed(2) }}</p>
                <p class="text-sm text-muted-foreground">
                  手續費率：{{ (Number(product.feeRate) * 100).toFixed(2) }}%
                </p>
              </template>
            </div>
          </div>
          <template v-if="!isLikeList">

            <div class="flex items-center justify-between pt-2 border-t border-gray-100">
              <div class="flex items-center text-xs text-gray-500">
                <Heart class="w-3 h-3 mr-1"/>
                點擊加入喜好
              </div>
              <ChevronRight class="w-4 h-4 text-gray-400 group-hover:text-blue-500 transition-colors"/>
            </div>
          </template>
        </CardContent>
      </Card>
    </div>
  </div>
</template>

<script setup lang="ts">
import {defineProps} from 'vue'
import {Heart, ChevronRight} from 'lucide-vue-next'
import {Card, CardContent} from '@/components/ui/card'

defineProps<{
  products: any[]
  isLikeList: boolean
  openAddDialog: (product: any) => void
}>()
</script>
