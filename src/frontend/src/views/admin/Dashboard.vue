<template>
  <div class="container width-70">
    <div class="row justify-content-center">
      <div class="col-md-10">
        <h1><b-badge variant="success">{{ welcome }}</b-badge></h1>
        <b-table hover :head-variant="'light'" :items="municipalities" :fields="fields">
          <template #cell(approved)="data">
            <div v-if="!data.item.approved">
              <button @click="approve(data.index, data.item.id)">Approve</button>
              <button @click="reject(data.index, data.item.id)">Reject</button>
            </div>
            <div v-else>
              <button id="municipalityButtonAction" v-if="data.item.disabled" @click="enable(data.index, data.item.id)">Enable</button>
              <button id="municipalityButtonAction" v-else @click="disable(data.index, data.item.id)">Disable</button>
            </div>
          </template>
        </b-table>
        <button @click="signOut()">Logout</button>
      </div>
    </div>
  </div>
</template>

<script>
import auth from '@/store'
import api from '@/api/admin-municipalities'

export default {
  name: 'adminDash',
  data () {
    return {
      welcome: 'Admin dashboard',
      fields: ['city', 'province', 'region', 'approved'],
      municipalities: []
    }
  },
  mounted() {
    api.getMunicipalities().then(response => {
          if (response.status == 200) this.municipalities = response.data;
      }, error => {
          console.log("Error: " + error);
      })
  },
  methods: {
    approve(index, id) {
      api.approveMunicipality(id).then(response => {
            if (response.status == 200) {
                this.municipalities[index].approved = true;
            }
        }, error => {
            console.log("Error: " + error);
        })
    },
    reject(index, id) {
      api.rejectMunicipality(id).then(response => {
            if (response.status == 200)  {
                this.municipalities[index].approved = false;
            }
        }, error => {
            console.log("Error: " + error);
        })
    },
    enable(index, id) {
      api.enableMunicipality(id).then(response => {
            if (response.status == 200)  {
                this.municipalities[index].disabled = false;
            }
        }, error => {
            console.log("Error: " + error);
        })
    },
    disable(index, id) {
      api.disableMunicipality(id).then(response => {
            if (response.status == 200)  {
                this.municipalities[index].disabled = true;
            }
        }, error => {
            console.log("Error: " + error);
        })
    },
    signOut() {
      auth.dispatch('fetchAdmin', { user: null })
        .then(() => this.$router.push('/'))
    }
  }
}

</script>

<style lang="scss" scoped>
  .table {
    margin: 50px 0;
  }
</style>