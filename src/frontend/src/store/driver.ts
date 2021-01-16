import { fireStore } from '@/firebase'

const driver = {
    state: {
        driver: {
            status: false,
            profile: {},
            vehicles: {},
            tickets: {}
        }
    },
    mutations: {
        setDriver(state, driver) {
            state.driver.profile = driver
            state.driver.status = true
        },
        addVehicle(state, vehicle) {
            state.driver.vehicles[vehicle.id] = vehicle
            state.driver.profile.vehicles[vehicle.id] = {id: vehicle.id, name: vehicle.name, type: vehicle.type}
        },
        addTicket(state, ticket) {
            state.driver.tickets[ticket.id] = ticket
        }
    },
    getters: {
        driver: state => state.driver,
        vehicles: state => state.driver.vehicles
    },
    actions: {
        async fetchDriver({ commit, rootGetters }) {
            if (rootGetters.driver.status) return rootGetters.driver
            const driver = await fireStore.collection('Users').doc(rootGetters.userUid).get()
            if (!driver.exists) return null
            commit('setDriver', driver.data())
            return driver.data()
        },
        async fetchVehicle({ commit, rootGetters}, id) {
            if (rootGetters.vehicles[id]) return rootGetters.vehicles[id]
            const vehicle = await fireStore.collection('Vehicles').doc(id).get()
            if (!vehicle.exists) return null
            commit('addVehicle', vehicle.data())
            return vehicle.data()
        }
    }
}

export default driver