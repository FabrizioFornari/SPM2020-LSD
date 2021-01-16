<template>
    <l-map id="map" ref="map"
        :zoom="zoom"
        :minZoom="minZoom"
        :maxZoom="maxZoom"
        :center="center"
        :options="mapOptions"
        @ready="handleEvents"
        @update:zoom="hideMarkers"
        @click="addParking">
        <l-tile-layer :url="url" :attribution="attribution"/>
        <l-search :options="searchOptions" />
        <l-routing v-if="waypoints" :waypoints="waypoints" :icon="activeIcon" :key="waypoints" />
        <l-locate-control :options="{icon: 'fa fa-crosshairs'}" />
        <l-control class="leaflet-bar leaflet-control" :position="'topleft'"> 
            <a class="autoSearch fa fa-repeat" v-bind:class="{ active: autoSearch }" @click="autoSearch = !autoSearch" /> 
        </l-control>
        <l-marker v-if="active" :lat-lng="active" :icon="activeIcon" :zIndexOffset="10" />
        <l-group v-if="markerVisible">
            <l-marker v-for="marker in parkings"
                :lat-lng="[marker.lat, marker.lon]"
                :icon="markerIcon"
                :key="marker.id"
                @click="showParking(marker.id)">
            </l-marker>
        </l-group>
    </l-map>
</template>

<script>
import { LMap, LTileLayer, LMarker, LLayerGroup, LControl } from 'vue2-leaflet'
import Vue2LeafletLocatecontrol from 'vue2-leaflet-locatecontrol/Vue2LeafletLocatecontrol'
import { OpenStreetMapProvider } from 'leaflet-geosearch'
import Geosearch from '@/components/Geosearch'
import Routing from '@/components/Routing'
import L, { latLng } from 'leaflet'
import 'leaflet/dist/leaflet.css'
import { fireStore, geoPoint } from '@/firebase'
import * as turf from '@turf/turf'
import municipalityApi from '@/api/municipality'
import store from '@/store'

