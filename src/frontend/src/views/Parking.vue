<template>
    <form v-if="id && !edit">
        <div class="details">
            <h3><b>{{ parking.name }}</b></h3>
            <i>{{ parking.address }}, {{ parking.city }}</i>
            <p class="sub">Parking slots</p>
            <div v-for="(amount, type) in parking.slots" :key="type">{{type}}: {{amount}}</div>
            <!--<p class="sub"><b>Opening time</b></p>
            <div v-for="(day, d) of parking.days" :key="d">{{weekday[d]}}: {{day.start}} - {{day.end}}</div>-->
            <p class="sub">Price</p>
            <div>{{ parking.price }}€/hour</div>
        </div>
        <div class="actions" v-if="this.$store.getters.userUid == parking.owner">
            <router-link class="pill btn-primary" :to="{path: '/map/parking/'+parking.id, query: {edit: true}}" id="parkingEditButton" :disabled="loading">Edit</router-link>
            <router-link class="pill btn-primary" :to="{path: '/map/slots', query: {p: parking.id}}" id="parkingSlotsButton" :disabled="loading">Add slots</router-link>
            <button class="pill btn-danger" @click.prevent="deleteParking" id="parkingRemoveButton" :disabled="loading">Remove</button>
        </div>
        <div class="actions" v-else>
            <button id="routeButton" class="pill btn-primary" @click.prevent="findRoute" :disabled="loading">Route</button>
            <router-link class="pill btn-primary" v-if="this.$store.getters.userRole != 'municipality'" :to="'/buy/ticket/'+parking.id" :disabled="loading">Buy</router-link>
        </div>
    </form>
    <form v-else @submit.prevent="edit ? editParking() : addParking()">
        <div class="details">
            <h3 v-if="edit"><b>Edit Parking</b></h3>
            <h3 v-else><b>New Parking</b></h3>
            <i>{{ parking.address }}, {{ parking.city }}</i>
            <label class="label">
                <input type="text" class="input" id="parkingName" v-model.trim="parking.name" required autofocus>
                <span>Name</span>
            </label>

            <p class="sub">Parking slots</p>
            <div class="slider">
                <a v-for="type in $store.getters.vehicleTypes" :key="type" v-show="!(type in slots)" @click="$set(slots, type)">
                    {{ type.toLowerCase() }}
                </a>
            </div>
            <label class="label" :class="{ 'removable' : Object.keys(slots).length > 1 }" v-for="(slot, type) in slots" :key="type">
                <input type="number" min="1" class="input" v-model.number="slots[type]" required>
                <span>{{ type.toLowerCase() }} slots</span>
                <a v-if="Object.keys(slots).length > 1" @click="$delete(slots, type)">-</a>
            </label>

            <p class="sub">Opening time</p>
            <label class="label">
                <label class="label">
                    <input type="time" class="input" id="parkingStartTime" v-model="days.start">
                    <span>From</span>
                </label>
                <label class="label">
                    <input type="time" class="input" id="parkingEndTime" v-model="days.end">
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
            <button class="pill btn-primary" id="parkingSaveButton" type="submit" :disabled="loading">Save</button>
            <router-link class="pill btn-secondary" :to="'/map/parking/'+parking.id" :disabled="loading">Cancel</router-link>
        </div>
        <div class="actions" v-else>
            <button class="action save pill btn-primary" id="parkingAddButton" type="submit" :disabled="loading">Add</button>
            <router-link class="pill btn-secondary" to="/map" :disabled="loading">Cancel</router-link>
        </div>
    </form>
</template>

<script>
import axios from 'axios'
import apiParking from '@/api/parking'
import apiSlot from '@/api/slot'
import { latLng } from 'leaflet'

export default {
    name: 'Parking',
    data() {
        return {
            weekday: ['Sun','Mon','Tue','Wed','Thu','Fri','Sat'],
            parking: {
                address: null,
                city: null,
                name: null
            },
            slots: {
                CAR: 1
            },
            days: {},
            infos: {
                guarded: true
            },
            loading: false
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
        addParking() {
            this.loading = true
			this.parking.slots = this.slots
            this.parking.owner = this.$store.getters.userUid
            apiParking.addParking(this.parking).then(response => {
                this.$store.dispatch('fetchParking', response.data)
                this.$router.push('/map')
            })
            this.loading = false
        },
        editParking() {
            this.loading = true
			this.parking.slots = this.slots
            this.parking.owner = this.$store.getters.userUid
            apiParking.editParking(this.parking).then(response => {
                this.$store.dispatch('fetchParking', response.data)
                this.$router.push('/map/parking/' + response.data.id)
            })
            this.loading = false
        },
        deleteParking() {
            this.loading = true
			apiParking.deleteParking(this.parking.id).then(response => {
                this.$store.dispatch('deleteParking', response.data)
                this.$router.push('/map')
            })
            this.loading = false
        },
        findRoute() {
            this.$store.dispatch("fetchWaypoints", latLng(this.parking.coords.y, this.parking.coords.x))
        }
    },
    async created() {
        if (this.id) {
            await apiParking.getParking(this.id).then(async response => {
                this.parking = response.data
                if (!this.parking) return this.$router.push('/map')
                this.$store.dispatch("fetchParking", this.parking)
                await apiSlot.getSlotsByParking(this.id).then(response => {
                    this.$store.dispatch("fetchSlots", response.data)
                })
            })
            if (this.edit && this.$store.getters.userUid != this.parking.owner) return this.$router.push('/map/parking/'+this.id)
            this.slots = { ...this.parking.slots }
            this.$store.commit("setActive", latLng(this.parking.coords.y, this.parking.coords.x))
        }
        else if (this.c.indexOf(',') > -1) {
            const coords = this.c.split(',')
            await axios
                .get('https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat='+coords[0]+'&lon='+coords[1]+'&zoom=16&accept-language=it')
                .then(response => {
                    const coords = {
                        x: response.data.lon,
                        y: response.data.lat
                    }
                    this.parking.coords = coords
                    this.parking.address = response.data.address.road || response.data.address.farm
                    this.parking.city = response.data.address.city || response.data.address.town || response.data.address.village
                    
                    if (this.$store.getters.user.city != this.parking.city) this.$router.push('/map')
                    else this.$store.commit("setActive", latLng(coords.y, coords.x))
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