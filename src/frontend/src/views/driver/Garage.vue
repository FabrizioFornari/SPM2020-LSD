<template>
    <div class="menu">
        <div class="choices vehicles">
            <router-link class="choice vehicle" v-for="car in cars" :key="car.id" :to="{ path: '/dashboard/garage/'+car.id }">
                <h4>{{ car.name }}</h4>
                <p>{{ car.plate }}</p>
            </router-link>
        </div>
        <router-link class="choice action add-vehicle" to="/dashboard/garage/new" v-if="cars.length < 5"> 
            Add a vehicle 
        </router-link>

        <router-view></router-view>
    </div>
</template>

<script>
export default {
    name: 'garage',
    data() {
        return {
            cars: [
                {
                    id: "3285782750",
                    name: "La Jaguar",
                    plate: "CA220NE"
                },
                {
                    id: "5982764358",
                    name: "La devo vende",
                    plate: "AA0000H"
                }
            ]
        }
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