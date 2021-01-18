import { fireStore } from '@/firebase'

const municipality = {
    state: {
        municipality: {
            profile: {}
        }
    },
    mutations: {
        setMunicipality(state, municipality) {
            state.municipality.profile = municipality
            state.municipality.status = true
        }
    },
    getters: {
        municipality: state => state.municipality
    },
    actions: {
        async fetchMunicipality({ commit, rootGetters }) {
            const municipality = await fireStore.collection('Municipality').doc(rootGetters.userUid).get()
            if (!municipality.exists) return null
            commit('setMunicipality', municipality.data())
            return municipality.data()
        }
    }
}

export default municipality