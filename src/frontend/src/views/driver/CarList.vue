<template>
    <div>
        <div v-for="(car, index) in cars" :key="index">
            <h2>{{ car.name }}</h2>
            <p>{{ car.plate }}</p>
        </div>
        <div v-if="cars.length < 5"> Add a car </div>
    </div>
</template>

<script>
export default {
    name: 'car-list',
    data() {
        return {
            cars: []
        }
    }
}
</script>

<style scoped>

</style>