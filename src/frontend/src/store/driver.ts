import { fireStore } from '@/firebase'

const driver = {
    state: {
        driver: {
            profile: {},
            tickets: {}
        }
    },
    mutations: {
        setDriver(state, driver) {
            state.driver.profile = driver
        },
        addTicket(state, ticket) {
            state.driver.tickets[ticket.id] = ticket
        }
    },
    getters: {
        driver: state => state.driver
    },
    actions: {
        async fetchDriver({ commit, rootGetters }) {
            const driver = await fireStore.collection('Users').doc(rootGetters.userUid).get()
            if (!driver.exists) return null
            commit('setDriver', driver.data())
            return driver.data()
        }
    }
}

export default driver