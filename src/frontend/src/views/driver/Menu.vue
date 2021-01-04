<template>
    <div class="choices">
        <router-link class="choice" id="profileButton" to="/dashboard/profile">
            <h3> View Profile </h3> 
            <p> See your private account details and modify them </p>
        </router-link>
        <router-link class="choice" id="garageButton" to="/dashboard/garage"> 
            <h3> My Garage </h3> 
            <p> Watch all your vehicles, add more or modify existing ones </p>
        </router-link>
        <router-link class="choice" id="pocketButton" to="/dashboard/pocket"> 
            <h3> My Pocket </h3> 
            <p> Save your cards, charge the app credit and keep track of your tickets </p>
        </router-link>
    </div>
</template>

<script>
export default {
    name: 'driverMenu'
}
</script>