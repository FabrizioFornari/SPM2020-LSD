<template>
  <div class="container width-40">
    <div class="row justify-content-center">
      <div class="col-md-12">
        <div class="card-body">
          <h1><b>Register</b></h1>as:          
          <select id="mySelect" v-model="type">
            <option value="driver">Driver</option>
            <option value="policeman">Policeman</option>
            <option value="municipality">Municipality</option>
          </select>
          <br><br>

          <form @submit="register">
            <div v-if="type != 'municipality'">
              <label class="label">
                <input type="text" class="input" value required autofocus v-model="form.name">
                <span>Name</span>
              </label>
              <label class="label">
                <input type="text" class="input" value required v-model="form.surname">
                <span>Surname</span>
              </label>
              <div v-if="type == 'policeman'">
                <label class="label">
                  <input type="text" class="input" value required v-model="form.municipalityId">
                  <span>Municipality</span>
                </label>
              </div>
            </div>

            <div v-else>
              <label class="label">
                <input type="text" class="input" value required autofocus v-model="form.city">
                <span>City</span>
              </label>
              <label class="label">
                <input type="text" class="input" value required v-model="form.province">
                <span>Province</span>
              </label>
              <label class="label">
                <input type="text" class="input" value required v-model="form.region">
                <span>Region</span>
              </label>
            </div>

            <label class="label">
              <input type="email" class="input" value required v-model="form.email">
              <span>Email</span>
            </label>
            <label class="label">
              <input type="password" class="input" required v-model="form.password">
              <span>Password</span>
            </label>
            <label class="label">
              <input type="password" class="input" required v-model="form.passwordConf">
              <span>Confirm Password</span>
            </label>

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
        const username = (this.form.name ? this.form.name : this.form.city)
        await signin(this.form.email, this.form.password, username)
        
        const user = {}
        if (this.type == "municipality") {
          user.city = this.form.city
          user.province = this.form.province
          user.region = this.form.region
        } else {
          user.name = this.form.name
          user.surname = this.form.surname
          if (this.type == "policeman") user.municipalityId = this.form.municipalityId
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