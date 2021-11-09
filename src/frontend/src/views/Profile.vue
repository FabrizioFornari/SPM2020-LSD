<template>
    <div id="profile">
        <div><b>Email: </b>{{ user.email }}</div>
        <div v-if="user.name"><b>Name: </b>{{ user.name }}</div>
        <div v-if="user.surname"><b>Surname: </b>{{ user.surname }}</div>
        <div v-if="user.city"><b>City: </b>{{ user.city }}</div>
        <div v-if="user.province"><b>Province: </b>{{ user.province }}</div>
        <div v-if="user.region"><b>Region: </b>{{ user.region }}</div>
    </div>
</template>

<script>
export default {
    name: 'Profile',
    data() {
        return {
            user: this.$store.getters.user
        }
    },
}
</script>

<style lang="scss" scoped>
#profile {
    padding: 30px 15px;
    display: flex;
    flex-flow: column;
    align-items: flex-start;

    div {
        font-size: 1.2rem;
    }
}
</style>