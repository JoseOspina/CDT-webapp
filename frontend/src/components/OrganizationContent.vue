<template lang="html">
  <div class="">
    <h1>{{ organizationName }}</h1>
    <router-link :to="{ name: 'OrganizationPolls', params: {} }">polls</router-link>
    <router-view :orgId="id"></router-view>
  </div>
</template>

<script>
import loggedUser from '@/mixins/loggedUser'

export default {
  mixins: [ loggedUser ],

  props: {
    id: {
      type: String
    }
  },

  data () {
    return {
      errorFlag: false,
      errorMsg: ''
    }
  },

  computed: {
    organizationName () {
      return this.$store.getters.organizationName
    }
  },

  watch: {
    id () {
      this.update()
    }
  },

  methods: {
    update () {
      if (this.id !== '') {
        this.$store.dispatch('updateOrganization', this.id)
      }
    }
  },

  created () {
    this.update()
  }

}
</script>

<style lang="css">
</style>