export default {
    name: 'spark-map',  
    data() {
        return {
            autoSearch: false,
            locSearch: false,
            searchBounds: null,
            searchSpots: [],

            zoom: 11,
            minZoom: 3,
            maxZoom: 18,
            url: 'https://tiles.stadiamaps.com/tiles/alidade_smooth/{z}/{x}/{y}{r}.png',
            // 'https://{s}.basemaps.cartocdn.com/light_all/{z}/{x}/{y}{r}.png'
            attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
            mapOptions: {
                zoomDelta: 0.5,
                zoomSnap: 0.25
            },

            userPosition: null,
            markerVisible: true,
            markerIcon: L.icon({
                popupAnchor: [0, -40],
                iconAnchor: [12, 40],
                iconUrl: require('leaflet/dist/images/marker-icon.png'),
                shadowUrl: require('leaflet/dist/images/marker-shadow.png')
            }),
            activeIcon: L.icon({
                popupAnchor: [0, -40],
                iconAnchor: [12, 40],
                iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-red.png',
                shadowUrl: require('leaflet/dist/images/marker-shadow.png')
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
        'l-group': LLayerGroup,
        'l-control': LControl,
        'l-locate-control': Vue2LeafletLocatecontrol,
        'l-search': Geosearch,
        'l-routing': Routing
    },
    methods: {
        handleEvents(map) {
            this.parseAreas(map.getBounds(), map)
            map.on('geosearch/showlocation', (ev) => {
                this.locSearch = true
                this.parseAreas(L.latLngBounds(ev.location.bounds), map)
            })
            map.on('moveend', () => {
                if (this.locSearch) this.locSearch = false
                else if (this.autoSearch && map.getZoom() > 10) this.parseAreas(map.getBounds(), map)
            })
            map.on('locationfound', (ev) => {
                this.$store.commit('setUserPosition', ev.latlng)
            })
            map.on('locationerror', (ev) => {
                alert(ev)
                this.$store.commit('setUserPosition', null)
            })
        },

        async addParking(ev) {
            if (store.getters.userRole == 'municipality')
                this.$router.push({ path: '/map/parking', query: { c: ev.latlng.lat + ',' + ev.latlng.lng }})
        },

        async showParking(id) {
            this.$router.push('/map/parking/'+id)
        },

        findParkings(bounds) {
            fireStore.collection('Parkings')
                .where('lat', '<=', bounds.getNorthEast().lat)
                .where('lat', '>=', bounds.getSouthWest().lat)
                .get()
                .then((querySnapshot) => {
                    console.log(querySnapshot.size, "new parkings were found")
                    querySnapshot.forEach((doc) => {
                        //this.markers.push(doc.data())
                        this.$store.commit("addParking", doc.data())
                    })
                })
                .catch((error) => {
                    console.log("Error getting documents: ", error);
                })
        },

        parseAreas(bounds, map) {
            let newBounds = bounds
            let result = turf.polygon(L.rectangle(bounds).toGeoJSON().geometry.coordinates)
            result.geometry.coordinates[0][4] = result.geometry.coordinates[0][4].slice() // Necessary because the last coord array is a reference and not a value
            
            if (this.searchBounds) {
                const initial = result.geometry.coordinates[0].slice()

                for (let i = this.searchSpots.length; i--;) {
                    if (bounds.intersects(this.searchSpots[i])) {
                        const element = turf.polygon(L.rectangle(this.searchSpots[i]).toGeoJSON().geometry.coordinates)
                        result = turf.difference(result, element) 
                        if (!result) break
                        else if (this.arrayEquals(initial, result.geometry.coordinates[0].slice())) this.searchSpots.splice(i, 1)
                    }
                }
                
                const oldBounds = this.searchBounds.getBounds()
                newBounds = [ [Math.max(bounds.getNorthEast().lat, oldBounds.getNorthEast().lat),
                               Math.max(bounds.getNorthEast().lng, oldBounds.getNorthEast().lng)], 
                              [Math.min(bounds.getSouthWest().lat, oldBounds.getSouthWest().lat),
                               Math.min(bounds.getSouthWest().lng, oldBounds.getSouthWest().lng)] ]
                map.removeLayer(this.searchBounds)
            }  
            
            this.searchBounds = L.rectangle(newBounds, {color: 'red', fillOpacity: 0})
            //this.searchBounds.addTo(map)

            if (result) {
                this.searchSpots.push(bounds)

                for (let pol of result.geometry.coordinates) {
                    if (result.geometry.type == 'MultiPolygon') pol = pol[0]
                    const area = new L.polygon(this.reverseCoords(pol), {
                        fillOpacity: 0,
                        color: 'blue'
                    })
                    //area.addTo(map)
                    if (result.geometry.type == 'Polygon' && result.geometry.coordinates.length > 1) return
                    else if (this.isRectangle(pol)) this.findParkings(area.getBounds())
                }
            }
        },

        reverseCoords(polygon) {
            for (const coords of polygon) coords.reverse()
            return polygon
        },

        isRectangle(polygon) {
            this.reverseCoords(polygon)
            if (polygon.length-1 != 4) return false
            if (polygon[0][0] == polygon[3][0] && polygon[1][0] == polygon[2][0] && polygon[0][1] == polygon[1][1] && polygon[2][1] == polygon[3][1]) return true
            if (polygon[0][0] == polygon[1][0] && polygon[2][0] == polygon[3][0] && polygon[0][1] == polygon[3][1] && polygon[1][1] == polygon[2][1]) return true
            return false
        },

        arrayEquals(a, b) {
            return Array.isArray(a) &&
                Array.isArray(b) &&
                a.length === b.length &&
                a.every((val, index) => (val[0] === b[index][0] && val[1] === b[index][1]));
        },
        
        hideMarkers(zoom) {
            zoom > 9.5 ? (this.markerVisible = true) : (this.markerVisible = false)
        }
    },
    computed: {
        center() { return this.$store.getters.center },
        active() { return this.$store.getters.active },
        parkings() { return this.$store.getters.parkings },
        waypoints() { return this.$store.getters.waypoints }
    },
    watch: {
        center() { return },
        active() { return },
        parkings() { return },
        waypoints() { return }
    }
}
</script>

<style lang="scss" scoped>
#map {
    width: 100%;
    height: 100vh;
    z-index: 1;
}

.autoSearch {
    &:hover {
        cursor: pointer;
    }

    &.active {
        color: white;
        background-color: rgb(41, 134, 255);
    }
}


@media (max-width: 400px) {
    #map {
        height: calc(100vh - 60px);
    }
}
</style>