<template>
    <div class="slotIcon">
        <img :src="getImg(type)">
    </div>
</template>

<script>
export default {
    name: 'SlotIcon',
    props: {
        type: {
            type: String
        }
    },
    methods: {
        getImg(type) {
            if (type) return require('../assets/' + type + '.png')
        }
    }
}
</script>

<style lang="scss" scoped>
.slotIcon {
    width: 36px;
    height: 36px;
    border-radius: 20px 20px 0px 20px;
    border: 1px solid white;
    font-size: 16px;
    background-color: white;
    box-shadow: 0 5px 10px #0002;
    transition: .2s;
    transform: rotate(45deg);
    display: flex;
    padding: 5px;

    &:hover {
        box-shadow: 0 5px 10px #0003;
    }

    img {
        transform: rotate(-45deg);
    }
}
</style>