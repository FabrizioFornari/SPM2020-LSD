<template>
    <form v-if="id && !edit">
        <div class="details">
            <h3>{{ parking.name }}</h3>
            <div>{{ parking.address }}, {{ parking.city }}</div>
        </div>
        <div class="actions" v-if="this.$store.getters.userUid == parking.municipalityId">
            <button class="action save" @click="edit = true">Edit</button>
            <button class="action save" type="submit">Cancel</button>
        </div>
        <div class="actions" v-else>
            <button class="action save" type="submit">Route</button>
            <button class="action save" type="submit" v-if="this.$store.getters.userRole != 'municipality'">Buy</button>
            <button class="action save" type="submit" v-if="this.$store.getters.userRole != 'municipality'">Pin it</button>
        </div>
    </form>
    <form v-else @submit.prevent="edit ? editParking() : addParking()">
        <div class="details">
            <h3 v-if="edit">Edit Parking</h3>
            <h3 v-else>New Parking</h3>
            <div>{{ parking.address }}, {{ parking.city }}</div>
            <label>
                <input type="text" class="input" v-model="parking.name" required autofocus>
                <span>Name</span>
            </label>
            <label>
                <input type="number" min="0" class="input" v-model.number="slots.car">
                <span>Car slots</span>
            </label>
            <label>
                <input type="number" min="0" class="input" v-model.number="slots.handicap">
                <span>Handicap slots</span>
            </label>
            <label>
                <input type="number" min="0" class="input" v-model.number="slots.motorcycle">
                <span>Motorcycle slots</span>
            </label>
            <label>
                <input type="time" class="input" v-model="parking.time" required>
                <input type="time" class="input" v-model="parking.time" required>
                <span>Opening time</span>
            </label>
            <label>
                <input type="number" min="0" step=".01" class="input" v-model.number="parking.price" required>
                <span>Price/h</span>
            </label>
        </div>

        <div class="actions">
            <button class="action save" type="submit" v-if="edit">Save</button>
            <button class="action save" type="submit" v-else>Add</button>
            <router-link class="action cancel" to="/map">Cancel</router-link>
        </div>
    </form>
</template>

<script>
import axios from 'axios'
import api from '@/api/municipality'
import { latLng } from 'leaflet'

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
            edit: false
        }
    },
    props: {
        id: {
            type: String
        }, 
        c: {
            type: String
        }
    },
    methods: {
        async addParking() {
            this.parking.slots = this.slots
            console.log(this.parking)
        }
    },
    async created() {
        if (this.id) {
            this.parking = await this.$store.dispatch("fetchParking", this.id)
            if (this.parking) this.$store.commit("setActive", latLng(this.parking.lat, this.parking.lon))
            else this.$router.push('/map')
        }
        else if (this.c && this.c.indexOf(',') > -1) {
            const coords = this.c.split(',')
            await axios
                .get('https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat='+coords[0]+'&lon='+coords[1]+'&zoom=16')
                .then(response => {
                    this.parking.lat = response.data.lat
                    this.parking.lon = response.data.lon
                    this.parking.address = response.data.address.road || response.data.address.farm
                    this.parking.city = response.data.address.city || response.data.address.town || response.data.address.village
                    this.$store.commit("setActive", latLng(response.data.lat, response.data.lon))
                }) 
                .catch(error => {
                    this.$router.push('/map')
                })
        }
    }
}
</script>

<style lang="scss" scoped>
.details {
    text-align: left;
}

.actions {
    width: inherit;
    height: 60px;
    bottom: 0;
    background-color: white;
    position: fixed;

    &::before {
        content: "";
        width: 85%;
        height: 1px;
        top: 0;
        left: 7.5%;
        background-color: #ddd;
        position: absolute;
    }

    & .action {
        margin: 15px;
    }
}
</style>