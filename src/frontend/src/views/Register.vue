<template>
  <div class="container width-40">
    <div class="row justify-content-center">
      <div class="col-md-12">
        <div class="card-body">
          Registrati come
          <select id="mySelect" v-model="type">
            <option value="driver">Driver</option>
            <option value="policeman">Policeman</option>
            <option value="municipality">Municipality</option>
          </select>
          <br><br>

          <form @submit="register">
            <div v-if="type != 'municipality'">
              <div class="form-group row">
                <label for="nameUser" class="col-md-4 col-form-label text-md-right">Name</label>
                <div class="col-md-8">
                  <input type="text" class="form-control" value required autofocus v-model="form.name"/>
                </div>
              </div>
              <div class="form-group row">
                <label for="surnameUser" class="col-md-4 col-form-label text-md-right">Surname</label>
                <div class="col-md-8">
                  <input type="text" class="form-control" value required v-model="form.surname"/>
                </div>
              </div>
              <div v-if="type == 'policeman'">
                <div class="form-group row">
                  <label for="nameUser" class="col-md-4 col-form-label text-md-right">District</label>
                  <div class="col-md-8">
                    <input type="text" class="form-control" value required v-model="form.district"/>
                  </div>
                </div>
              </div>
            </div>

            <div v-else>
              <div class="form-group row">
                <label for="nameMunicipality" class="col-md-4 col-form-label text-md-right">City</label>
                <div class="col-md-8">
                  <input type="text" class="form-control" value required autofocus v-model="form.city"/>
                </div>
              </div>
              <div class="form-group row">
                <label for="districtMunicipality" class="col-md-4 col-form-label text-md-right">Province</label>
                <div class="col-md-8">
                  <input type="text" class="form-control" value required v-model="form.province"/>
                </div>
              </div>
              <div class="form-group row">
                <label for="districtMunicipality" class="col-md-4 col-form-label text-md-right">Region</label>
                <div class="col-md-8">
                  <input type="text" class="form-control" value required v-model="form.region"/>
                </div>
              </div>
            </div>

            <div class="form-group row">
              <label for="emailUser" class="col-md-4 col-form-label text-md-right">Email</label>
              <div class="col-md-8">
                <input type="email" class="form-control" value required v-model="form.email"/>
              </div>
            </div>
            <div class="form-group row">
              <label for="passwordUser" class="col-md-4 col-form-label text-md-right">Password</label>
              <div class="col-md-8">
                <input type="password" class="form-control" required v-model="form.password"/>
              </div>
            </div>
            <div class="form-group row">
              <label for="passwordUserConfirm" class="col-md-4 col-form-label text-md-right">Confirm password</label>
              <div class="col-md-8">
                <input type="password" class="form-control" required v-model="form.passwordConf"/>
              </div>
            </div>

            <div class="form-group row mb-0">
              <div class="col-md-12">
                <button type="submit" class="btn btn-primary">Register now!</button>
                <br><br>
                <p><router-link to="/login">Are you already signed in?</router-link></p>
              </div>
            </div>
          </form>          
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { signin } from '@/firebase';
import auth from '@/api/auth'
import store from '@/store'

export default {
  data() {
    return {
      type: "driver",
      form: {
        name: "",
        surname: "",
        district: "",
        city: "",
        province: "",
        region: "",
        email: "",
        password: "",
        passwordConf: ""
      }
    }
  },
  methods: {
    async register(e) {
      e.preventDefault()
      if (this.form.password == this.form.passwordConf) {
        await signin(this.form.email, this.form.password)
        
        const user = {}
        if (this.type == "municipality") {
          user.city = this.form.city
          user.province = this.form.province
          user.region = this.form.region
        } else {
          user.name = this.form.name
          user.surname = this.form.surname
          if (this.type == "policeman") user.district = this.form.district
        }
        user.email = this.form.email
        user.id = store.getters.user.uid
        
        auth.registerUser(user, this.type)
        store.dispatch("fetchRole", this.type)
        this.$router.push(this.$route.query.redirect || '/')
      }
    }
  }
}
</script>