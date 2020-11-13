<template>
  <div id="app">
    <div id="nav">
      <router-link to="/login" v-if="!this.$store.getters.isLogged">Login</router-link> 
      <router-link to="/dashboard" v-else>Profilo</router-link>
    </div>

    <div id="main" v-if="$route.fullPath !== '/'">
      <router-view :key="$route.fullPath"/>
      <router-link to="/" id="backhome"></router-link>
    </div>
    <Map />
  </div>
</template>

<script>
import Map from '@/components/Map.vue' // @ is an alias to /src

export default {
  components: {
    Map
  }
} 
</script>

<style lang="scss">
@import "~leaflet.markercluster/dist/MarkerCluster.css";
@import "~leaflet.markercluster/dist/MarkerCluster.Default.css";

html,
body {
  margin: 0;
  padding: 0;
}

a:hover {
  text-decoration: none !important;
}

#app {
  height: 100vh;
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  right: 0;
  background-color: #fff;
  box-shadow: 0 10px 20px #00000019;
  margin: 10px;
  padding: 10px 20px;
  border-radius: 10px;
  position: fixed;
  z-index: 100;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}

#main {
  width: 40%;
  min-width: 400px;
  height: 100vh;
  right: 0;
  background-color: #fff;
  box-shadow: 0 0 30px #00000022;
  overflow-y: auto;
  position: fixed;
  z-index: 2;

  #backhome {
    width: 60%;
    height: 100vh;
    top: 0;
    left: 0;
    position: fixed;
  }
}

.justify-content-center {
  height: 100vh;
  min-height: 300px;
  align-items: center;
}
</style>
