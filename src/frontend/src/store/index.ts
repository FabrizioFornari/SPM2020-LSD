import Vue from 'vue'
import Vuex from 'vuex'
import map from './map'
import auth from './auth'
import parking from './parking'
import slot from './slot'
import ticket from './ticket'
import vehicle from './vehicle'
import apiParking from '@/api/parking'
import apiTicket from '@/api/ticket'
import apiVehicle from '@/api/vehicle'

Vue.use(Vuex)

export default new Vuex.Store({
    modules: {
        map,
        auth,
        parking,
        slot,
        ticket,
        vehicle
    },
    actions: {
        async initialize({ dispatch, getters }, user) {
            let parkings = null, 
                tickets = null, 
                vehicles = null;
            dispatch("fetchUser", user)
            if (user) {
                const role = getters.userRole
                if (role == "POLICEMAN" || role == "DRIVER") {
                    tickets = (await apiTicket.getTickets()).data
                    vehicles = (await apiVehicle.getVehicles()).data
                } else if (role == "MUNICIPALITY") {
                    parkings = (await apiParking.getParkings()).data
                }
            }
            if (parkings) dispatch("fetchParkings", parkings)
            dispatch("fetchTickets", tickets)
            dispatch("fetchVehicles", vehicles)
        }
    }
})