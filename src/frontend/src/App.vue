<template>
  <div id="app">
    <div id="nav">
      <router-link to="/login" v-if="!this.$store.getters.isLogged">Login</router-link> 
      <router-link to="/dashboard" v-else id="profileMenu">Profile</router-link>
    </div>
    <div id="nav-mobile">
      <router-link to="/" class="mapIcon"><img src="./assets/map.svg"></router-link>
      <router-link to="/login" v-if="!this.$store.getters.isLogged"><img src="./assets/user.svg"></router-link> 
      <router-link to="/dashboard" v-else class="userIcon"><img src="./assets/user.svg"></router-link>
    </div>

    <div id="main" v-if="$route.fullPath !== '/'">
      <div class="page">
        <router-view :key="$route.fullPath"/>
      </div>
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
  box-shadow: 0 10px 20px #00000019;
  margin: 10px;
  border-radius: 10px;
  overflow: hidden;
  position: fixed;
  z-index: 100;

  a {
    padding: 10px 20px;
    background-color: #fff;
    font-weight: bold;
    color: #2c3e50;
    display: block;
  }
}

#nav-mobile {
  display: none;
}

#main {
  width: max-content;
  min-width: 400px;
  height: 100vh;
  right: 0;
  background-color: #fff;
  box-shadow: 0 0 30px #00000022;
  position: fixed;
  z-index: 2;

  #backhome {
    width: 100vw;
    height: 100vh;
    top: 0;
    left: 0;
    position: fixed;
  }

  .page {
    height: 100%;
    padding-top: 30px;
    padding-bottom: 10px;
    overflow-x: hidden;
    overflow-y: auto;
    position: relative;
    z-index: 1;

    .dashboard {
      height: 100vh;
      flex-flow: column;
      align-items: center;
      justify-content: center;
      display: flex;
    }
  }
}

.justify-content-center {
  height: 100%;
  min-height: 300px;
  align-items: center;
}

.container {
  height: 100%;
  max-width: 100% !important;
  min-width: 400px;
  padding: 0 6% !important;
  flex-flow: column;
  display: flex;

  &.width-40 {
    width: 40vw;
  }
  &.width-70 {
    width: 70vw;
  }
}


@media (max-width: 400px) {
  #nav {
    display: none;
  }

  #nav-mobile {
    width: 100vw;
    height: 60px;
    bottom: 0;
    background-color: #fff;
    box-shadow: 0 10 20px #00000019;
    display: flex;
    flex-flow: row;
    align-items: center;
    justify-content: space-evenly;
    position: fixed;
    z-index: 100;

    &::before {
      content: "";
      width: 85%;
      height: 1px;
      top: 0;
      background-color: #ddd;
      position: absolute;
    }

    a {
      width: 60px;
      height: 100%;
      display: flex;
      justify-content: center;

      &.router-link-exact-active img {
        width: 28px;
      }
    }

    img {
      width: 25px;
      transition: .1s;
    }
  }

  #main {
    width: 100%;
    min-width: 100%;
    padding-bottom: 60px;

    #backhome {
      display: none;
    }

    .container {
      width: 100% !important;
      min-width: 100%;
      flex-flow: column;
      display: flex;
    }
  }
}
</style>