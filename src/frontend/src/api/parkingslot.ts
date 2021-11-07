import AXIOS from '@/config/axios'

const endpoint = '/parkingslot/'

export default {
    async getSlot(id: string) {
        return AXIOS.get(endpoint + '?id=' + id)
    },
    async getSlotsByParking(parking: string) {
        return AXIOS.get(endpoint + '?parking=' + parking)
    },
    async getSlotsByParkingAndType(parking: string, type: string) {
        return AXIOS.get(endpoint + '?parking=' + parking + "&type=" + type)
    },
    async getSlotsByArea(topright, botleft) {
        return AXIOS.get(endpoint + '?topright=' + topright + '&botleft=' + botleft)
    },
    async addSlot(slot) {
        return AXIOS.post(endpoint, slot)
    },
    async deleteSlot(id: string) {
        return AXIOS.delete(endpoint + id)
    }
}