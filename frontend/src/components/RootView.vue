<template>
  <div class="hello">
    <button v-if="!isAuthenticated" @click="login()" class="w3-button w3-blue w3-round-large">login</button>
    <button v-else @click="logout()" class="w3-button w3-blue w3-round-large">logout</button>
  </div>
</template>

<script>
export default {
  name: 'RootView',
  data () {
    return {
    }
  },
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

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
