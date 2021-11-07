import { fireStore } from '@/firebase'
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
            for (const id in parkings)
                Vue.set(state.parkings, id, parkings[id])
        },
        setParkings(state, tickets) {
            state.tickets = tickets
        },
        removeParking(state, parkingId) {
            Vue.delete(state.parkings, parkingId)
        }
    },
    getters: {
        parkings: state => state.parkings
    },
    actions: {        
        fetchParkings({ commit }, parkings) {
            if (!parkings) commit("setParkings", {})
            else commit("addParkings", parkings)
        },
        async fetchParking({ commit, rootGetters }, id) {
            if (rootGetters.parkings[id]) return rootGetters.parkings[id]
            const parking = await fireStore.collection('Parkings').doc(id).get()
            if (!parking.exists) return null
            commit('addParking', parking.data())
            return parking.data()
        },
        async coordParking({ rootGetters }, id) {
            if (rootGetters.parkings[id]) return [rootGetters.parkings[id].lat, rootGetters.parkings[id].lon]
            const parking = await fireStore.collection('Parkings').doc(id).get()
            if (!parking.exists) return null
            return [parking.data().lat, parking.data().lon]
        }
    }
}

export default parking