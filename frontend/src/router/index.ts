import {createRouter, createWebHistory} from 'vue-router'

const ProductList = () => import('@/pages/ProductListView.vue')

const routes = [
    {
        path: '/',
        component: () => import('@/layout/BaseLayout.vue'),
        children: [
            {path: '', name: 'ProductList', component: ProductList},
            {path: 'like-list', name: 'LikeListView', component: ProductList},
        ]
    },
    {path: '/:pathMatch(.*)*', redirect: '/'}
]

export const router = createRouter({
    history: createWebHistory(),
    routes
})
