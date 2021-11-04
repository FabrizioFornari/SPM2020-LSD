<template>
  <div class="menu">
    <component :is="role"></component>
    <div class="choice action logout" id="logoutButton" @click="signOut()"> Logout </div>
  </div>
</template>

<script>
import driverMenu from './driver/Menu'
import municipalityMenu from './municipality/Menu'
import { logout } from '@/firebase'

export default {
  name: 'choices',
  data() {
    return {
      role: this.$store.getters.userRole.toLowerCase() + 'Menu'
    }
  },
  methods: {
    signOut() {
      logout()
    }
  },
  components: {
    driverMenu,
    municipalityMenu
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