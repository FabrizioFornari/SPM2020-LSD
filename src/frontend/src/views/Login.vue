<template>
  <div class="container width-40">
    <div class="row justify-content-center">
      <div class="col-md-12">
        <div class="card-body">
          <div v-if="error" class="alert alert-danger">{{ error }}</div>
          <h1><b>Login</b></h1>
          <form @submit.prevent="signIn">
            <label class="label">
              <input type="email" id="emailLogin" class="input" value required autofocus v-model="form.email">
              <span>Email</span>
            </label>

            <label class="label">
              <input type="password" id="passwordLogin" class="input" required v-model="form.password"/>
              <span>Password</span>
            </label>

            <div class="form-group row mb-0">
              <div class="col-md-12">
                <button type="submit" id="buttonLogin" class="btn btn-primary">Login</button>
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
import { login } from '@/firebase';

export default {
  data() {
    return {
      form: {
        email: "",
        password: ""
      },
      error: null
    }
  },
  methods: {
    async signIn() {
      this.error = null
      this.error = await login(this.form.email, this.form.password)
      if (!this.error) this.$router.push(this.$route.query.redirect || '/map')
    }
  }
}
</script>