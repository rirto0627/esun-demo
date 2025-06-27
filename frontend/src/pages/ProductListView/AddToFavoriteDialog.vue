<template>
  <div v-if="show" class="fixed inset-0 z-50 flex items-center justify-center">
    <div
        class="absolute inset-0 bg-black/50 backdrop-blur-sm"
        @click="handleClose"
    ></div>

    <div class="relative bg-white rounded-lg shadow-xl w-full max-w-md mx-4 max-h-[90vh] overflow-y-auto">
      <div class="flex items-center justify-between p-6 border-b border-gray-200">
        <h2 class="text-xl font-bold mb-4">
          {{ isUpdateMode ? '🔁 更新喜好商品' : '➕ 加入喜好清單' }}
        </h2>
        <button
            @click="handleClose"
            class="p-1 rounded-md hover:bg-gray-100 transition-colors"
        >
          <X class="w-5 h-5 text-gray-500" />
        </button>
      </div>

      <div class="p-6 space-y-4">
        <ProductInfo v-if="product" :product="product" />

        <form @submit.prevent="handleSubmit" class="space-y-4">
          <AccountSelector
              v-model="formData.accountId"
              :accounts="accounts"
              :is-like-list="isLikeList"
              :product="product"
          />


          <QuantitySelector
              v-model="formData.quantity"
              :is-like-list="isLikeList"
              :product="product"
          />


          <PriceCalculator
              :product="product"
              :quantity="formData.quantity"
          />

          <div class="flex flex-col space-y-2 pt-4">
            <div class="flex space-x-3">
              <button
                  type="button"
                  @click="handleClose"
                  class="flex-1 px-4 py-2 border border-gray-300 rounded-md text-gray-700 hover:bg-gray-50 transition-colors"
              >
                取消
              </button>
              <button
                  type="submit"
                  class="flex-1 px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 transition-colors flex items-center justify-center"
                  :disabled="isSubmitting || !formData.accountId"
              >
                {{ isUpdateMode ? '更新' : '加入喜好清單' }}
              </button>
            </div>

            <button
                v-if="isUpdateMode"
                type="button"
                @click="handleDelete"
                class="w-full px-4 py-2 text-red-600 border border-red-300 rounded-md hover:bg-red-50 transition-colors"
                :disabled="isSubmitting"
            >
              🗑 刪除喜好商品
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import {ref, watch, watchEffect} from 'vue'
import { X } from 'lucide-vue-next'

import axios from 'axios'
import ProductInfo from './AddToFavoriteDialog/ProductInfo.vue'
import AccountSelector from './AddToFavoriteDialog/AccountSelector.vue'
import QuantitySelector from './AddToFavoriteDialog/QuantitySelector.vue'
import PriceCalculator from './AddToFavoriteDialog/PriceCalculator.vue'

const props = defineProps<{
  show: boolean
  product: {
    sn: number;
    id: number
    name: string
    price: number
    feeRate: number
    orderNum: number
    account: string
    totalAmount: number
    totalFee: number
  } | null
  accounts: {
    id: number
    name: string
    number: string
  }[],
  isLikeList: boolean
  isUpdateMode:boolean
}>()

const emit = defineEmits<{
  close: []
  success: [favoriteItem: any]
  refresh: []
}>()


const isSubmitting = ref(false)

const formData = ref({
  accountId: '',
  quantity: 1
})

watch(() => props.show, (newValue) => {
  if (newValue) {
    formData.value = {
      accountId: '',
      quantity: 1
    }
  }
})

const handleClose = () => {
  emit('close')
}

const calculateFee = () => {
  if (!props.product) return 0
  const productAmount = props.product.price * formData.value.quantity
  return productAmount * (props.product.feeRate / 100)
}

const calculateTotal = () => {
  if (!props.product) return 0
  const productAmount = props.product.price * formData.value.quantity
  const fee = calculateFee()
  return productAmount + fee
}

watchEffect(() => {
  if (props.isUpdateMode && props.product) {
    formData.value.accountId = props.product.account
    formData.value.quantity = props.product.orderNum
  }
})

const handleDelete = async () => {
  if (!props.product?.sn) return alert('缺少商品序號，無法刪除')

  const confirmDelete = confirm('確定要刪除這筆喜好商品嗎？')
  if (!confirmDelete) return

  isSubmitting.value = true

  try {
    const res = await axios.delete(`/api/like-list/${props.product.sn}`)

    if (res.data?.status === 'success') {
      alert('刪除成功')
      emit('refresh')
      handleClose()
    } else {
      alert('刪除失敗，請稍後再試')
    }
  } catch (err: any) {
    alert('伺服器錯誤：' + (err.response?.data?.message || err.message))
  } finally {
    isSubmitting.value = false
  }
}


const handleSubmit = async () => {
  if (!props.product) return

  isSubmitting.value = true

  try {
    const payload = {
      userId: 'A1236456789',
      productNo: props.product.id,
      account: formData.value.accountId,
      orderNum: formData.value.quantity
    }

    const method = props.isUpdateMode ? 'put' : 'post'
    const url = props.isUpdateMode
        ? `/api/like-list/${props.product.sn ?? ''}`
        : '/api/like-list'

    const res = await axios({
      method,
      url,
      data: payload,
      headers: { 'Content-Type': 'application/json' }
    })

    if (res.data?.status === 'success') {
      const favoriteItem = {
        id: Date.now(),
        ...payload,
        totalAmount: calculateTotal(),
        createdAt: new Date().toISOString()
      }
      emit('success', favoriteItem)
      emit('refresh')
      handleClose()
    } else {
      alert((props.isUpdateMode ? '更新' : '新增') + '失敗，請稍後再試')
    }
  } catch (err: any) {
    alert('伺服器錯誤：' + (err.response?.data?.message || err.message))
  } finally {
    isSubmitting.value = false
  }
}




</script>

<style scoped>
.fixed.inset-0.z-50 {
  z-index: 9999;
}

.overflow-y-auto::-webkit-scrollbar {
  width: 6px;
}

.overflow-y-auto::-webkit-scrollbar-track {
  background: #f1f1f1;
  border-radius: 3px;
}

.overflow-y-auto::-webkit-scrollbar-thumb {
  background: #c1c1c1;
  border-radius: 3px;
}

.overflow-y-auto::-webkit-scrollbar-thumb:hover {
  background: #a8a8a8;
}

button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}
</style>