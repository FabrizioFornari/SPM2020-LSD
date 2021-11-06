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
            account: {}
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
        SET_USER(state, account) {
            state.user.account = account
        }
    },
    getters: {
        isLoggedAdmin: state => state.admin.loggedIn,
        isLogged: state => state.user.loggedIn,
        userRole: state => state.user.account.type,
        userUid: state => state.user.account.id,
        user: state => state.user.account
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
        async fetchUser({ commit }, user) {
            commit("SET_USER_STATE", user !== null)
            if (!user) user = {}
            commit("SET_USER", user)
            commit("SET_USER_ROLE", user.type)
        }
    }
}

export default auth