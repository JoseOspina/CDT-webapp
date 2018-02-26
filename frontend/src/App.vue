<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App',

  methods: {
  },

  created () {
    console.log('app loaded')
    this.$store.commit('updateAuthenticated')

    this.$store.state.user.auth.authNotifier.on('authChange',
      (authState) => {
        console.log('auth changed')
        this.$store.commit('setAuthenticated', authState.authenticated)
        this.$router.push({name: 'RootView'})
      })
  }
}
</script>

<style>
@import 'css/w3.css';
</style>
