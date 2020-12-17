import AXIOS from '@/config/axios'

export default {
    registerUser(user, type: string) {
        return AXIOS.post(`/register/`+type,user);
    }
}