<template>
    <div class="menu">
        <div class="choices parkings">
            <router-link class="choice parking" v-for="(parking, index) in parkings" :key="index" :to="'/map/parking/' + parking.id">
                <h4>{{ parking.name }}</h4>
                <p>{{ parking.address }}</p>
            </router-link>
        </div>
    </div>
</template>

<script>
import apiParking from '@/api/parking'

export default {
    name: 'Parkings',
    data() {
        return {
            parkings: []
        }
    },
    async created() {
        await apiParking.getParkings().then(response => {
            this.parkings = response.data
        })
    },
}
</script>

<style lang="scss" scoped>
.menu {
    .parking {
        flex: 1 1 200px;
    }
}
</style>