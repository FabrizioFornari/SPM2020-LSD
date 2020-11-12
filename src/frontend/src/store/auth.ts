import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        user: {
            loggedIn: false,
            data: null
        }
    },
    mutations: {
        SET_USER_STATE(state, value) {
            state.user.loggedIn = value;
        },
        SET_USER(state, data) {
            state.user.data = data;
        }
    },
    actions: {
        fetchUser({ commit }, user) {
            commit("SET_USER_STATE", user !== null);
            if (user) {
                commit("SET_USER", {
                    name: user.displayName,
                    email: user.email,
                    uid: user.uid
                });
            } else {
                commit("SET_USER", null);
            }
        }
    },
    getters: {
        isLogged: state => state.user.loggedIn,
        user: state => state.user.data
    }
})