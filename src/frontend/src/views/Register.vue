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
          <div v-if="error" class="alert alert-danger">{{ error }}</div>

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
              <label class="label" v-if="type == 'policeman'">
                <input type="text" class="input" value required v-model="form.municipalityId">
                <span>Municipality</span>
              </label>
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

            <div>
              <label class="label">
                <input type="email" class="input" value required v-model="form.email">
                <span>Email</span>
              </label>
              <label class="label">
                <input type="password" minlength="8" class="input" required v-model="form.password">
                <span>Password</span>
              </label>
              <label class="label">
                <input type="password" minlength="8" class="input" required v-model="form.passwordConf">
                <span>Confirm Password</span>
              </label>
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
import { login } from '@/firebase'
import auth from '@/api/auth'
import store from '@/store'

export default {
  data() {
    return {
      type: "driver",
      form: {
        name: "",
        surname: "",
        municipalityId: "",
        city: "",
        province: "",
        region: "",
        email: "",
        password: "",
        passwordConf: ""
      },
      error: null
    }
  },
  methods: {
    async register(e) {
      e.preventDefault()
      this.error = null
      if (this.form.password != this.form.passwordConf) {
        this.error = "Password and confim password must be the same"
        return
      }
      
      const user = {}
      user.password = this.form.password
      user.user = this.form
      
      await auth.registerUser(user, this.type)
      await login(this.form.email, this.form.password)
      store.dispatch("fetchRole", this.type)
      this.$router.push(this.$route.query.redirect || '/map')
    }
  }
}
</script>