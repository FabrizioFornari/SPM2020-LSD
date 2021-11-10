<template>
    <form>
        <div class="details">
            <h3><b>{{ parking.name }}</b></h3>
            <i>{{ parking.address }}, {{ parking.city }}</i>
            <p class="sub">Slot identifier</p>
            <div>{{ slot.id }}</div>
            <p class="sub">Slot type</p>
            <div>{{ slot.type }}</div>
            <p class="sub">Price</p>
            <div>{{ parking.price }}€/hour</div>
        </div>
        <div class="actions" v-if="this.$store.getters.userUid == parking.owner">
            <router-link class="pill btn-primary" :to="{path: '/map/parking/'+parking.id, query: {edit: true}}" id="slotEditButton" :disabled="loading">Edit</router-link>
            <button class="pill btn-danger" @click.prevent="deleteSlot" id="slotRemoveButton" :disabled="loading">Remove</button>
        </div>
        <div class="actions" v-else>
            <button id="routeButton" class="pill btn-primary" @click.prevent="findRoute" :disabled="loading">Route</button>
            <router-link class="pill btn-primary" v-if="this.$store.getters.userRole != 'municipality'" :to="'/buy/ticket/'+parking.id+'/'+slot.id" :disabled="loading">Buy</router-link>
        </div>
    </form>
</template>

<script>
import apiParking from '@/api/parking'
import apiSlot from '@/api/slot'
import { latLng } from 'leaflet'

export default {
    name: 'ParkingSlot',
    data() {
        return {
            parking: {},
            slot: {},
            loading: false
        }
    },
    props: {
        id: {
            type: String
        }
    },
    methods: {
        deleteSlot() {
            this.loading = true
			apiSlot.deleteSlot(this.slot.id).then(response => {
                this.$store.dispatch('deleteSlot', response.data)
                this.$router.push('/map')
            })
            this.loading = false
        },
        findRoute() {
            this.$store.dispatch("fetchWaypoints", latLng(this.parking.coords.y, this.parking.coords.x))
        }
    },
    async created() {
        if (!this.id) return this.$router.push('/map')

        await apiSlot.getSlot(this.id).then(async response => {
            this.slot = response.data
            if (!this.slot) return this.$router.push('/map')
            await apiParking.getParking(this.slot.parking).then(async response => {
                this.parking = response.data
                this.$store.dispatch("fetchParking", this.parking)
                await apiSlot.getSlotsByParking(this.parking.id).then(response => {
                    this.$store.dispatch("fetchSlots", response.data)
                })
            })
        })
        this.$store.commit("setActive", latLng(this.slot.coords.y, this.slot.coords.x))
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

    .slider {
        display: flex;

        & a {
            margin-right: 10px;
            padding: 0 10px;
            border: 1px solid #00000033;
            border-radius: 30px;

            &:hover {
                border: 1px solid black;
            }      
        }
    }

    .removable {
        align-items: center;
        margin-right: 45px !important;

        & > a {
            width: 30px;
            height: 30px;
            right: -45px;
            border-radius: 100%;
            background-color: #00000013;
            display: flex;
            justify-content: center;
            position: absolute;

            &:hover {
                background-color: #f71b1b62;
            }      
        }
    }
}
</style>