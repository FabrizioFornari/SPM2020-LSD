<template>
  <div id="app">
    <div id="nav">
      <router-link to="/login" v-if="!this.$store.getters.isLogged">Login</router-link> 
      <router-link to="/dashboard" v-else id="profileMenu">Profile</router-link>
    </div>
    <div id="nav-mobile">
      <router-link to="/map" class="mapIcon"><img src="./assets/map.svg"></router-link>
      <router-link to="/login" v-if="!this.$store.getters.isLogged"><img src="./assets/user.svg"></router-link> 
      <router-link to="/dashboard" v-else class="userIcon"><img src="./assets/user.svg"></router-link>
    </div>

    <div id="float" v-if="$route.meta.float && ready">
      <router-link to="/map" id="close" />
      <router-view :key="$route.fullPath" />
    </div>
    <div id="main" v-else-if="$route.name !== 'Home'">
      <div class="page">
        <router-view :key="$route.fullPath" />
      </div>
      <router-link to="/map" id="backhome" />
    </div>
    
    <Map v-if="ready" />
  </div>
</template>

<script>
import Map from '@/components/Map.vue'

export default {
  data() {
    return {
      ready: false
    }
  },
  components: {
    Map
  },
  async created() {
    const center = [45.449534, 9.179764] 
    this.$store.commit("setCenter", center)
    this.ready = true
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
  cursor: pointer;
}

button {
  border: none;
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

#float {
  width: 350px;
  height: 100vh;
  right: 0;
  padding: 25px 0;
  background-color: white;
  box-shadow: 0 0 30px #00000022;
  overflow-y: auto;
  position: fixed;
  z-index: 100;

  & #close {
    color: #00000033;
    
    &::after {
      top: 0;
      right: 15px;
      font-size: 30px;
      content: "\00d7";
      position: absolute;
    }
    &:hover {
      color: #00000099;
    }
  }
}

#main {
  width: max-content;
  height: 100vh;
  right: 0;
  box-shadow: 0 0 30px #00000022;
  position: fixed;
  z-index: 2;

  #backhome {
    width: 100vw;
    height: 100vh;
    top: 0;
    left: 0;
    position: fixed;

    &:hover {
      background-color: #00000011;
      cursor: url('assets/cross.png'), pointer;
    }
  }

  .page {
    height: 100%;
    padding-top: 30px;
    padding-bottom: 10px;
    background-color: #fff;
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
  align-items: center;
}

.container {
  height: 100%;
  max-width: 100% !important;
  min-width: 400px;
  padding: 0 5% !important;
  flex-flow: column;
  display: flex;

  &.width-40 {
    width: 40vw;
  }
  &.width-70 {
    width: 70vw;
  }
}

form {
  width: inherit;
  flex-flow: column;
  display: flex;

  & .details {
    margin: 0 15px;
  }
}

label {
    margin: 15px 0 !important;
    display: flex !important;
    flex: 1 1 auto;
    border: 1px solid #00000019;
    background-color: white;
    border-radius: 8px;
    transition: 0.3s;
    position: relative;

    & > label {
      margin: 0 !important;
      border: none !important;
      flex: 1 1 auto;

      &::after {
        content: '';
        height: 60%;
        align-self: center;
        border-right: 1px solid #00000019;
        transition: .3s;
      }

      &:hover::after {
        border-right: 1px solid #00000033;
      }

      &:last-child::after {
        border-right: none;
      }
    }
    
    & > .input {
        width: 100%;
        padding: 15px 20px 10px 20px;
        border: none;
        border-radius: 8px;
        outline: none;
    }

    & > span {
        margin-left: 10px;
        padding: 0 10px;
        background-color: white;
        color: #555;
        align-self: center;
        position: absolute;
        transition: 0.2s;
    }

    &:hover,
    &:focus-within {
      border: 1px solid #00000033;
    }

    &:focus-within > span,
    & .input:valid + span {
        margin-bottom: 50px;
        font-size: 10px;
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
    display: flex;
    flex-flow: row;
    align-items: center;
    justify-content: space-evenly;
    position: fixed;
    z-index: 100;

    &::before {
      content: "";
      width: 90%;
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

  #float {
    width: 100vw;
    min-width: 100vw;
    height: 170px;
    bottom: 0;
    border-radius: 15px 15px 0 0;
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