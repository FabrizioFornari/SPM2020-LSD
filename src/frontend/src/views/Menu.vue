<template>
  <div class="menu">
    <municipalityMenu v-if="role == 'municipality'"></municipalityMenu>
    <driverMenu v-else></driverMenu>
    <div class="choice action logout btn btn-danger" id="logoutButton" @click="signOut()"> Logout </div>
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
      role: this.$store.getters.userRole.toLowerCase()
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