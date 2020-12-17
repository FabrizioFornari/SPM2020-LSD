import Vue from 'vue'
import api from '@/api/admin-auth'

const auth = {
    state: {
        admin: {
            loggedIn: false
        },
        user: {
            loggedIn: false,
            role: null,
            data: null
        }
    },
    mutations: {
        SET_ADMIN_STATE(state, value) {
            state.admin.loggedIn = value
        },
        SET_USER_STATE(state, value) {
            state.user.loggedIn = value
        },
        SET_USER_ROLE(state, role) {
            state.user.role = role
        },
        SET_USER(state, data) {
            state.user.data = data
        }
    },
    actions: {
        fetchAdmin({ commit }, { user, password }) {
            if (user === null) {
                return new Promise((resolve, reject) => {
                    api.logoutAdmin().then(response => {
                        if (response.status == 200) {
                            console.log("Logout successful")
                            commit("SET_ADMIN_STATE", false)
                            Vue.$cookies.remove('admin')
                        }
                        resolve(response)
                    })
                    .catch(error => {
                        console.log("Error: " + error)
                        reject()
                    })
                })
            }
            else return new Promise((resolve, reject) => {
                console.log("Accessing backend with user: '" + user + "'")
                api.loginAdmin(user, password).then(response => {
                    if (response.status == 200) {
                        console.log("Login successful")
                        commit("SET_ADMIN_STATE", true)
                        Vue.$cookies.set('admin', true)
                    }
                    resolve(response)
                })
                .catch(error => {
                    console.log("Error: " + error)
                    reject("Invalid credentials!")
                })
            })
        },
        fetchUser({ commit }, user) {
            commit("SET_USER_STATE", user !== null)
            if (user) {
                commit("SET_USER", {
                    name: user.displayName,
                    email: user.email,
                    uid: user.uid
                })
            } else {
                commit("SET_USER_ROLE", null)
                commit("SET_USER", null)
            }
        },
        fetchRole({ commit }, role) {
            if (role) commit("SET_USER_ROLE", role)
        }
    },
    getters: {
        isLoggedAdmin: state => state.admin.loggedIn,
        isLogged: state => state.user.loggedIn,
        userRole: state => state.user.role,
        user: state => state.user.data
    }
}

export default auth