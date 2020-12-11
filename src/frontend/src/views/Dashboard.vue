<template>
  <div class="container width-70">
    <div class="header">
      <h1 class="welcome"><b> Hi {{ name }}! </b></h1>
      <h5> What you wanna do today? </h5>
    </div>

    <div class="sections">
      <router-view></router-view>
      <div class="menu" v-if="$route.fullPath == '/dashboard'">
        <div class="choice"> 
          <h3> View Profile </h3> 
          <p> See your private account details and modify them </p>
        </div>
        <router-link class="choice" to="/dashboard/garage"> 
          <h3> My Garage! </h3> 
          <p> Watch all your vehicles, add more or modify existing ones </p>
        </router-link>
        <div class="choice logout" @click="signOut()"> Logout </div>
      </div>
    </div>
  </div>
</template>

<script>
import store from '@/store/auth'
import { logout } from '@/firebase'

export default {
  name: 'dashboard',
  data () {
    return {
      name: store.getters.user.name
    }
  },
  methods: {
    signOut() {
      logout()
    }
  }
}
</script>

<style lang="scss" scoped>
.header {
  padding-left: 15px;
  text-align: left;

  .welcome {
    font-size: 3em;
  }
}

.sections {
  margin-top: auto;
}

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