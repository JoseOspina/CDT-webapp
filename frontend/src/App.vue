<template>
  <div id="app" class="">
    <router-view/>
  </div>
</template>

<script>
import Auth0Lock from 'auth0-lock'
import { i18n } from '@/main.js'

export default {
  name: 'App',

  methods: {
  },

  created () {
    var options = {
      auth: {
        responseType: 'token id_token',
        params: {
          connectionScopes: {
            connectionName: [ 'openid', 'user_metadata', 'app_metadata', 'picture' ]
          }
        }
      },
      theme: {
        logo: 'https://image.ibb.co/gjZuF7/logo_dark.png',
        primaryColor: '#009B9C'
      },
      languageDictionary: {
        title: 'welcome'
      }
    }

    var lock = new Auth0Lock(process.env.AUTH0_CLIENT_ID, process.env.AUTH0_DOMAIN, options)

    lock.on('authenticated', (authResult) => {
      console.log('authResult')
      console.log(authResult)
      localStorage.setItem('access_token', authResult.accessToken)
      localStorage.setItem('id_token', authResult.idToken)
      this.$store.commit('setAuthenticated', !!localStorage.getItem('id_token'))
      this.$store.dispatch('updateProfile')
      this.$router.push({ name: 'RootView' })
    })

    lock.on('authorization_error', (error) => {
      console.log('error authenticating')
      console.log(error)
      this.$store.dispatch('logoutUser')
    })

    /* check if user is authenticated */
    this.$store.commit('setLock', lock)
    this.$store.commit('setAuthenticated', !!localStorage.getItem('id_token'))
    this.$store.dispatch('updateProfile')
    /* set locale */
    i18n.locale = 'de'
  }
}
</script>

<style>

@import 'css/w3.css';
@import 'css/colors.css';
@import 'css/common.css';
@import 'ext/radar-chart.css';

</style>
