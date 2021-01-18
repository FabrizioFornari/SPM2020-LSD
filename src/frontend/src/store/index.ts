import Vue from 'vue'
import Vuex from 'vuex'
import map from './map'
import auth from './auth'
import driver from './driver'
import municipality from './municipality'
import parking from './parking'

Vue.use(Vuex)

export default new Vuex.Store({
    modules: {
        map,
        auth,
        driver,
        municipality,
        parking
    }
})