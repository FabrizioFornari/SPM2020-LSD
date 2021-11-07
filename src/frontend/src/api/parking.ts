import AXIOS from '@/config/axios'

const endpoint = '/parking/'

export default {
    async getParking(id: string) {
        return AXIOS.get(endpoint + '?id=' + id)
    },
    async getParkings() {
        return AXIOS.get(endpoint)
    },
    async getParkingsByArea(topright, botleft) {
        return AXIOS.get(endpoint + '?topright=' + topright + '&botleft=' + botleft)
    },
    async getParkingsByAreaAndType(topright, botleft, type) {
        return AXIOS.get(endpoint + '?topright=' + topright + '&botleft=' + botleft + '&type=' + type)
    },
    async addParking(parking) {
        return AXIOS.post(endpoint, parking)
    },
    async editParking(parking) {
        return AXIOS.post(endpoint, parking)
    },
    async deleteParking(id: string) {
        return AXIOS.delete(endpoint + id)
    }
}