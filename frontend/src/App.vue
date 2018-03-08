<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
import { i18n } from '@/main.js'

export default {
  name: 'App',

  methods: {
  },

  created () {
    console.log('app loaded')
    this.$store.commit('updateAuthenticated')
    this.$store.dispatch('updateProfile')

    this.$store.state.user.auth.authNotifier.on('authChange',
      (authState) => {
        console.log('auth changed')
        this.$store.commit('setAuthenticated', authState.authenticated)
        this.$router.push({name: 'RootView'})
      })

    /* set locale */
    i18n.locale = 'de'
  }
}
</script>

<style>

@import 'css/w3.css';
@import 'css/modals.css';
@import 'ext/radar-chart.css';
@import 'css/common.css';

</style>
