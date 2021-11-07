import Vue from 'vue'

const ticket = {
    state: {
        tickets: {}
    },
    mutations: {
        addTicket(state, ticket) {
            Vue.set(state.tickets, ticket.id, ticket)
        },
        addTickets(state, tickets) {
            for (const ticket of tickets)
                Vue.set(state.tickets, ticket.id, ticket)
        },
        setTickets(state, tickets) {
            state.tickets = tickets
        },
        removeTicket(state, ticketId) {
            Vue.delete(state.tickets, ticketId)
        }
    },
    getters: {
        tickets: state => state.vehicles
    },
    actions: {
        fetchTicket({ commit }, ticket) {
            commit("addTicket", ticket)
        },
        fetchTickets({ commit }, tickets) {
            if (!tickets) commit("setVehicles", {})
            else commit("addVehicles", tickets)
        }
    }
}

export default ticket