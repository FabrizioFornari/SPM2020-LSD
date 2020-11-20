<template>
  <div>
  </div>
</template>

<script>
import { GeoSearchControl } from 'leaflet-geosearch'

export default {
  props: {
    options: {
      required: true,
    },
  },
  name: 'v-geosearch',
  mounted() {
    this.add();
  },
  beforeDestroy() {
    this.remove();
  },
  methods: {
    deferredMountedTo(parent) {
      new GeoSearchControl(this.options).addTo(parent);
    },
    remove() {
      if (this.markerCluster) {
        this.$parent.removeLayer(this.markerCluster);
      }      
    },
    add() {
      if (this.$parent._isMounted) {
        this.deferredMountedTo(this.$parent.mapObject);
      }
    },
  },
};
</script>

<style>
  @import url("https://unpkg.com/leaflet-geosearch/dist/geosearch.css");
</style>