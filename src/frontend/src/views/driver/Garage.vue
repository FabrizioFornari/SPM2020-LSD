<template>
    <div class="menu">
        <div class="choices vehicles">
            <router-link class="choice vehicle" v-for="car in vehicles" :key="car.id" :to="{ path: '/dashboard/vehicle/'+car.id }">
                <h4>{{ car.name }}</h4>
                <p>My {{ car.type }}</p>
            </router-link>
        </div>
        <router-link class="choice action add-vehicle btn btn-primary" to="/dashboard/garage/new"> 
            Add a vehicle 
        </router-link>

        <router-view></router-view>
    </div>
</template>

<script>
export default {
    name: 'Garage',
    computed: {
        vehicles() { return this.$store.getters.vehicles }
    },
    watch: {
        vehicles() { return }
    }
}
</script>

<style lang="scss" scoped>
.menu {
    .vehicle {
        flex: 1 1 200px;
    }
}
</style>