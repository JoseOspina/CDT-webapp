<template lang="html">
  <div class="">
    <h1>login callback</h1>
  </div>
</template>

<script>
export default {
  computed: {
    isAuthenticated () {
      return this.$store.getters.isAuthenticated
    }
  },
  created () {
    this.$store.dispatch('handleAuthentication')
    this.$router.push({name: 'RootView'})
  }
}
</script>

<style lang="css">
</style>
