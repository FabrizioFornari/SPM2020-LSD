import AXIOS from '@/config/axios'

export default {
    async addParking(parking) {
        return AXIOS.post(`/add/parking/`, parking)
    },
    async editParking(parking) {
        return AXIOS.post(`/edit/parking/`, parking)
    },
    async removeParking(parking) {
        return AXIOS.post(`/remove/parking/`, parking)
    }
}