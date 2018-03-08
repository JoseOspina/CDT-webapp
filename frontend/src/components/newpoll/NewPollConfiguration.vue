<template lang="html">
  <div class="w3-container new-poll-container">
    <div class="column-container">

      <div class="back-button cursor-pointer">
        <div class="back-button-content">
          <i class="fa fa-chevron-left" aria-hidden="true"></i>
        </div>
      </div>

      <div class="w3-row">
        <app-new-poll-header>
          New Poll - <i>Configuration</i>
        </app-new-poll-header>
      </div>

      <div class="w3-row w3-margin-top">
        <app-config-select
          v-model="config.audience"
          :elements="audienceElements">
        </app-config-select>
      </div>
      <hr>
      <div class="w3-row w3-center">
        <button @click="create()" type="button" name="button">create</button>
      </div>
      <app-error-panel v-if="errorCreatingPoll" :message="errorCreatingPollMsg"></app-error-panel>
    </div>
  </div>
</template>

<script>
import NewPollHeader from '@/components/styled/NewPollHeader'
import PollConfigSelect from '@/components/styled/PollConfigSelect'
import { getDefaultConfig } from '@/support/newPollEmptyElements'

export default {
  components: {
    'app-new-poll-header': NewPollHeader,
    'app-config-select': PollConfigSelect
  },
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
    },
    audienceElements () {
      return [
        { value: 'ANYONE_WITH_LINK', text: 'Anyone with the link' },
        { value: 'ANY_MEMBER', text: 'Any member in the organization' },
        { value: 'SPECIFIC_MEMBERS', text: 'Select specific members' }
      ]
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

<style scoped>

</style>
