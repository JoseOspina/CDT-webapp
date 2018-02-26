<template>
  <div>
  </div>
</template>

<script>
import loggedUser from '@/mixins/loggedUser'

export default {
  name: 'RootView',

  mixins: [loggedUser],

  created () {
    if (this.isAuthenticated) {
      console.log('redirecting to app view')
      this.$router.replace({ name: 'AppView' })
    } else {
      console.log('redirecting to landing page')
      this.$router.replace({ name: 'LandingPage' })
    }
  }
}
</script>

<style scoped>
</style>
