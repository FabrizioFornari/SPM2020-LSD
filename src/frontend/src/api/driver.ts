import AXIOS from '@/config/axios'
import store from '@/store'

export default {
    async addVehicle(vehicle) {
        const access = await store.dispatch("fetchAccess")
        return AXIOS.post(`/add/vehicle/`+access.uid+'/'+access.token, vehicle)
    },
    async buyTicket(ticket) {
        const access = await store.dispatch("fetchAccess")
        return AXIOS.post(`/buy/ticket/`+access.uid+'/'+access.token, ticket)
    }
}