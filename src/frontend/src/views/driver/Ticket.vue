<template>
    <form @submit.prevent="buyTicket()">
        <div class="details">
            <h3><b>Buy Ticket</b></h3>
            <div class="address">{{ parking.address }}, {{ parking.city }}</div>
            
            <p class="sub">Choose vehicle</p>
            <div class="picker">
                <div v-for="vehicle of $store.getters.driver.profile.vehicles" :key="vehicle.id" 
                    :class="{ 'selected' : vehicle.id == ticket.vehicle }" 
                    @click="ticket.vehicle = vehicle.id">
                    <img :src="require('./../../assets/'+vehicle.type+'.png')">
                    {{ vehicle.name }}
                </div>
            </div>

            <p class="sub">Time</p>
            <label class="label">
                <label class="label">
                    <input type="time" class="input" v-model="ticket.start" @input="priceCalculator" required>
                    <span>From</span>
                </label>
                <label class="label">
                    <input type="time" class="input" :min="ticket.start" v-model="ticket.end" @input="priceCalculator" required>
                    <span>To</span>
                </label>
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
import api from '@/api/driver'
import { latLng } from 'leaflet'

export default {
    name: 'Parking',
    data() {
        return {
            ticket: {
                vehicle: null
            },
            parking: {
                address: null,
                city: null,
                name: null,
                slots: null
            }
        }
    },
    props: {
        id: {
            type: String
        }
    },
    methods: {
        async buyTicket() {
            //this.$store.commit("addTicket", this.ticket)
            console.log(this.ticket)
        },
        priceCalculator() {
            if (this.ticket.start && this.ticket.end) {
                const hours = this.ticket.end.split(':')[0] - this.ticket.start.split(':')[0]
                let minutes = this.ticket.end.split(':')[1] - this.ticket.start.split(':')[1]

                if (hours>0) minutes = 60*hours + minutes
                if (minutes>0) this.ticket.price = (minutes * this.parking.price / 60).toFixed(2)
                else this.ticket.price = null
            }
        }
    },
    async created() {
        this.parking = { ...await this.$store.dispatch("fetchParking", this.id) }
        if (!this.parking) this.$router.push('/map')
        else this.$store.commit("setActive", latLng(this.parking.lat, this.parking.lon))
    }
}
</script>

<style lang="scss" scoped>
.details {
    text-align: left;
    padding: 0 10px;
    padding-bottom: 70px;

    .address {
        font-style: italic;
    }

    .sub {
        margin-top: 35px;
    }
}

.picker {
    display: flex;

    & div {
        margin-right: 15px;
        padding: 0 15px;
        border: 1px solid #00000019;
        border-radius: 8px;
        display: flex;
        flex-flow: column;
        align-items: center;
        cursor: pointer;

        &:hover {
            border: 1px solid;
        }

        & img {
            width: 60px;
            opacity: .3;
        }

        &.selected  {
            color: black;
            background-color: aliceblue;
            border: 1px solid;

            img {
                opacity: 1;
            }
        }
    }
}
</style>