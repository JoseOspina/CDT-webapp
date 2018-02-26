<template lang="html">
  <div class="">
    <h1>App View</h1>
    <button v-if="!isAuthenticated" @click="login()" class="w3-button w3-blue w3-round-large">login</button>
    <button v-else @click="logout()" class="w3-button w3-blue w3-round-large">logout</button>
  </div>
</template>

<script>
export default {
  computed: {
    isAuthenticated () {
      return this.$store.getters.isAuthenticated
    }
  },
  methods: {
    login () {
      this.$store.dispatch('login')
    },
    logout () {
      this.$store.dispatch('logout')
    }
  }
}
</script>

<style lang="css">
</style>
