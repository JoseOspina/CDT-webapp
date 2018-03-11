<template lang="html">
  <div class="">
    <app-header></app-header>
    <router-view></router-view>
  </div>
</template>

<script>
import Header from '@/components/Header'
export default {
  components: {
    'app-header': Header
  },

  created () {
    if (!this.$store.state.user.authenticated) {
      this.$router.push({name: 'LandingPage'})
    }
  }
}
</script>

<style scoped>
</style>
