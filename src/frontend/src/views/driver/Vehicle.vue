<template>
    <div class="vehicle">
        <div class="details">
            <input type="text" v-model="vehicle.name">
            <input type="text" v-model="vehicle.plate">
            <input type="text" v-model="vehicle.cod">
            <input type="text" v-model="vehicle.type">
        </div>
        
        <div v-if="!id" class="actions">
            <button class="action save" @click="addVehicle()">Add</button>
            <router-link class="action cancel" to="/dashboard/garage">Cancel</router-link>
        </div>
        <div v-else class="actions">
            <button class="action save" @click="updateVehicle()">Save</button>
            <router-link class="action cancel" to="/dashboard/garage">Cancel</router-link>
        </div>
    </div>
</template>

<script>
import { fireAuth, fireStore } from '@/firebase'

export default {
    data() {
        return {
            vehicle: {}
        }
    },
    props: {
        id: {
            type: String,
            default: null
        }
    },
    async created() {
        if (this.id) this.vehicle = await this.$store.dispatch("fetchVehicle", this.id)
    },
    methods: {
        async addVehicle() {
            const token = await fireAuth.currentUser.getIdToken()
        },
        async updateVehicle() {
            const vehicles = {[this.vehicle.id]: {id: this.vehicle.id, name: this.vehicle.name, type: this.vehicle.type}}
            const batch = fireStore.batch()
            batch.update(fireStore.collection('Vehicles').doc(this.vehicle.id), this.vehicle)
            batch.set(fireStore.collection('Users').doc(this.vehicle.owner), { vehicles }, { merge: true })
            batch.commit().then(() => {
                alert("UPDATED")
                this.$store.commit("addVehicle", this.vehicle)
                this.$router.push('/dashboard/garage')
            })
        }
    }
}
</script>

<style lang="scss" scoped>
.vehicle {
    flex: 1 1 auto;
    flex-flow: column;
    justify-content: center;
    display: flex;

    .details {
        flex: 1 1 auto;
        flex-flow: column;
        justify-content: center;
        display: flex;

        input {
            margin: 15px;
            border: 1px solid #00000011;
            border-radius: 8px;
            padding: 10px 20px;

            &:hover,
            &:focus {
                border: 1px solid #00000033;
            }
        }
    }

    .actions {
        flex-flow: row;
        align-items: center;
        display: flex;

        .action {
            margin: 15px;
            border-radius: 8px;
            padding: 10px 20px;
            flex: 1 1 100px;
            justify-content: center;
            display: flex;
        }
        
        .save {
            color: rgb(47, 160, 37);
            border: 1px solid rgba(23, 129, 13, 0.418);
            background-color: rgba(47, 236, 0, 0.255);
        }

        .cancel {
            color: white;
            border: 1px solid #ec000041;
            background-color: #ff1c1c54;
        }
    }
}
</style>