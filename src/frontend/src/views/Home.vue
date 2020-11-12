<template>
  <div class="home">
    <Map />
  </div>
</template>

<script>
import { Component, Vue } from 'vue-property-decorator';
import Map from '@/components/Map.vue';

export default {
  name: 'home', 
  components: {
    Map
  }
} 
</script>
