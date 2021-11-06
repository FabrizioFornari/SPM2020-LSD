import AXIOS from '@/config/axios'

export default {
    async addVehicle(vehicle) {
        return AXIOS.post('/add/vehicle/', vehicle)
    },
    async buyTicket(ticket) {
        return AXIOS.post('/buy/ticket/', ticket)
    }
}