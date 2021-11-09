import Vue from 'vue'

const slot = {
    state: {
        slots: {},
        newSlots: []
    },
    mutations: {
        addSlots(state, slots) {
            for (const slot of slots)
                Vue.set(state.slots, slot.id, slot)
        },
        setSlots(state, slots) {
            state.slots = slots
        },
        deleteSlot(state, slotId) {
            Vue.delete(state.slots, slotId)
        },
        addNewSlot(state, slot) {
            state.newSlots.push(slot)
        },
        deleteNewSlot(state, index) {
            state.newSlots.splice(index, 1)
        },
        setNewSlots(state, slots) {
            state.newSlots = slots
        }
    },
    getters: {
        slots: state => state.slots,
        newSlots: state => state.newSlots
    },
    actions: {
        addSlots({ commit }, slots) {
            commit("addSlots", slots)
        },
        fetchSlots({ commit }, slots) {
            commit("setSlots", {})
            commit("addSlots", slots)
        },
        deleteSlot({ commit }, slot) {
            commit("deleteSlot", slot)
        },
        addNewSlot({ commit }, coords) {
            const slot = {coords: { x: coords.lng, y: coords.lat }};
            commit("addNewSlot", slot)
        },
        resetNewSlots({ commit }) {
            commit("setNewSlots", [])
        }
    }
}

export default slot