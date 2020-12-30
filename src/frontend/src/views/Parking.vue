<template>
    <div class="details">
        <h3 v-if="id"> {{ parking.name }} </h3>
        <h3 v-else> New Parking </h3>
        <div> Via e città </div>
    </div>
</template>

<script>
import { latLng } from 'leaflet'
export default {
    name: 'Parking',
    data() {
        return {
            parking: {}
        }
    },
    props: {
        id: {
            type: String
        }, 
        c: {
            type: Object
        }
    },
    async created() {
        if (this.id) {
            this.parking = await this.$store.dispatch("fetchParking", this.id)
            if (this.parking) this.$store.commit("setActive", latLng(this.parking.lat, this.parking.lon))
            else this.$router.push('/map')
        }
        else if (this.c) this.$store.commit("setActive", this.c)
    }
}
</script>

<style lang="scss" scoped>
.details {
    text-align: left;
}
</style>