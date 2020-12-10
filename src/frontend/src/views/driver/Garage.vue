<template>
    <div class="vehicles">
        <div class="vehicle" v-for="car in cars" :key="car.id">
            <h4>{{ car.name }}</h4>
            <p>{{ car.plate }}</p>
        </div>
        <div class="vehicle add-vehicle" v-if="cars.length < 5"> Add a vehicle </div>
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
.vehicles {
    flex-flow: row wrap;
    justify-content: center;
    display: flex;

    .vehicle {
        margin: 15px;
        padding: 10px;
        border: 1px solid #00000011;
        border-radius: 8px;
        flex: 1 1 120px;
        flex-flow: column;
        justify-content: center;
        display: flex;

        &:hover {
            box-shadow: 0 5px 10px #00000011;
            cursor: pointer;
        }

        &.add-vehicle {
          padding: 10px;
          border: 1px solid #0010ec41;
          background-color: rgba(0, 63, 236, 0.255);
        }
    }
}
</style>