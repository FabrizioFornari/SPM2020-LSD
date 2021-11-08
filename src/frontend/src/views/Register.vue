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

          <form @submit.prevent="register">
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
                <button type="submit" class="btn btn-primary" :disabled="loading">Register now!</button>
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
import auth from '@/api/auth'

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
      loading: false,
      error: null
    }
  },
  methods: {
    async register(e) {
      this.error = null
      if (this.form.password != this.form.passwordConf) 
        return this.error = "Password and confim password must be the same"
      
      this.loading = true
      const user = {}
      user.password = this.form.password
      user.user = this.form
      
      await auth.newUser(user, this.type).then((response) => {
        this.$router.push('/login')
      }).catch((error) => {
        this.error = error
      })
      this.loading = false
    }
  }
}
</script>