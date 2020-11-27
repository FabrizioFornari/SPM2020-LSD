<template>
  <div class="container width-70">
    <h1><b-badge variant="success">Hi {{ name }}!</b-badge></h1>
    <h5>This is your dashboard</h5>
    <button @click="signOut()">Logout</button>
  </div>
</template>

<script>
import store from '@/store/auth'
import { logout } from '@/firebase'

export default {
  name: 'dashboard',
  data () {
    return {
      name: store.getters.user.name,
    }
  },
  methods: {
    signOut() {
      logout();
    }
  }
}

</script>