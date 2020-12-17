import Vue from 'vue'
import Vuex from 'vuex'
import auth from './auth'
import driver from './driver'

Vue.use(Vuex)

export default new Vuex.Store({
    modules: {
        auth,
        driver
    }
})