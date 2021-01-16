<template>
    <form class="vehicle" @submit.prevent="!id ? addVehicle() : updateVehicle()">
        <div class="details">
            <label class="label">
                <input type="text" id="nameVehicle" class="input" v-model="vehicle.name" required>
                <span>Name</span>
            </label>
            <label class="label">
                <input type="text" class="input" v-model="vehicle.plate" required>
                <span>Plate</span>
            </label>
            <label class="label">
                <input type="text" class="input" v-model="vehicle.cod" required>
                <span>Code</span>
            </label>
            <label class="label"> 
                <select class="input" v-model="vehicle.type" required>
                    <option value="other">Other</option>
                    <option value="car">Car</option>
                </select>
                <span>Type</span>
            </label>
        </div>
        
        <div class="actions">
            <button v-if="!id" class="action save" id="saveVehicle" type="submit">Add</button>
            <button v-else class="action save" type="submit">Save</button>
            <router-link class="action cancel" to="/dashboard/garage">Cancel</router-link>
        </div>
    </form>
</template>

<script>
import { fireStore } from '@/firebase'
import api from '@/api/driver'

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
            await api.addVehicle(this.vehicle)
            this.$router.push('/dashboard/garage')
        },
        async updateVehicle() {
            const vehicles = {[this.vehicle.id]: {id: this.vehicle.id, name: this.vehicle.name, type: this.vehicle.type}}
            const batch = fireStore.batch()
            batch.update(fireStore.collection('Vehicles').doc(this.vehicle.id), this.vehicle)
            batch.set(fireStore.collection('Users').doc(this.vehicle.owner), { vehicles }, { merge: true })
            batch.commit().then(() => {
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
        flex-flow: row wrap;
        align-content: center;
        display: flex;
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