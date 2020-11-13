<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-md-10">
        <div class="card-body">
          <div v-if="error" class="alert alert-danger">{{error}}</div>
          <form @submit.prevent="signIn">
            <div class="form-group row">
              <label for="email" class="col-md-4 col-form-label text-md-right">Email</label>

              <div class="col-md-6">
                <input id="email" type="email" class="form-control" name="email" value required autofocus
                  v-model="form.email"/>
              </div>
            </div>

            <div class="form-group row">
              <label for="password" class="col-md-4 col-form-label text-md-right">Password</label>

              <div class="col-md-6">
                <input id="password" type="password" class="form-control" name="password" required
                  v-model="form.password"/>
              </div>
            </div>

            <div class="form-group row mb-0">
              <div class="col-md-12">
                <button type="submit" class="btn btn-primary">Login</button>
                <br><br>
                <p><router-link to="/register">Non sei registrato?</router-link></p>
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
      }
    };
  },
  methods: {
    async signIn() {
      await login(this.form.email, this.form.password)
      this.$router.push(this.$route.query.redirect || '/')
      this.form.email = ""
      this.form.password = ""
    }
  }
};
</script>