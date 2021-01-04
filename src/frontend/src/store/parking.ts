import { fireStore } from '@/firebase'

const parking = {
    state: {
        parkings: {},
        center: [],
        active: null
    },
    mutations: {
        addParking(state, parking) {
            state.parkings[parking.id] = parking
        },
        addParkings(state, parkings) {
            for (const key in parkings)
                state.parkings[key] = parkings[key]
        },
        setCenter(state, center) {
            state.center = center
        },
        setActive(state, active) {
            state.active = active
            state.center = active
        }
    },
    getters: {
        parkings: state => state.parkings,
        center: state => state.center,
        active: state => state.active
    },
    actions: {
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