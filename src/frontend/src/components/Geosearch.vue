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

<style lang="scss">
  @import url("https://unpkg.com/leaflet-geosearch/dist/geosearch.css");

  .leaflet-geosearch-bar {
    width: 400px;
    max-width: calc(100% - 120px);
    left: 50%;
    bottom: 20px;
    transform: translateX(-50%);
    position: fixed;
    cursor: auto;

    form {
      border: none !important;
      box-shadow: 0 10px 20px #00000019 !important;
      flex-flow: column-reverse;
      display: flex;

      input {
        height: 50px !important;
        font-size: 16px;
        text-indent: 10px;
      }

      a.reset {
        top: auto;
        bottom: 0;
        height: 50px;
        padding: 10px;
      }

      .results.active {
        border-top: 0 !important;
        border-bottom: 1px solid #ddd;
      }
    }
  }
</style>