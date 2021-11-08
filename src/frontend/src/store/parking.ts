import Vue from 'vue'

const parking = {
    state: {
        parkings: {},
        slots: {}
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
        },
        setSlots(state, slots) {
            state.slots = slots
        }
    },
    getters: {
        parkings: state => state.parkings,
        slots: state => state.slots
    },
    actions: {
        fetchParking({ commit }, parking) {
            commit("addParking", parking)
        },        
        fetchParkings({ commit }, parkings) {
            if (!parkings) commit("setParkings", {})
            else commit("addParkings", parkings)
        },
        deleteParking({ commit }, id) {
            commit("deleteParking", id)
        },
        fetchSlots({ commit }, slots) {
            commit("setSlots", slots)
        }
    }
}

export default parking