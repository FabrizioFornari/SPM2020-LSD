import Vue from 'vue'

const vehicle = {
    state: {
        vehicles: {},
        vehicleTypes: ['BICYCLE', 'CAR', 'CARAVAN', 'MOTORCYCLE']
    },
    mutations: {
        addVehicle(state, vehicle) {
            Vue.set(state.vehicles, vehicle.id, vehicle)
        },
        addVehicles(state, vehicles) {
            for (const vehicle of vehicles)
                Vue.set(state.vehicles, vehicle.id, vehicle)
        },
        setVehicles(state, vehicles) {
            state.vehicles = vehicles
        },
        deleteVehicle(state, vehicleId) {
            Vue.delete(state.vehicles, vehicleId)
        }
    },
    getters: {
        vehicles: state => state.vehicles,
        vehicleTypes: state => state.vehicleTypes
    },
    actions: {
        fetchVehicle({ commit }, vehicle) {
            commit("addVehicle", vehicle)
        },
        fetchVehicles({ commit }, vehicles) {
            if (!vehicles) commit("setVehicles", {})
            else commit("addVehicles", vehicles)
        },
        deleteVehicle({ commit }, id) {
            commit("deleteVehicle", id)
        }
    }
}

export default vehicle