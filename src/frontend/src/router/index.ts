import Vue from 'vue'
import VueRouter, { RouteConfig } from 'vue-router'
import store from '@/store/auth'
import { onAuthStateInit } from '@/firebase'
import Home from '../views/Home.vue'

Vue.use(VueRouter)

const routes: Array<RouteConfig> = [
  {
    path: '/',
    name: 'Home',
    component: Home,
    meta: { 
        title: 'Sparking',
    }
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue'),
    meta: { 
        title: 'Login - Sparking',
        requiresAuth: false 
    }
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: () => import('../views/Dashboard.vue'),
    meta: { 
        title: 'Dashboard - Sparking',
        requiresAuth: true 
    }
  },

  // otherwise redirect to home
  { 
    path: '*', 
    beforeEnter: (to, from, next) => {
      next('/')
    }
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach(async (to, from, next) => {
  if (to.meta.requiresAuth !== undefined) await onAuthStateInit()
  
  if (to.meta.requiresAuth == true && !store.getters.isLogged) next({ path: '/login', query: { redirect: to.fullPath } })
  else if (to.meta.requiresAuth == false && store.getters.isLogged) next('/dashboard')
  else next(); // make sure to always call next()!

  if (to.meta.title !== undefined) document.title = to.meta.title
  else document.title = 'Sparking'
});

export default router