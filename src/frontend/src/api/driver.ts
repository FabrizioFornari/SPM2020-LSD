import AXIOS from '@/config/axios'
import { fireAuth } from '@/firebase';

export default {
    async addVehicle(vehicle) {
        const token = await fireAuth.currentUser.getIdToken()
        return AXIOS.post(`/add/vehicle/`+this.$store.getters.user.uid+'/'+token,vehicle);
    }
}