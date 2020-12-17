import AXIOS from '@/config/axios'

export default {
    getMunicipalities() {
        return AXIOS.get(`/admin/view/municipalities`)
    },
    approveMunicipality(id: string) {
        return AXIOS.post(`/admin/approve/municipality/`+id)
    },
    rejectMunicipality(id: string) {
        return AXIOS.post(`/admin/reject/municipality/`+id)
    },
    enableMunicipality(id: string) {
        return AXIOS.post(`/admin/enable/municipality/`+id)
    },
    disableMunicipality(id: string) {
        return AXIOS.post(`/admin/disable/municipality/`+id)
    }
}