<template>
  <div class="container width-40">
    <div class="row justify-content-center">
      <div class="col-md-12">
        <div class="card-body">
          <h1><b>Login</b></h1>
          <div v-if="error" class="alert alert-danger">{{ error }}</div>
          <form @submit.prevent="signIn">
            <div>
              <label class="label">
                <input type="email" id="emailLogin" class="input" value required autofocus v-model="form.email">
                <span>Email</span>
              </label>
              <label class="label">
                <input type="password" id="passwordLogin" class="input" required v-model="form.password"/>
                <span>Password</span>
              </label>
            </div>

            <div class="form-group row mb-0">
              <div class="col-md-12">
                <button type="submit" id="buttonLogin" class="choice btn btn-primary" :disabled="loading">Login</button>
                <br><br>
                <p><router-link to="/register">Aren't you registered?</router-link></p>
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

export default {
  data() {
    return {
      form: {
        email: "",
        password: ""
      },
      loading: false,
      error: null
    }
  },
  methods: {
    async signIn() {
      this.loading = true
      this.error = null
      this.error = await login(this.form.email, this.form.password)
      if (!this.error) this.$router.push(this.$route.query.redirect || '/map')
      this.loading = false
    }
  }
}
</script>