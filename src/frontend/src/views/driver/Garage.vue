<template>
    <div class="menu">
        <div class="choices vehicles" v-if="status">
            <router-link class="choice vehicle" v-for="(car, index) in vehicles" :key="index" :to="{ path: '/dashboard/vehicle/'+car.id }">
                <h4>{{ car.name }}</h4>
                <p>My {{ car.type }}</p>
            </router-link>
        </div>
        <router-link class="choice action add-vehicle" to="/dashboard/garage/new"> 
            Add a vehicle 
        </router-link>

        <router-view></router-view>
    </div>
</template>

<script>
export default {
    name: 'garage',
    computed: {
        status() { return this.$store.getters.driver.status },
        vehicles() { return this.$store.getters.driver.profile.vehicles }
    },
    watch: {
        status(newStatus, oldStatus) { return },
        vehicles(newDriver, oldDriver) { return }
    }
}
</script>

<style lang="scss" scoped>
.menu {
    .vehicle {
        flex: 1 1 150px;
    }

    .add-vehicle {
        color: rgb(37, 66, 160);
        border: 1px solid #0010ec41;
        background-color: rgba(0, 63, 236, 0.255);
    }
}
</style>