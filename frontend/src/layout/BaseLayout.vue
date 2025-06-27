<template>
  <div class="min-h-screen flex flex-col bg-background">
    <header class="bg-white border-b border-gray-200 shadow-sm px-6 py-4 sticky top-0 z-40">
      <div class="container mx-auto flex items-center justify-between">
        <div class="flex items-center">
          <h1 class="text-xl font-bold text-gray-900">🌐 金融商品系統</h1>
        </div>

        <nav class="hidden md:flex ml-8 space-x-1">
          <RouterLink
              v-for="route in routes"
              :key="route.path"
              :to="route.path"
              class="nav-link"
              :class="{ 'nav-link-active': $route.path === route.path }"
          >
            <component :is="route.icon" class="w-4 h-4 mr-2" />
            {{ route.name }}
          </RouterLink>
        </nav>

        <button
            class="md:hidden p-2 rounded-lg hover:bg-gray-100 transition-colors"
            @click="toggleMenu"
        >
          <XIcon v-if="menuOpen" class="w-6 h-6 text-gray-600" />
          <MenuIcon v-else class="w-6 h-6 text-gray-600" />
        </button>
      </div>
    </header>

    <Teleport to="body">
      <Transition name="slide-down">
        <div
            v-if="menuOpen"
            class="fixed inset-0 z-50 md:hidden"
        >
          <div
              class="absolute inset-0 backdrop-blur-sm bg-black/20 transition-opacity duration-300"
              @click="closeMenu"
          />

          <div
              class="absolute top-0 left-0 w-full bg-white/95 backdrop-blur-md border-b border-gray-200 shadow-2xl transform"
          >
            <div class="p-6 border-b border-gray-100">
              <div class="flex items-center justify-between">
                <h2 class="text-lg font-semibold text-gray-900">選單</h2>
                <button
                    @click="closeMenu"
                    class="p-1 rounded-md hover:bg-gray-100 transition-colors"
                >
                  <XIcon class="w-5 h-5 text-gray-500" />
                </button>
              </div>
            </div>

            <nav class="p-4 space-y-2">
              <RouterLink
                  v-for="route in routes"
                  :key="route.path"
                  :to="route.path"
                  class="mobile-nav-link"
                  :class="{ 'mobile-nav-link-active': $route.path === route.path }"
                  @click="closeMenu"
              >
                <component :is="route.icon" class="w-5 h-5 mr-3" />
                {{ route.name }}
              </RouterLink>
            </nav>
          </div>
        </div>
      </Transition>
    </Teleport>

    <main class="flex-1 container mx-auto px-4 py-6">
      <RouterView />
    </main>

    <footer class="bg-muted text-muted-foreground text-center text-sm py-4 border-t">
      <p>&copy; 2025 E.SUN DEMO — By 徐茂霖</p>
    </footer>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { Heart, Package, Menu as MenuIcon, X as XIcon } from 'lucide-vue-next'

const menuOpen = ref(false)

const routes = [
  { path: '/like-list', name: '喜好清單', icon: Heart },
  { path: '/product-list', name: '所有商品', icon: Package }
]

const toggleMenu = () => {
  menuOpen.value = !menuOpen.value
}

const closeMenu = () => {
  menuOpen.value = false
}

const handleKeydown = (event: KeyboardEvent) => {
  if (event.key === 'Escape') {
    closeMenu()
  }
}

onMounted(() => {
  document.addEventListener('keydown', handleKeydown)
})

onUnmounted(() => {
  document.removeEventListener('keydown', handleKeydown)
})
</script>

<style scoped>
.nav-link {
  display: flex;
  align-items: center;
  padding: 0.5rem 1rem;
  border-radius: 0.5rem;
  font-size: 0.875rem;
  font-weight: 500;
  transition: all 0.2s ease-in-out;
  color: rgb(75 85 99);
}

.nav-link:hover {
  color: rgb(17 24 39);
  background-color: rgb(249 250 251);
  box-shadow: 0 1px 2px 0 rgb(0 0 0 / 0.05);
}

.nav-link-active {
  background-color: rgb(239 246 255);
  color: rgb(29 78 216);
  border: 1px solid rgb(191 219 254);
  box-shadow: 0 1px 2px 0 rgb(0 0 0 / 0.05);
}

.mobile-nav-link {
  display: flex;
  align-items: center;
  width: 100%;
  padding: 0.75rem 1rem;
  border-radius: 0.5rem;
  font-size: 1rem;
  font-weight: 500;
  transition: all 0.2s;
  color: rgb(55 65 81);
}

.mobile-nav-link:hover {
  color: rgb(17 24 39);
  background-color: rgb(249 250 251);
}

.mobile-nav-link-active {
  background-color: rgb(239 246 255);
  color: rgb(29 78 216);
  border: 1px solid rgb(191 219 254);
}

@media (min-width: 768px) {
  .fixed.inset-0.z-50 {
    display: none !important;
  }
}
</style>