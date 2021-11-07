import AXIOS from '@/config/axios'

const endpoint = '/account/'

export default {
    getUser() {
        return AXIOS.get(endpoint);
    },
    newUser(user, type: string) {
        return AXIOS.post(endpoint + type, user);
    }
}