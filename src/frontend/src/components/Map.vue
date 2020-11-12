<template>
    <l-map id="homeMap"
        :zoom="zoom"
        :center="center"
        :options="mapOptions">
        <l-tile-layer :url="url" :attribution="attribution"/>
        <l-marker v-for="(marker, index) in markers"  
            :lat-lng="[marker.lat, marker.lon]" 
            :icon="myMarkerIcon"
            :key="index">
            <l-popup>
            <b>{{ marker.category }}</b> <br> 
            <i>{{ marker.address }}</i> <br> 
            {{ marker.date | formatDate }}
            </l-popup>
        </l-marker>
    </l-map>
</template>

<script>
import { LMap, LTileLayer, LMarker, LPopup } from 'vue2-leaflet'
import { latLng } from "leaflet"
import 'leaflet/dist/leaflet.css'

export default {
    name: 'map',  
    data() {
        return {
            markers: [],
            myMarkerIcon: window.L.icon({
            popupAnchor: [0, -40],
            iconAnchor: [12, 40],
            iconUrl: require('leaflet/dist/images/marker-icon.png'),
            shadowUrl: require('leaflet/dist/images/marker-shadow.png'),
            iconRetinaUrl: require('leaflet/dist/images/marker-icon-2x.png')
            }),

            zoom: 11,
            center: latLng(45.449534, 9.179764),
            url: 'https://tiles.stadiamaps.com/tiles/alidade_smooth/{z}/{x}/{y}{r}.png',
            // 'https://{s}.basemaps.cartocdn.com/light_all/{z}/{x}/{y}{r}.png'
            attribution:
                '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
            currentZoom: 11.5,
            currentCenter: latLng(45.449534, 9.179764),
            mapOptions: {
                zoomSnap: 0.5
            }
        }
    },
    components: {
        'l-map': LMap,
        'l-tile-layer': LTileLayer,
        'l-marker': LMarker,
        'l-popup': LPopup
    }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>
#homeMap {
    width: 100vw;
    height: 100vh;
    margin-top: 100px;
    margin-left: -6vw;
    z-index: 1;
}
</style>