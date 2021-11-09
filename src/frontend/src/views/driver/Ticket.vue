<template>
    <form @submit.prevent="buyTicket()">
        <div class="details">
            <h3><b>Buy Ticket</b></h3>
            <i>{{ parking.address }}, {{ parking.city }}</i>
            
            <p class="sub">Choose vehicle</p>
            <div class="picker">
                <label v-for="vehicle of $store.getters.vehicles" v-show="types.includes(vehicle.type)"
                    :key="vehicle.id"
                    :class="{ 'selected' : vehicle.id == ticket.vehicle }" >
                    <input type="radio" name="vehicle" :value="vehicle.id" v-model="ticket.vehicle" required>
                    <h5>{{ vehicle.name }}</h5>
                    <p>My {{ vehicle.type }}</p>
                </label>
            </div>

            <p class="sub">Time</p>
            <label class="label">
                <input type="datetime-local" class="input" v-model="date.start" @input="priceCalculator" required>
                <span>From</span>
            </label>
            <label class="label">
                <input type="datetime-local" class="input" v-model="date.end" :min="date.start" @input="priceCalculator" required>
                <span>To</span>
            </label>
            <h3 class="sub">Total: {{ ticket.price || 0 }}€</h3>
        </div>

        <div class="actions">
            <button class="action save" type="submit">Buy</button>
            <router-link class="action cancel" :to="'/map/parking/'+parking.id">Undo</router-link>
        </div>
    </form>
</template>

<script>
import apiTicket from '@/api/ticket'
import apiParking from '@/api/parking'
import apiSlot from '@/api/slot'
import { latLng } from 'leaflet'
import moment from 'moment'

export default {
    name: 'Parking',
    data() {
        return {
            date: {
                start: null,
                end: null
            },
            ticket: {
                vehicle: null,
                payer: this.$store.getters.userUid,
                parking: this.slotid ? this.slotid : this.parkingid,
                type: this.slotid ? "SLOT" : "PARKING"
            },
            parking: {
                address: null,
                city: null,
                name: null,
                slots: {}
            },
            slot: {},
            types: []
        }
    },
    props: {
        parkingid: {
            type: String
        },
        slotid: {
            type: String
        }
    },
    methods: {
        async buyTicket() {
            this.ticket.inception = moment(this.date.start).format('YYYY-MM-DD HH:mm')
            this.ticket.expiration = moment(this.date.end).format('YYYY-MM-DD HH:mm')
            await apiTicket.buyTicket(this.ticket).then(response => {
                this.$store.dispatch('fetchTicket', response.data)
                this.$router.push('/dashboard/pocket')
                console.log(this.$store.getters.tickets)
            })
        },
        priceCalculator() {
            if (this.date.start && this.date.end) {
                const length = moment(this.date.end) - moment(this.date.start)
                this.ticket.price = length > 0 ? (length/1000/60/60*this.parking.price).toFixed(2) : null
            }
        }
    },
    async created() {
        await apiParking.getParking(this.parkingid).then(async response => {
            this.parking = response.data
            if (!this.parking) this.$router.push('/map')
            await apiSlot.getSlotsByParking(this.parkingid).then(response => {
                this.$store.dispatch("fetchSlots", response.data)
                this.slot = this.$store.getters.slots[this.slotid]
                if (this.slotid && !this.slot) return this.$router.push('/map')
            })
        })
        this.types = this.slot ? [this.slot.type] : Object.keys(this.parking.slots)
        const active = this.slot ? latLng(this.slot.coords.y, this.slot.coords.x) : latLng(this.parking.coords.y, this.parking.coords.x)
        this.$store.commit("setActive", active)
    }
}
</script>

<style lang="scss" scoped>
.details {
    text-align: left;
    padding: 0 10px;
    padding-bottom: 70px;

    .sub {
        margin-top: 35px;
        font-weight: bold;
    }
}

.picker {
    display: flex;

    label {
        min-width: max-content;
        margin-right: 15px;
        padding: 5px 15px;
        border: 1px solid #00000019;
        border-radius: 8px;
        display: flex;
        flex-flow: column;
        align-items: center;
        cursor: pointer;

        &.selected  {
            color: black;
            border: 1px solid;

            img {
                opacity: 1;
            }
        }

        &:hover {
            border: 1px solid;
        }

        h5 {
            margin: 0;
        }

        input {
            left: 0;
            opacity: 0;
            position: absolute;
        }

        p {
            margin: 0;
            font-size: 14px;
        }
    }
}
</style>