import AXIOS from '@/config/axios'
import store from '@/store'

export default {
    async addParking(parking) {
        const access = await store.dispatch("fetchAccess")
        return AXIOS.post(`/add/parking/`+access.uid+'/'+access.token, parking)
    },
    async editParking(parking) {
        const access = await store.dispatch("fetchAccess")
        return AXIOS.post(`/edit/parking/`+access.uid+'/'+access.token, parking)
    },
    async removeParking(parkingId) {
        const access = await store.dispatch("fetchAccess")
        return AXIOS.post(`/remove/parking/`+access.uid+'/'+access.token+'/'+parkingId)
    }
}