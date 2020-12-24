<template>
  <div class="menu">
    <div class="choices">
      <router-link class="choice" id="profileButton" to="/dashboard/profile">
          <h3> View Profile </h3> 
          <p> See your private account details and modify them </p>
      </router-link>
      <router-link class="choice" id="garageButton" to="/dashboard/garage"> 
          <h3> My Garage! </h3> 
          <p> Watch all your vehicles, add more or modify existing ones </p>
      </router-link>
    </div>
    <div class="choice action logout" id="logoutButton" @click="signOut()"> Logout </div>
  </div>
</template>

<script>
import { logout } from '@/firebase'

export default {
  name: 'choices',
  methods: {
    signOut() {
      logout()
    }
  }
}
</script>

<style lang="scss" scoped>
.logout {
  border: 1px solid #ec000041;
  background-color: #ff1c1c54;
  color: white;
}
</style>