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

      <app-poll-details v-if="showDetails" :pollId="pollId" @edit="edit()"></app-poll-details>
      <app-poll-editor v-if="editing" :pollId="pollId" @cancel="details()" @edited="edited()"></app-poll-editor>

    </div>
  </div>
</template>

<script>
import PollDetails from '@/components/OrganizationPollDetails'
import PollEditor from '@/components/PollEditor'

export default {
  components: {
    'app-poll-details': PollDetails,
    'app-poll-editor': PollEditor
  },

  data () {
    return {
      showDetails: true,
      editing: false
    }
  },

  computed: {
    pollId () {
      return this.$route.params.pollId
    }
  },

  methods: {
    edit () {
      this.showDetails = false
      this.editing = true
    },
    details () {
      this.editing = false
      this.showDetails = true
    },
    edited () {
      this.editing = false
      this.showDetails = true
    }
  }
}
</script>

<style scoped>

</style>
