<template>
    <form v-if="id && !edit">
        <div class="details">
            <h3><b>{{ parking.name }}</b></h3>
            <div class="address">{{ parking.address }}, {{ parking.city }}</div>
            <p class="sub">Parking slots</p>
            <div v-for="(amount, type) in parking.slots" :key="type">{{type}}: {{amount}}</div>
            <p class="sub">Opening time</p>
            <div v-for="(day, d) of parking.days" :key="d">{{weekday[d]}}: {{day.start}} - {{day.end}}</div>
            <p class="sub">Price</p>
            <div>{{ parking.price }}/hour</div>
        </div>
        <div class="actions" v-if="this.$store.getters.userUid == parking.municipalityId">
            <router-link class="action save" :to="{path: '/map/parking/'+parking.id, query: {edit: true}}">Edit</router-link>
            <button class="action cancel" @click.prevent="removeParking">Remove</button>
        </div>
        <div class="actions" v-else>
            <button id="routeButton" class="action save" @click.prevent="findRoute">Route</button>
            <router-link class="action save" v-if="this.$store.getters.userRole != 'municipality'" :to="'/buy/ticket/'+parking.id">Buy</router-link>
            <button class="action save" v-if="this.$store.getters.userRole != 'municipality'">Pin it</button>
        </div>
    </form>
    <form v-else @submit.prevent="addParking()">
        <div class="details">
            <h3 v-if="edit"><b>Edit Parking</b></h3>
            <h3 v-else><b>New Parking</b></h3>
            <div class="address">{{ parking.address }}, {{ parking.city }}</div>
            <label class="label">
                <input type="text" class="input" id="parkingName" v-model.trim="parking.name" required autofocus>
                <span>Name</span>
            </label>

            <p class="sub">Parking slots</p>
            <div class="slider">
                <a v-for="type in $store.getters.vehicleTypes" :key="type" v-show="!(type in slots)" @click="$set(slots, type)">
                    {{ type }}
                </a>
            </div>
            <label class="label" :class="{ 'removable' : Object.keys(slots).length > 1 }" v-for="(slot, type) in slots" :key="type">
                <input type="number" min="1" class="input" v-model.number="slots[type]" required>
                <span>{{ type }} slots</span>
                <a v-if="Object.keys(slots).length > 1" @click="$delete(slots, type)">-</a>
            </label>

            <p class="sub">Opening time</p>
            <label class="label">
                <label class="label">
                    <input type="time" class="input" id="parkingStartTime" v-model="days.start" required>
                    <span>From</span>
                </label>
                <label class="label">
                    <input type="time" class="input" id="parkingEndTime" v-model="days.end" required>
                    <span>To</span>
                </label>
            </label>
            <label class="label">
                <input type="number" min="0" step=".01" class="input" id="parkingPrice" v-model.number="parking.price" required>
                <span>Price/h</span>
            </label>

            <p class="sub">Extra info</p>
            <b-form-checkbox
                v-for="(value, info) in infos"
                v-model="parking[info]"
                :key="info"> {{ info }}
            </b-form-checkbox>
        </div>

        <div class="actions" v-if="edit">
            <button class="action save" type="submit">Save</button>
            <router-link class="action cancel" :to="'/map/parking/'+parking.id">Cancel</router-link>
        </div>
        <div class="actions" v-else>
            <button class="action save" id="parkingAddButton" type="submit">Add</button>
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
            weekday: ['Sun','Mon','Tue','Wed','Thu','Fri','Sat'],
            parking: {
                id: null,
                address: null,
                city: null,
                name: null
            },
            slots: {
                car: 1
            },
            days: {},
            infos: {
                reservation: false,
                guarded: true
            }
        }
    },
    props: {
        id: {
            type: String
        }, 
        c: {
            type: String
        },
        edit: {
            type: Boolean
        }
    },
    methods: {
        async addParking() {
            this.parking.slots = this.slots
            //this.parking.days = this.days
            console.log(this.parking)
            this.parking.municipalityId = this.$store.getters.userUid
            api.addParking(this.parking).then(response => {
                if (response >= 200 && response < 300) {
                    this.$store.commit("addParking", this.parking)
                    this.$router.push('/map/parking/'+this.parking.id)
                }
            })
        },
        async removeParking() {
            api.removeParking(this.parking.id).then(response => {
                if (response >= 200 && response < 300) {
                    this.$store.commit("removeParking", this.parking.id)
                    this.$router.push('/map')
                }
            })
        },
        findRoute() {
            this.$store.dispatch("fetchWaypoints", latLng(this.parking.lat, this.parking.lon))
        }
    },
    async created() {
        if (this.id) {
            this.parking = { ...await this.$store.dispatch("fetchParking", this.id) }
            if (!this.parking || (this.edit && this.$store.getters.userUid != this.parking.municipalityId)) this.$router.push('/map')
            else {
                this.slots = { ...this.parking.slots }
                this.$store.commit("setActive", latLng(this.parking.lat, this.parking.lon))
            }
        }
        else if (this.c.indexOf(',') > -1) {
            const coords = this.c.split(',')
            await axios
                .get('https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat='+coords[0]+'&lon='+coords[1]+'&zoom=16')
                .then(response => {
                    this.parking.lat = response.data.lat
                    this.parking.lon = response.data.lon
                    this.parking.address = response.data.address.road || response.data.address.farm
                    this.parking.city = response.data.address.city || response.data.address.town || response.data.address.village
                    if (this.$store.getters.municipality.profile.city != this.parking.city) this.$router.push('/map')
                    else this.$store.commit("setActive", latLng(response.data.lat, response.data.lon))
                }) 
                .catch(error => {
                    this.$router.push('/map')
                })
        }
        else this.$router.push('/map')
    }
}
</script>

<style lang="scss" scoped>
.details {
    text-align: left;
    padding: 0 10px;
    padding-bottom: 80px;

    .address {
        font-style: italic;
    }

    .sub {
        margin-top: 35px;
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