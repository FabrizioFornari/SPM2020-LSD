<template>
  <div class="menu">
    <router-link class="choice" to="/dashboard/profile">
        <h3> View Profile </h3> 
        <p> See your private account details and modify them </p>
    </router-link>
    <router-link class="choice" to="/dashboard/garage"> 
        <h3> My Garage! </h3> 
        <p> Watch all your vehicles, add more or modify existing ones </p>
    </router-link>
    <div class="choice logout" @click="signOut()" id="logoutButton"> Logout </div>
  </div>
</template>

<script>
import { logout } from '@/firebase'

export default {
  name: 'menu',
  methods: {
    signOut() {
      logout()
    }
  }
}
</script>

<style lang="scss" scoped>
.menu {
  flex-flow: row wrap;
  justify-content: center;
  display: flex;

  .choice {
    margin: 15px;
    padding: 15px;
    color: black;
    border: 1px solid #00000011;
    border-radius: 8px;
    flex: 1 1 300px;
    flex-flow: column;
    justify-content: center;
    display: flex;

    &.logout {
      padding: 10px;
      border: 1px solid #ec000041;
      background-color: #ff1c1c54;
      color: white;
    }

    &:hover {
      box-shadow: 0 5px 10px #00000011;
      cursor: pointer;
    }

    p {
      margin: 0;
      font-size: 0.9em;
    }
  }
}
</style>