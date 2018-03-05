<template lang="html">
  <div class="w3-container">
    <h3>Poll Configuration</h3>
    <div class="w3-row w3-margin-top">
      <select v-model="config.audience" class="w3-input">
        <option value="ANYONE_WITH_LINK">Anyone with the link</option>
        <option value="ANY_MEMBER">Any member in the organization</option>
        <option value="SPECIFIC_MEMBERS">Select specific members</option>
      </select>
    </div>
    <hr>
    <div class="w3-row w3-center">
      <button @click="create()" type="button" name="button">create</button>
    </div>
    <app-error-panel v-if="errorCreatingPoll" :message="errorCreatingPollMsg"></app-error-panel>
  </div>
</template>

<script>
import { getDefaultConfig } from '@/support/newPollEmptyElements'

export default {
  data () {
    return {
      poll: null,
      config: null,
      errorCreatingPoll: false,
      errorCreatingPollMsg: ''
    }
  },
  computed: {
    orgId () {
      return this.$route.params.orgId
    }
  },

  methods: {
    create () {
      var ok = true
      if (this.config.audience === '') {
        this.showErrors = true
      }

      if (ok) {
        /* save in global state */
        this.poll.config = this.config
        this.$store.commit('setNewPoll', this.poll)
        this.axios.post('/1/organization/' + this.orgId + '/poll', this.poll).then((response) => {
          if (response.data.result === 'success') {
            this.$router.push({name: 'OrganizationPolls'})
          } else {
            this.errorCreatingPoll = true
            this.errorCreatingPollMsg = response.data.result
          }
        }).catch((error) => {
          this.errorCreatingPoll = true
          this.errorCreatingPollMsg = error.message
        })
      }
    }
  },

  created () {
    /* works with a local copy of the poll state */
    this.poll = JSON.parse(JSON.stringify(this.$store.state.newpoll.poll))
    this.config = getDefaultConfig()
  }
}
</script>

<style lang="css">
</style>
