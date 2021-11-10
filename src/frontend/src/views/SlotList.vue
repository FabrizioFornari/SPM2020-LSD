<template>
    <form @submit.prevent="addSlots()">
        <div class="details">
            <h3><b>Add Slots</b></h3>
            <i>{{ parking.name }}</i>

            <p class="sub">New slots ({{ newSlots.length }})</p>
            <div v-for="(slot, index) of newSlots" :key="index" class="slot">
                <div>
                    <i>Number {{ index+1 }}</i>
                    <select name="type" v-model="slot.type" required>
                        <option v-for="(value, type) of parking.slots" :key="type" :value="type">{{ type.toLowerCase() }}</option>
                    </select>
                </div>
                <button class="btn btn-danger" @click="$store.commit('deleteNewSlot', index)">X</button>
            </div>
        </div>

        <div class="actions">
            <button class="pill btn-primary" id="parkingAddButton" type="submit" :disabled="loading">Add</button>
            <router-link class="pill btn-secondary" to="/map" :disabled="loading">Cancel</router-link>
        </div>
    </form>
</template>

<script>
import apiParking from '@/api/parking'
import apiSlot from '@/api/slot'
import { latLng } from 'leaflet'
import store from '@/store'

export default {
    name: 'SlotList',
    data() {
        return {
            parking: {},
            loading: false
        }
    },
    props: {
        p: {
            type: String
        }
    },
    methods: {
        addSlots() {
            this.disabled = true
            for (const slot of store.getters.newSlots) slot.parking = this.p
            apiSlot.addSlots(store.getters.newSlots).then(response => {
                store.dispatch("addSlots", response.data)
                this.$router.push('/map/parking/' + this.p)
            })
            this.disabled = false
        }
    },
    async created() {
        if (!this.p) return this.$router.push('/map')
        
        await apiParking.getParking(this.p).then(async response => {
            this.parking = response.data
            if (!this.parking) return this.$router.push('/map')
            store.dispatch("fetchParking", this.parking)
            await apiSlot.getSlotsByParking(this.parking.id).then(response => {
                store.dispatch("fetchSlots", response.data)
            })
        })
        if (store.getters.userUid != this.parking.owner) return this.$router.push('/map')
        store.commit("setActive", latLng(this.parking.coords.y, this.parking.coords.x))
        store.commit("setInsertSlots", true)
    },
    beforeDestroy() {
        store.commit("setInsertSlots", false)
        store.dispatch("resetNewSlots")
    },
    computed: {
        newSlots() { return store.getters.newSlots }
    },
    watch: {
        newSlots() { return }
    }
}
</script>

<style lang="scss" scoped>
.details {
    text-align: left;
    padding: 0 10px;
    padding-bottom: 80px;

    .sub {
        margin-top: 35px;
        font-weight: bold;
    }

    .slot {
        margin-bottom: 10px;
        padding: 10px;
        border: 1px solid #00000020;
        border-radius: 10px;
        display: flex;
        transition: .2s;

        &:hover {
            box-shadow: 0 5px 10px #00000020;
            cursor: pointer;
        }

        div {
            padding-right: 20px;
            display: flex;
            flex: 1;
            flex-flow: column;
        }
    }
}
</style>