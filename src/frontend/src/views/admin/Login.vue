<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-md-10">
        <div class="card-body">
          <div v-if="error" class="alert alert-danger">{{error}}</div>
          <form @submit.prevent="signIn">
            <div class="form-group row">
              <div class="col-md-12">
                <input type="text" class="form-control" placeholder="Username" value required autofocus v-model="user"/>
              </div>
            </div>

            <div class="form-group row">
              <div class="col-md-12">
                <input type="password" class="form-control" placeholder="Password" required v-model="password"/>
              </div>
            </div>

            <div class="form-group row mb-0">
              <div class="col-md-12">
                <button type="submit" class="btn btn-primary">Login</button>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import auth from '@/store/auth';

export default {
  name: 'adminLogin',
  data () {
    return {
      user: '',
      password: '',
      error: false,
      errors: []
    }
  },
  methods: {
    signIn() {
      auth.dispatch('fetchAdmin', { user: this.user, password: this.password })
        .then(() => this.$router.push('/admin/dashboard'))
        .catch(error => {
          this.errors.push(error);
          this.error = true
        })
    }
  }
}
</script>