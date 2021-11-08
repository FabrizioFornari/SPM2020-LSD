<template>
    <form class="vehicle" @submit.prevent="!id ? addVehicle() : deleteVehicle()">
        <div class="details">
            <label class="label"> 
                <select class="input" v-model="vehicle.type" required>
                    <option value="BICYCLE">Bycicle</option>
                    <option value="CAR">Car</option>
                    <option value="CARAVAN">Caravan</option>
                    <option value="MOTORCYCLE">Motorcycle</option>
                </select>
                <span>Type</span>
            </label>
            <label class="label">
                <input type="text" id="nameVehicle" class="input" v-model="vehicle.name" required>
                <span>Name</span>
            </label>
            <label class="label" v-if="vehicle.type != 'BICYCLE'">
                <input type="text" class="input" v-model="vehicle.plate" required>
                <span>Plate</span>
            </label>
            <label class="label" v-if="vehicle.type != 'BICYCLE'">
                <input type="text" class="input" v-model="vehicle.cod" required>
                <span>Code</span>
            </label>
        </div>
        
        <div class="actions">
            <button v-if="!id" class="action save" type="submit">Add</button>
            <button v-else class="action save" id="deleteVehicle" type="submit">Delete</button>
            <router-link class="action cancel" to="/dashboard/garage">Cancel</router-link>
        </div>
    </form>
</template>

<script>
import api from '@/api/vehicle'

export default {
    data() {
        return {
            vehicle: {
                type: "CAR"
            }
        }
    },
    props: {
        id: {
            type: String,
            default: null
        }
    },
    created() {
        if (this.id) {
            const v = this.$store.getters.vehicles[this.id]
            if (v) this.vehicle = v
            else this.$router.push("/dashboard/garage")
        }
    },
    methods: {
        async addVehicle() {
            this.vehicle.owner = this.$store.getters.userUid
            await api.addVehicle(this.vehicle, this.vehicle.type.toLowerCase()).then(response => {
                this.$store.dispatch("fetchVehicle", response.data)
                this.$router.push("/dashboard/garage")
            })
        },
        async deleteVehicle() {
            await api.deleteVehicle(this.id).then(response => {
                this.$store.dispatch("deleteVehicle", response.data)
                this.$router.push("/dashboard/garage")
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