<template>
  <div class="home">
    <Map />
  </div>
</template>

<script>
import { Component, Vue } from 'vue-property-decorator';
import Map from '@/components/Map.vue'; // @ is an alias to /src

export default {
  name: 'home', 
  components: {
    Map
  }
} 
</script>
