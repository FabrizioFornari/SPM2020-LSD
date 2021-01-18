import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store'
import { onAuthStateInit } from '@/firebase'

Vue.use(VueRouter)

const router: VueRouter = new VueRouter({
  mode: 'history', 
  routes: [
    {
      path: '/map',
      name: 'Home',
      meta: { 
          title: 'Sparking',
      },
      beforeEnter: (to, from, next) => {
        store.commit("setActive", null)
        store.commit("setWaypoints", null)
        next()
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
      path: '/map/parking',
      name: 'NewParking',
      component: () => import('../views/Parking.vue'),
      props: route => ( route.query ),
      meta: { 
        float: true,
        requiresRole: ['municipality'] 
      },
      beforeEnter: (to, from, next) => {
        if (!to.query.c) next('/map')
        else next()
      }
    },
    {
      path: '/map/parking/:id',
      name: 'Parking',
      component: () => import('../views/Parking.vue'),
      props: route => ({ id: route.params.id, edit: (route.query.edit === 'true') }),
      meta: { 
        float: true 
      }
    },
    {
      path: '/buy/ticket/:id',
      name: 'NewTicket',
      component: () => import('../views/driver/Ticket.vue'),
      props: true,
      meta: { 
        float: true,
        requiresRole: ['driver', 'policeman'] 
      }
    },
    {
      path: '/dashboard',
      name: 'Dashboard',
      component: () => import('../views/Dashboard.vue'),
      children: [
        {
          path: '',
          name: 'Menu',
          component: () => import('../views/Menu.vue'),
          meta: { 
            title: 'Dashboard - Sparking',
            requiresAuth: true 
          }
        },
        {
          path: 'profile',
          name: 'Profile',
          component: () => {
            switch (store.getters.userRole) {
              case 'driver': return import('../views/driver/Profile.vue')
              case 'policeman': return import('../views/driver/Profile.vue')
              case 'municipality': return import('../views/driver/Profile.vue')
            }
          },
          meta: { 
            title: 'My Profile - Sparking',
            requiresAuth: true 
          }
        },
        {
          path: 'garage',
          name: 'Garage',
          component: () => import('../views/driver/Garage.vue'),
          meta: { 
              title: 'My Garage - Sparking',
              requiresRole: ['driver', 'policeman'] 
          }
        },
        {
          path: 'pocket',
          name: 'Pocket',
          component: () => import('../views/driver/Garage.vue'),
          meta: { 
              title: 'My Pocket - Sparking',
              requiresRole: ['driver', 'policeman'] 
          }
        },
        {
          path: 'vehicle/:id',
          name: 'Vehicle',
          component: () => import('../views/driver/Vehicle.vue'),
          props: true,
          meta: {
              title: 'My Vehicle - Sparking',
              requiresRole: ['driver', 'policeman'] 
          }
        },
        {
          path: 'garage/new',
          name: 'NewVehicle',
          component: () => import('../views/driver/Vehicle.vue'),
          meta: {
              title: 'New Vehicle - Sparking',
              requiresRole: ['driver', 'policeman'] 
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
        next('/map')
      }
    }
  ]
})

router.beforeEach(async (to, from, next) => {
  if (to.meta.requiresAuth !== undefined) {
    await onAuthStateInit()
    if (to.meta.requiresAuth == true && !store.getters.isLogged) next({ path: '/login', query: { redirect: to.fullPath } })
    else if (to.meta.requiresAuth == false && store.getters.isLogged) next('/dashboard')
    else if (to.meta.requiresAdmin == true && !store.getters.isLoggedAdmin) next('/map')
    else if (to.meta.requiresAdmin == false && store.getters.isLoggedAdmin) next('/admin/dashboard')
    else next(); // make sure to always call next()!
  } else if (to.meta.requiresRole !== undefined) {
    await onAuthStateInit()
    if (!store.getters.isLogged) next({ path: '/login', query: { redirect: to.fullPath } })
    else if (!to.meta.requiresRole.includes(store.getters.userRole)) next('/map')
    else next()
  } else next()

  if (to.meta.title !== undefined) document.title = to.meta.title
  else document.title = 'Sparking'
})

export default router