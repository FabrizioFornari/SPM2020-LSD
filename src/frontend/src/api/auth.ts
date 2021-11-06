import AXIOS from '@/config/axios'

export default {
    getUser() {
        return AXIOS.get(`/account/`);
    },
    newUser(user, type: string) {
        return AXIOS.post(`/account/` + type, user);
    }
}