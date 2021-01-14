import Vue from 'vue'
import Vuex from 'vuex'
import map from './map'
import auth from './auth'
import driver from './driver'
import parking from './parking'

Vue.use(Vuex)

export default new Vuex.Store({
    modules: {
        map,
        auth,
        driver,
        parking
    }
})