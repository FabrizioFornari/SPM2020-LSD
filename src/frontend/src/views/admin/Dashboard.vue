<template>
  <div class="container">
    <div class="row justify-content-center">
      <h1><b-badge variant="success">{{ welcome }}</b-badge></h1>
      <button @click="signOut()">Logout</button>
    </div>
  </div>
</template>

<script>
import auth from '@/store/auth'
import api from '@/api/admin-auth'

export default {
  name: 'adminDash',
  data () {
    return {
      welcome: 'Admin dashboard'
    }
  },
  mounted() {
    console.log("Admin section")
  },
  methods: {
    signOut() {
      auth.dispatch('fetchAdmin', { user: null })
        .then(() => this.$router.push('/'))
    }
  }
}

</script>