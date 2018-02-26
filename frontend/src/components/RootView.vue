<template>
  <div>
    <app-landing-page v-if="!isAuthenticated"></app-landing-page>
    <app-view v-else></app-view>
  </div>
</template>

<script>
import LandingPage from '@/components/LandingPage.vue'
import AppView from '@/components/AppView.vue'

export default {
  name: 'RootView',

  components: {
    'app-landing-page': LandingPage,
    'app-view': AppView
  },

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
  }
}
</script>

<style scoped>
</style>
