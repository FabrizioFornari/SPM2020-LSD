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

<style lang="scss">
.header {
  padding-left: 15px;
  text-align: left;

  .welcome {
    font-size: 3em;
  }
}

.sections {    
  flex: 1 1 auto;
  flex-flow: column;
  display: flex;
}

.menu {
  flex-flow: column;
  flex: 1 1 auto;
  display: flex;

  .choices {
    flex-flow: row wrap;
    flex: 1 1 auto;
    align-content: center;
    display: flex;

    .choice {
      flex: 1 1 300px;
    }
  }

  .choice {
    margin: 15px;
    padding: 15px;
    color: black;
    border: 1px solid #00000011;
    border-radius: 8px;
    flex-flow: column;
    justify-content: center;
    display: flex;
    transition: 0.3s;

    &.action {
      padding: 10px;
    }

    &:hover {
      box-shadow: 0 5px 10px #00000011;
      cursor: pointer;
    }

    p {
      margin: 0;
      font-size: 0.9em;
    }
  }
}
</style>