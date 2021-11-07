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
        setAdminState(state, value) {
            state.admin.loggedIn = value
        },
        setUserState(state, value) {
            state.user.loggedIn = value
        },
        setUserRole(state, role) {
            state.user.role = role
        },
        setUser(state, account) {
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
                            commit("setAdminState", false)
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
                        commit("setAdminState", true)
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
            commit("setUserState", user !== null)
            if (!user) user = {}
            console.log(user)
            commit("setUser", user)
            commit("setUserRole", user.type)
        }
    }
}

export default auth