import AXIOS from '@/config/axios'

export default {
    loginAdmin(user: string, password: string) {
        return AXIOS.get(`/admin/login`,{
            auth: {
                username: user,
                password: password
            }}
        );
    },
    logoutAdmin() {
        return AXIOS.post(`/admin/logout`);
    }
}