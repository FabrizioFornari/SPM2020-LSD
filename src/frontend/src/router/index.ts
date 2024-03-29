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
        requiresRole: ['MUNICIPALITY'] 
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
        float: true,
        requiresRole: route => route.query.edit == 'true' ? ['MUNICIPALITY'] : undefined
      }
    },
    {
      path: '/map/slot/:id',
      name: 'Slot',
      component: () => import('../views/Slot.vue'),
      props: route => ({ id: route.params.id }),
      meta: { 
        float: true
      }
    },
    {
      path: '/map/slots',
      name: 'AddSlots',
      component: () => import('../views/SlotList.vue'),
      props: route => ( route.query ),
      meta: { 
        float: true,
        requiresRole: ['MUNICIPALITY'] 
      },
      beforeEnter: (to, from, next) => {
        if (!to.query.p) next('/map')
        else next()
      }
    },
    {
      path: '/buy/ticket/:parkingid/:slotid?',
      name: 'NewTicket',
      component: () => import('../views/driver/Ticket.vue'),
      props: true,
      meta: { 
        float: true,
        requiresRole: ['DRIVER', 'POLICEMAN'] 
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
          component: () => import('../views/Profile.vue'),
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
              requiresRole: ['DRIVER', 'POLICEMAN'] 
          }
        },
        {
          path: 'garage/new',
          name: 'NewVehicle',
          component: () => import('../views/driver/Vehicle.vue'),
          meta: {
              title: 'New Vehicle - Sparking',
              requiresRole: ['DRIVER', 'POLICEMAN'] 
          }
        },
        {
          path: 'pocket',
          name: 'Pocket',
          component: () => import('../views/driver/Pocket.vue'),
          meta: { 
              title: 'My Pocket - Sparking',
              requiresRole: ['DRIVER', 'POLICEMAN'] 
          }
        },
        {
          path: 'vehicle/:id',
          name: 'Vehicle',
          component: () => import('../views/driver/Vehicle.vue'),
          props: true,
          meta: {
              title: 'My Vehicle - Sparking',
              requiresRole: ['DRIVER', 'POLICEMAN'] 
          }
        },
        {
          path: 'parkings',
          name: 'Parkings',
          component: () => import('../views/municipality/Parkings.vue'),
          meta: {
              title: 'My Parkings - Sparking',
              requiresRole: ['MUNICIPALITY'] 
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
  const role = typeof to.meta.requiresRole === 'function' ? to.meta.requiresRole(to) : to.meta.requiresRole

  if (to.meta.requiresAuth !== undefined) {
    await onAuthStateInit()
    if (to.meta.requiresAuth == true && !store.getters.isLogged) next({ path: '/login', query: { redirect: to.fullPath } })
    else if (to.meta.requiresAuth == false && store.getters.isLogged) next('/dashboard')
    else if (to.meta.requiresAdmin == true && !store.getters.isLoggedAdmin) next('/map')
    else if (to.meta.requiresAdmin == false && store.getters.isLoggedAdmin) next('/admin/dashboard')
    else next(); // make sure to always call next()!
  } else if (role !== undefined) {
    await onAuthStateInit()
    if (!store.getters.isLogged) next({ path: '/login', query: { redirect: to.fullPath } })
    else if (!role.includes(store.getters.userRole)) next('/map')
    else next()
  } else next()

  document.title = to.meta.title || 'Sparking'
})

export default router