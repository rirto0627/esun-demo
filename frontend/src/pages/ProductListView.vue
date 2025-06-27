<template>
  <section class="p-4">
    <h2 class="text-xl font-bold mb-4">{{ title }}</h2>

    <ProductFilter @filter-changed="handleFilter"/>

    <div v-if="loading" class="text-gray-500">載入中...</div>
    <div v-else-if="error" class="text-red-500">錯誤：{{ error }}</div>

    <ProductGrid
        v-if="!loading && !error && filteredProducts.length > 0"
        :products="filteredProducts"
        :is-like-list="isLikeList"
        :open-add-dialog="openAddDialog"
    />

    <div v-else-if="!loading && !error" class="text-sm text-gray-500">目前沒有喜歡的商品</div>

    <AddToFavoriteDialog
        :show="showDialog"
        :product="selectedProduct"
        :accounts="accounts"
        :is-update-mode="isUpdateMode"
        :is-like-list="isLikeList"
        @close="closeDialog"
        @success="handleAddSuccess"
        @refresh="fetchProducts"
    />

    <SuccessToast
        :show="showSuccessToast"
        message="已成功加入喜好清單！"
        @hide="showSuccessToast = false"
    />
  </section>
</template>

<script setup lang="ts">
import {ref, computed, watch} from 'vue'
import {useRoute} from 'vue-router'
import {api} from '@/lib/api'
import ProductGrid from './ProductListView/ProductGrid.vue'
import ProductFilter from './ProductListView/ProductFilter.vue'
import AddToFavoriteDialog from './ProductListView/AddToFavoriteDialog.vue'
import SuccessToast from './ProductListView/SuccessToast.vue'

interface ProductBase {
  id: number
  name: string
}

interface ProductRegular extends ProductBase {
  price: number
  feeRate: number
}

interface ProductLike extends ProductBase {
  totalAmount: number
  totalFee: number
  orderNum: number
}

type Product = ProductRegular | ProductLike

const route = useRoute()
const isLikeList = computed(() => route.path === '/like-list')
const title = computed(() => isLikeList.value ? '💖 我的喜好清單' : '📦 所有商品清單')

const products = ref<Product[]>([])
const filteredProducts = ref<Product[]>([])
const loading = ref(true)
const error = ref('')
const filterKeyword = ref('')

const selectedProduct = ref<any>(null)
const showDialog = ref(false)
const showSuccessToast = ref(false)
const accounts = ref([{id: 1, name: '活期存款', number: '1111999666'}])
const isUpdateMode = ref(false)
const selectedAccount = ref('')
const quantity = ref(1)
const fetchProducts = async () => {
  loading.value = true
  error.value = ''
  products.value = []
  filteredProducts.value = []

  try {
    const url = isLikeList.value ? '/like-list' : '/product-list'
    const params = isLikeList.value ? {userId: 'A1236456789'} : {}
    const res = await api.get(url, {params})
    // console.log(res.data.data)
    if (res.data.status === 'success') {
      const mapped: Product[] = res.data.data.map((item: any) => ({
        id: item.productNo,
        name: item.productName,
        price: item.productPrice,
        feeRate: item.productFeeRate,
        ...(isLikeList.value
            ? {totalAmount: item.totalAmount, totalFee: item.totalFee, orderNum: item.orderNum, account: item.account,sn:item.sn}
            : {})
      }))
      products.value = mapped
      applyFilter()
    } else {
      error.value = res.data.message || '資料取得失敗'
    }
  } catch (err: any) {
    error.value = err.message || '無法取得商品資料'
  } finally {
    loading.value = false
  }
}

const applyFilter = () => {
  const keyword = filterKeyword.value.trim()
  filteredProducts.value = keyword
      ? products.value.filter(p => p.name.includes(keyword))
      : [...products.value]
}
const handleFilter = (keyword: string) => {
  filterKeyword.value = keyword
  applyFilter()
}

const openAddDialog = (product: any) => {
  selectedProduct.value = product

  const isLike = isLikeList.value
  isUpdateMode.value = isLike
  showDialog.value = true

  selectedAccount.value = isLike ? product.account ?? '' : ''

  quantity.value = isLike ? product.orderNum ?? 1 : 1
}


const closeDialog = () => {
  showDialog.value = false
  selectedProduct.value = null
}
const handleAddSuccess = (item: any) => {
  showSuccessToast.value = true
  setTimeout(() => (showSuccessToast.value = false), 3000)
}

watch(() => route.path, fetchProducts, {immediate: true})
</script>
