<template>
    <div class="vehicles">
        <div class="vehicle" v-for="car in cars" :key="car.id">
            <h4>{{ car.name }}</h4>
            <p>{{ car.plate }}</p>
        </div>
        <div class="add-vehicle" v-if="cars.length < 5"> Add a vehicle </div>
    </div>
</template>

<script>
export default {
    
}
</script>