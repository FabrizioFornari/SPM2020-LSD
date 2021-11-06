const map = {
    state: {
        center: null,
        active: null,
        waypoints: null,
        userPosition: null
    },
    mutations: {
        setCenter(state, center) {
            state.center = center
        },
        setActive(state, active) {
            state.active = active
        },
        setWaypoints(state, points) {
            state.waypoints = points
        },
        setDestination(state, end) {
            state.waypoints = null
            state.waypoints = [state.userPosition, end]
        },
        setUserPosition(state, pos) {
            state.userPosition = pos
        }
    },
    getters: {
        center: state => state.center,
        active: state => state.active,
        waypoints: state => state.waypoints,
        userPosition: state => state.userPosition
    },
    actions: {
        async fetchWaypoints({ commit, rootGetters }, destination) {
            if (rootGetters.userPosition) commit('setDestination', destination)
            else alert("You have to set a starting point or your position to start routing")
        }
    }
}

export default map