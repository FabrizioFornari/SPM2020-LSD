import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store/auth'
import { onAuthStateInit } from '@/firebase'

Vue.use(VueRouter)

const router: VueRouter = new VueRouter({
  mode: 'history', 
  routes: [
    {
      path: '/',
      name: 'Home',
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
      path: '/register',
      name: 'Register',
      component: () => import('../views/Register.vue'),
      meta: { 
          title: 'Register - Sparking',
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
      },
      children: [
        {
          path: 'garage',
          name: 'Garage',
          component: () => import('../views/driver/Garage.vue'),
          meta: { 
              title: 'My Garage - Sparking'
          }
        }
      ]
    },
    { 
      path: '/admin/login',
      name: 'AdminLogin',
      component: () => import('../views/admin/Login.vue'),
      meta: { 
          title: 'Admin Login',
          requiresAdmin: false 
      }
    },
    { 
      path: '/admin/dashboard',
      name: 'AdminDash',
      component: () => import('../views/admin/Dashboard.vue'),
      meta: { 
          title: 'Admin Dashboard',
          requiresAdmin: true 
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
})

router.beforeEach(async (to, from, next) => {
  if (to.meta.requiresAuth !== undefined) await onAuthStateInit()
  
  if (to.meta.requiresAuth == true && !store.getters.isLogged) next({ path: '/login', query: { redirect: to.fullPath } })
  else if (to.meta.requiresAuth == false && store.getters.isLogged) next('/dashboard')
  else if (to.meta.requiresAdmin == true && !store.getters.isLoggedAdmin) next('/')
  else if (to.meta.requiresAdmin == false && store.getters.isLoggedAdmin) next('/admin/dashboard')
  else next(); // make sure to always call next()!

  if (to.meta.title !== undefined) document.title = to.meta.title
  else document.title = 'Sparking'
});

export default router