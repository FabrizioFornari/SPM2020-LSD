<template>
  <div class="container width-40">
    <div class="row justify-content-center">
      <div class="col-md-12">
        <div class="card-body">
          <div v-if="error" class="alert alert-danger">{{error}}</div>
          <form @submit.prevent="signIn">
            <div class="form-group row">
              <label for="email" class="col-md-4 col-form-label text-md-right">Email</label>
              <div class="col-md-6">
                <input type="email" id="emailLogin" class="form-control" value required autofocus v-model="form.email"/>
              </div>
            </div>

            <div class="form-group row">
              <label for="password" class="col-md-4 col-form-label text-md-right">Password</label>
              <div class="col-md-6">
                <input type="password" id="passwordLogin" class="form-control" required v-model="form.password"/>
              </div>
            </div>

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
      await login(this.form.email, this.form.password)
      this.$router.push(this.$route.query.redirect || '/')
      this.form.email = ""
      this.form.password = ""
    }
  }
}
</script>