<template>
  <div class="container width-70">
    <div class="header">
      <h1 class="welcome"><b> Hi {{ name }}! </b></h1>
      <h5> What you wanna do today? </h5>
    </div>

    <div class="sections">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
export default {
  name: 'dashboard',
  data () {
    return {
      name: this.$store.getters.user.name
    }
  }
}
</script>

<style lang="scss" scoped>
.header {
  padding-left: 15px;
  text-align: left;

  .welcome {
    font-size: 3em;
  }
}

.sections {
  margin-top: auto;
}
</style>