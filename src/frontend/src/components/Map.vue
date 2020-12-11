<template>
    <div class="map">
        <l-map id="homeMap" ref="homeMap"
            :zoom="zoom"
            :minZoom="minZoom"
            :maxZoom="maxZoom"
            :center="center"
            :options="mapOptions"
            @ready="handleEvents"
            @update:zoom="hideMarkers">
            <l-tile-layer :url="url" :attribution="attribution"/>
            <l-search :options="searchOptions" />
            <l-routing :waypoints="waypoints" :icon="myMarkerIcon" :key="waypoints" />
            <l-locate-control :options="{icon: 'fa fa-crosshairs'}" />
            <l-control class="leaflet-bar leaflet-control" :position="'topleft'"> 
                <a class="autoSearch fa fa-repeat" v-bind:class="{ active: autoSearch }" @click="autoSearch = !autoSearch" /> 
            </l-control>
            <l-group v-if="markerVisible">
                <l-marker v-for="(marker, index) in markers"  
                    :lat-lng="[marker.coords.latitude, marker.coords.longitude]" 
                    :icon="myMarkerIcon"
                    :key="index">
                    <l-popup>
                        <b>{{ marker.name }}</b> <br>
                        <button @click="findRoute(marker.coords.latitude, marker.coords.longitude)">Indicazioni</button>
                    </l-popup>
                </l-marker>
            </l-group>
        </l-map>
    </div>
</template>

<script>
import { LMap, LTileLayer, LMarker, LPopup, LLayerGroup, LControl } from 'vue2-leaflet'
import Vue2LeafletLocatecontrol from 'vue2-leaflet-locatecontrol/Vue2LeafletLocatecontrol'
import { OpenStreetMapProvider } from 'leaflet-geosearch'
import Geosearch from '@/components/Geosearch'
import Routing from '@/components/Routing'
import L, { latLng } from 'leaflet'
import 'leaflet/dist/leaflet.css'
import { fireStore, geoPoint } from '@/firebase'
import * as turf from '@turf/turf'

export default {
    name: 'spark-map',  
    data() {
        return {
            autoSearch: true,
            locSearch: false,
            searchBounds: null,
            searchSpots: [],

            zoom: 11,
            minZoom: 3,
            maxZoom: 17,
            center: latLng(45.449534, 9.179764),
            url: 'https://tiles.stadiamaps.com/tiles/alidade_smooth/{z}/{x}/{y}{r}.png',
            // 'https://{s}.basemaps.cartocdn.com/light_all/{z}/{x}/{y}{r}.png'
            attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
            mapOptions: {
                zoomSnap: 0.5
            },

            userPosition: null,
            markerVisible: true,
            markers: [],
            myMarkerIcon: L.icon({
                popupAnchor: [0, -40],
                iconAnchor: [12, 40],
                iconUrl: require('leaflet/dist/images/marker-icon.png'),
                shadowUrl: require('leaflet/dist/images/marker-shadow.png')
            }),
            waypoints: [],

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
        'l-group': LLayerGroup,
        'l-control': LControl,
        'l-locate-control': Vue2LeafletLocatecontrol,
        'l-search': Geosearch,
        'l-routing': Routing
    },
    methods: {
        handleEvents(map) {
            map.on('geosearch/showlocation', (ev) => {
                this.locSearch = true
                this.parseAreas(L.latLngBounds(ev.location.bounds), map)
            })
            map.on('moveend', () => {
                if (this.locSearch) this.locSearch = false
                else if (this.autoSearch && map.getZoom() >= 10) this.parseAreas(map.getBounds(), map)
            })
            map.on('locationfound', (ev) => {
                this.userPosition = ev.latlng
            })
            map.on('locationerror', (ev) => {
                alert(ev)
                this.userPosition = null
            })
        },

        findParkings(bounds) {
            fireStore.collection('Parkings')
                .where('coords', '<=', new geoPoint(bounds.getNorthEast().lat, bounds.getNorthEast().lng))
                .where('coords', '>=', new geoPoint(bounds.getSouthWest().lat, bounds.getSouthWest().lng))
                .get()
                .then((querySnapshot) => {
                    console.log(querySnapshot.size, "new parkings were found")
                    querySnapshot.forEach((doc) => {
                        this.markers.push(doc.data())
                    })
                })
                .catch((error) => {
                    console.log("Error getting documents: ", error);
                })
        },

        findRoute(lat, lng) {
            if (this.userPosition) this.waypoints = [this.userPosition, latLng(lat, lng)]
            else alert("You have to set a starting point or you position to start routing")
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
            this.searchBounds.addTo(map)

            if (result) {
                this.searchSpots.push(bounds)

                for (let pol of result.geometry.coordinates) {
                    if (result.geometry.type == 'MultiPolygon') pol = pol[0]
                    const area = new L.polygon(this.reverseCoords(pol), {
                        fillOpacity: 0,
                        color: 'blue'
                    }).addTo(map)
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
            zoom >= 9 ? (this.markerVisible = true) : (this.markerVisible = false)
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

.autoSearch {
    &:hover {
        cursor: pointer;
    }

    &.active {
        color: white;
        background-color: rgb(41, 134, 255);
    }
}
</style>