import AXIOS from '@/config/axios'

const endpoint = '/ticket/'

export default {
    async getTickets() {
        return AXIOS.get(endpoint)
    },
    async buyTicket(ticket) {
        return AXIOS.post(endpoint, ticket)
    }
}