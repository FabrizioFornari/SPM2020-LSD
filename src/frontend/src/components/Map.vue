<template>
    <div class="map">
        <l-map id="homeMap" ref="homeMap"
            :zoom="zoom"
            :center="center"
            :options="mapOptions"
            @ready="handleEvents">
            <l-tile-layer :url="url" :attribution="attribution"/>
            <l-search :options="searchOptions" />
            <l-locate-control :options="{icon: 'fa fa-crosshairs'}" />
            <l-marker v-for="(marker, index) in markers"  
                :lat-lng="[marker.coords.latitude, marker.coords.longitude]" 
                :icon="myMarkerIcon"
                :key="index">
                <l-popup>
                    <b>{{ marker.name }}</b>
                </l-popup>
            </l-marker>
        </l-map>
    </div>
</template>

<script>
import { LMap, LTileLayer, LMarker, LPopup } from 'vue2-leaflet'
import Vue2LeafletLocatecontrol from 'vue2-leaflet-locatecontrol/Vue2LeafletLocatecontrol'
import { OpenStreetMapProvider } from 'leaflet-geosearch'
import Geosearch from '@/components/Geosearch'
import L from 'leaflet'
import { latLng } from 'leaflet'
import 'leaflet/dist/leaflet.css'
import { fireStore, geoPoint } from '@/firebase'

export default {
    name: 'spark-map',  
    data() {
        return {
            autoSearch: false,
            zoom: 11,
            center: latLng(45.449534, 9.179764),
            url: 'https://tiles.stadiamaps.com/tiles/alidade_smooth/{z}/{x}/{y}{r}.png',
            // 'https://{s}.basemaps.cartocdn.com/light_all/{z}/{x}/{y}{r}.png'
            attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
            currentZoom: 11.5,
            currentCenter: latLng(45.449534, 9.179764),
            mapOptions: {
                zoomSnap: 0.5
            },

            markers: [],
            myMarkerIcon: L.icon({
                popupAnchor: [0, -40],
                iconAnchor: [12, 40],
                iconUrl: require('leaflet/dist/images/marker-icon.png'),
                shadowUrl: require('leaflet/dist/images/marker-shadow.png'),
                iconRetinaUrl: require('leaflet/dist/images/marker-icon-2x.png')
            }),

            searchOptions: {
                provider: new OpenStreetMapProvider(),
                style: 'bar',
                showMarker: false
            }
        }
    },
    components: {
        'l-map': LMap,
        'l-tile-layer': LTileLayer,
        'l-marker': LMarker,
        'l-popup': LPopup,
        'l-locate-control': Vue2LeafletLocatecontrol,
        'l-search': Geosearch
    },
    methods: {
        handleEvents(map) {
            map.on('geosearch/showlocation', (ev) => {
                const bounds = ev.location.bounds
                fireStore.collection('Parkings')
                    .where('coords', '>=', new geoPoint(bounds[0][0], bounds[0][1]))
                    .where('coords', '<=', new geoPoint(bounds[1][0], bounds[1][1]))
                    .get()
                    .then((querySnapshot) => {
                        querySnapshot.forEach((doc) => {
                            this.markers.push(doc.data())
                        })
                    })
                    .catch((error) => {
                        console.log("Error getting documents: ", error);
                    })
            })
            map.on('moveend', (ev) => {
                if (this.autoSearch) console.log(map.getBounds()); 
            })
        }
    }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>
#homeMap {
    width: 100%;
    height: 100vh;
    z-index: 1;
}
</style>