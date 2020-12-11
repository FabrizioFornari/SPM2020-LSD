import AXIOS from '@/config/axios'

export default {
    addParking(uid: string, token: string, parking: any) {
        return AXIOS.post(`/municipality/`+uid+'/'+token+'/insert/parking/'+parking.name+'/'+parking.lat+'/'+parking.lon);
    }
}