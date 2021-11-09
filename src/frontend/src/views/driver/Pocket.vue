<template>
    <div class="menu">
        <div class="choices tickets">
            <router-link class="choice ticket" v-for="ticket in tickets" :key="ticket.id" :to="{ path: '/dashboard/vehicle/'+ticket.id }">
                <h4>{{ $store.getters.vehicles[ticket.vehicle].name }}</h4>
                <p>{{ ticket.type }} reservation</p>
            </router-link>
        </div>
    </div>
</template>

<script>
export default {
    name: 'Pocket',
    computed: {
        tickets() { return this.$store.getters.tickets }
    },
    watch: {
        tickets() { return }
    }
}
</script>

<style lang="scss" scoped>
.menu {
    .ticket {
        flex: 1 1 200px;
    }

    .add-vehicle {
        color: rgb(37, 66, 160);
        border: 1px solid #0010ec41;
        background-color: rgba(0, 63, 236, 0.255);
    }
}
</style>