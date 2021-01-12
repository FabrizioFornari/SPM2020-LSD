import { fireStore } from '@/firebase'

const parking = {
    state: {
        parkings: {},
        vehicleTypes: ['car', 'motorcycle', 'caravan', 'bicycle', 'handicap'],
        center: [],
        active: null,
        waypoints: null,
        userPosition: null
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
        },
        setDestination(state, end) {
            state.waypoints = null
            state.waypoints = [state.userPosition, end]
        },
        setUserPosition(state, pos) {
            state.userPosition = pos
        }
    },
    getters: {
        parkings: state => state.parkings,
        vehicleTypes: state => state.vehicleTypes,
        center: state => state.center,
        active: state => state.active,
        waypoints: state => state.waypoints,
        userPosition: state => state.userPosition
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
        },
        async fetchWaypoints({ commit, rootGetters }, destination) {
            if (rootGetters.userPosition) commit('setDestination', destination)
            else alert("You have to set a starting point or you position to start routing")
        }
    }
}

export default parking