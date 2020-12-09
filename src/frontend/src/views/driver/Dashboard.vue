<template>
  <div class="container width-70">
    <h1><b-badge variant="success">Hi {{ name }}!</b-badge></h1>
    <h5>This is your dashboard</h5>
    <button @click="signOut()">Logout</button>

    <car-list />
  </div>
</template>

<script>
import store from '@/store/auth'
import { logout } from '@/firebase'
import CarList from '@/components/CarList.vue'
import CarList from './CarList.vue'

export default {
  name: 'dashboard',
  data () {
    return {
      name: store.getters.user.name,
    }
  },
  components: {
    CarList
  },
  methods: {
    signOut() {
      logout();
    }
  }
}
</script>