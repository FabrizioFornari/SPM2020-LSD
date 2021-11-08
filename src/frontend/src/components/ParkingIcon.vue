<template>
    <div class="parkingIcon">
        <b>{{ price }}€<span>/h</span></b>
    </div>
</template>

<script>
export default {
    name: 'ParkingIcon',
    props: {
        price: {
            type: Number
        }
    }
}
</script>

<style lang="scss" scoped>
.parkingIcon {
    width: max-content;
    padding: 5px 10px;
    border-radius: 0 10px 10px 10px;
    border: 1px solid white;
    font-size: 16px;
    background-color: white;
    box-shadow: 0 5px 10px #0002;
    transition: .2s;

    &:hover {
        border: 1px solid #0008;
        box-shadow: 0 5px 10px #0003;
    }

    span {
        font-size: 14px;
    }
}
</style>