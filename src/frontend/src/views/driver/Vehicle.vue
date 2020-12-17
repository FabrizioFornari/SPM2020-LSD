<template>
    <div class="vehicle">
        <h4>{{ car.name }}</h4>
        <p>{{ car.plate }}</p>
    </div>
</template>

<script>
export default {
    data() {
        return {
            car: {
                name: "Ciao",
                plate: "ahgjha"
            }
        }
    },
    props: {

    }
}
</script>