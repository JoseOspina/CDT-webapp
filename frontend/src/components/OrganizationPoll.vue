<template lang="html">
  <div class="w3-container content-container">

    <router-link :to="{ name: 'OrganizationPolls'}"
      class="w3-row disp-block w3-margin-bottom w3-hide-large">
      <app-back-button class=""></app-back-button>
    </router-link>

    <div class="central-column-container dark-1">

      <router-link :to="{ name: 'OrganizationPolls'}"
        class="w3-hide-medium w3-hide-small back-button-in-column">
        <app-back-button class=""></app-back-button>
      </router-link>

      <component :is="currentComponent" :pollId="pollId">
      </component>

    </div>
  </div>
</template>

<script>
import PollDetails from '@/components/OrganizationPollDetails'

export default {
  data () {
    return {
      currentComponent: PollDetails
    }
  },

  computed: {
    pollId () {
      return this.$route.params.pollId
    }
  },

  methods: {
    editPoll () {
      this.$router.push({name: 'EditPoll'})
    }
  }
}
</script>

<style scoped>

</style>
