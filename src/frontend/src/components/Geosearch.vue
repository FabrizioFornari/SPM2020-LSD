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
    width: 500px;
    max-width: calc(100% - 100px);
    min-width: 400px;
    left: 50%;
    bottom: 20px;
    border-radius: 5px;
    background-color: #fff;
    box-shadow: 0 10px 20px rgba(0,0,0,.09803921568627451);
    transform: translateX(-50%);
    overflow: hidden;
    position: absolute;
    cursor: auto;

    form {
      border: none !important;
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

  @media (max-width: 400px) {
    .leaflet-geosearch-bar {
      width: 100%;
      max-width: 100%;
      min-width: 100%;
      bottom: 0;
      border-radius: 15px 15px 0 0;
    }
  }
</style>