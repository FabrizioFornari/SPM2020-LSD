<template>
    <form @submit.prevent="buyTicket()">
        <div class="details">
            <h3><b>Buy Ticket</b></h3>
            <div class="address">{{ parking.address }}, {{ parking.city }}</div>
            
            <p class="sub">Time and Price</p>
            <label class="label">
                <input type="time" class="input" v-model="days.time" required>
                <input type="time" class="input" v-model="parking.time" required>
                <span>Opening time</span>
            </label>
            <label class="label">
                <input type="number" min="0" step=".01" class="input" v-model.number="parking.price" required>
                <span>Price/h</span>
            </label>
        </div>

        <div class="actions">
            <button class="action save" type="submit">Buy</button>
            <router-link class="action cancel" :to="'/map/parking/'+parking.id">Undo</router-link>
        </div>
    </form>
</template>

<script>
import api from '@/api/driver'

export default {
    name: 'Parking',
    data() {
        return {
            parking: {
                address: null,
                city: null,
                name: null
            },
            slots: {},
            days: {},
            edit: false
        }
    },
    props: {
        id: {
            type: String
        }
    },
    methods: {
        async buyTicket() {
            this.parking.slots = this.slots
            console.log(this.parking)
        }
    },
    async created() {
        this.parking = { ...await this.$store.dispatch("fetchParking", this.id) }
        if (!this.parking) this.$router.push('/map')
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
        margin-top: 25px;
    }
}
</style>