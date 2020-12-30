<template>
    <div class="choices">
        <router-link class="choice" id="profileButton" to="/dashboard/profile">
            <h3> View Profile </h3> 
            <p> See the municipality account details and modify them </p>
        </router-link>
        <router-link class="choice" id="garageButton" to="/dashboard/parkings"> 
            <h3> All Parkings </h3> 
            <p> See all municipality's parking, their details and actual usage data </p>
        </router-link>
        <router-link class="choice" id="statisticsButton" to="/dashboard/statistics"> 
            <h3> Statistics </h3> 
            <p> Watch all statistics on parkings availability and usage through time </p>
        </router-link>
    </div>
</template>

<script>
export default {
    name: 'municipalityMenu'
}
</script>