import Vue from 'vue'

const parking = {
    state: {
        parkings: {}
    },
    mutations: {
        addParking(state, parking) {
            Vue.set(state.parkings, parking.id, parking)
        },
        addParkings(state, parkings) {
            for (const parking of parkings)
                Vue.set(state.parkings, parking.id, parking)
        },
        setParkings(state, parkings) {
            state.parkings = parkings
        },
        deleteParking(state, parkingId) {
            Vue.delete(state.parkings, parkingId)
        }
    },
    getters: {
        parkings: state => state.parkings
    },
    actions: {
        fetchParking({ commit }, parking) {
            commit("addParking", parking)
        },        
        fetchParkings({ commit }, parkings) {
            commit("setParkings", {})
            commit("addParkings", parkings)
        },
        deleteParking({ commit }, id) {
            commit("deleteParking", id)
        }
    }
}

export default parking