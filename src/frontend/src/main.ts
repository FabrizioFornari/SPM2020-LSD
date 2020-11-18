import Vue from 'vue'
import App from './App.vue'
import './registerServiceWorker'
import router from './router'
import store from './store/auth'
import VueCookies from 'vue-cookies'
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.config.productionTip = false
Vue.use(VueCookies)
Vue.use(BootstrapVue)

store.commit("SET_ADMIN_STATE", Vue.$cookies.isKey('admin'))

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')