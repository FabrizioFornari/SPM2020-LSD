<template>
    <l-map id="map" ref="map"
        :zoom="zoom"
        :center="center"
        :minZoom="minZoom"
        :maxZoom="maxZoom"
        :options="mapOptions"
        @ready="handleEvents"
        @update:zoom="updateZoom"
        @click="addParking">
        <l-tile-layer :url="url" :attribution="attribution"/>
        <l-search :options="searchOptions" />
        <l-routing v-if="waypoints" :waypoints="waypoints" :icon="activeIcon" :key="waypoints" />
        <l-locate-control :options="{icon: 'fa fa-crosshairs'}" />
        <l-control class="leaflet-bar leaflet-control" :position="'topleft'"> 
            <a class="autoSearch fa fa-repeat" v-bind:class="{ active: autoSearch }" @click="autoSearch = !autoSearch" /> 
        </l-control>
        <l-marker v-if="active" :lat-lng="active" :icon="activeIcon" :zIndexOffset="10" />
        <l-group v-if="newZoom > 13.5">
            <l-marker v-for="parking in parkings"
                :lat-lng="[parking.coords.y, parking.coords.x]"
                :key="'parking'+parking.id"
                @click="$router.push('/map/parking/'+parking.id)">
                <l-icon>
                    <p-icon :price="parking.price"></p-icon>
                </l-icon>
            </l-marker>
        </l-group>
        <l-group v-if="newZoom > 16">
            <l-marker v-for="slot in slots"
                :lat-lng="[slot.coords.y, slot.coords.x]"
                :key="'slot'+slot.id"
                @click="$router.push('/map/slot/'+slot.id)">
                <l-icon :icon-anchor="[16, 36]">
                    <s-icon :type="slot.type"></s-icon>
                </l-icon>
            </l-marker>
            <l-marker v-for="(slot, index) in newSlots"
                :lat-lng="[slot.coords.y, slot.coords.x]"
                :key="'newslot'+index">
                <l-icon :icon-anchor="[16, 36]">
                    <s-icon :type="slot.type"></s-icon>
                </l-icon>
            </l-marker>
        </l-group>
    </l-map>
</template>

<script>
import { LMap, LTileLayer, LMarker, LLayerGroup, LControl, LIcon } from 'vue2-leaflet'
import Vue2LeafletLocatecontrol from 'vue2-leaflet-locatecontrol/Vue2LeafletLocatecontrol'
import { OpenStreetMapProvider } from 'leaflet-geosearch'
import Geosearch from '@/components/Geosearch'
import Routing from '@/components/Routing'
import ParkingIcon from './ParkingIcon.vue'
import SlotIcon from './SlotIcon.vue'
import L, { latLng } from 'leaflet'
import 'leaflet/dist/leaflet.css'
import * as turf from '@turf/turf'
import store from '@/store'
import apiParking from '../api/parking'

export default {
    name: 'spark-map',  
    data() {
        return {
            map: null,
            autoSearch: false,
            locSearch: false,
            searchBounds: null,
            searchSpots: [],

            zoom: 14,
            newZoom: 14,
            minZoom: 3,
            maxZoom: 18,
            url: 'https://tiles.stadiamaps.com/tiles/alidade_smooth/{z}/{x}/{y}{r}.png',
            // 'https://{s}.basemaps.cartocdn.com/light_all/{z}/{x}/{y}{r}.png'
            attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
            mapOptions: {
                zoomDelta: 0.5,
                zoomSnap: 0.5
            },

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
        'l-icon': LIcon,
        'l-group': LLayerGroup,
        'l-control': LControl,
        'l-locate-control': Vue2LeafletLocatecontrol,
        'l-search': Geosearch,
        'l-routing': Routing,
        'p-icon': ParkingIcon,
        's-icon': SlotIcon
    },
    methods: {
        handleEvents(map) {
            this.map = map
            //this.parseAreas(map.getBounds(), map)
            this.findParkings(map.getBounds())
            map.on('geosearch/showlocation', (ev) => {
                this.locSearch = true
                //this.parseAreas(L.latLngBounds(ev.location.bounds), map)
                //this.findParkings(ev.location.bounds)
            })
            map.on('moveend', (ev) => {
                if (this.locSearch) {
                    if (ev.target.getZoom() <= 13) map.setZoom(13.5)
                    this.findParkings(map.getBounds())
                    this.locSearch = false
                }
                else if (this.autoSearch && map.getZoom() > 13) this.findParkings(map.getBounds()) //this.parseAreas(map.getBounds(), map)
            })
            map.on('locationfound', (ev) => {
                store.commit('setUserPosition', ev.latlng)
            })
            map.on('locationerror', (ev) => {
                alert(ev)
                store.commit('setUserPosition', null)
            })
        },

        addParking(ev) {
            if (store.getters.userRole != 'MUNICIPALITY') return
            if (store.getters.insertSlots) this.$store.dispatch('addNewSlot', ev.latlng)
            else this.$router.push({ path: '/map/parking', query: { c: ev.latlng.lat + ',' + ev.latlng.lng }})
        },

        findParkings(bounds) {
            const ne = [bounds.getEast(), bounds.getNorth()]
            const sw = [bounds.getWest(), bounds.getSouth()]
            apiParking.getParkingsByArea(ne.join(','), sw.join(',')).then(response => {
                store.dispatch("fetchParkings", response.data)
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
        
        updateZoom(zoom) {
            this.newZoom = zoom
        }
    },
    computed: {
        center() { return store.getters.center },
        active() { return store.getters.active },
        waypoints() { return store.getters.waypoints },
        parkings() { return store.getters.parkings },
        slots() { return store.getters.slots },
        newSlots() { return store.getters.newSlots }
    },
    watch: {
        center() { return },
        active(newAct, oldAct) { if (newAct) this.map.flyTo(newAct, this.map.getZoom() > 17 ? this.map.getZoom() : 17) },
        waypoints() { return },
        parkings() { return },
        slots() { return },
        newSlots() { return }
    }
}
</script>

<style lang="scss">
#map {
    height: 100vh;
    flex: 1;
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

.leaflet-marker-icon {
    z-index: 1 !important;

    &:hover {
        z-index: 1000 !important;
    }
}


@media (max-width: 480px) {
    #map {
        height: calc(100vh - 60px);
    }
}
</style>