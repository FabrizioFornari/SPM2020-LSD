import AXIOS from '@/config/axios'

const endpoint = '/parking/'

export default {
    async getParking(id: string) {
        return AXIOS.get(endpoint + '?id=' + id)
    },
    async getParkings() {
        return AXIOS.get(endpoint)
    },
    async getParkingsByArea(ne, sw) {
        return AXIOS.get(endpoint + '?ne=' + ne + '&sw=' + sw)
    },
    async getParkingsByAreaAndType(ne, sw, type) {
        return AXIOS.get(endpoint + '?ne=' + ne + '&sw=' + sw + '&type=' + type)
    },
    async addParking(parking) {
        return AXIOS.post(endpoint, parking)
    },
    async editParking(parking) {
        return AXIOS.post(endpoint + 'edit', parking)
    },
    async deleteParking(id: string) {
        return AXIOS.delete(endpoint + id)
    }
}