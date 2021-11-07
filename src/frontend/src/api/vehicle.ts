import AXIOS from '@/config/axios'

const endpoint = '/vehicle/'

export default {
    async getVehicles() {
        return AXIOS.get(endpoint)
    },
    async addVehicle(vehicle, type: string) {
        return AXIOS.post(endpoint + type, vehicle)
    },
    async deleteVehicle(id: string) {
        return AXIOS.delete(endpoint + id)
    }
}