<template lang="html">
  <div class="w3-container content-container this-container">
    <div class="column-container">

      <router-link :to="{ name: 'NewPollQuestions'}"
        class="w3-row disp-block w3-margin-bottom w3-hide-large">
        <app-back-button class=""></app-back-button>
      </router-link>

      <div class="central-column-container dark-1">

        <router-link :to="{ name: 'NewPollQuestions'}"
          class="w3-hide-medium w3-hide-small back-button-in-column">
          <app-back-button class=""></app-back-button>
        </router-link>

        <div class="w3-row column-header">
          <app-column-header>
            New Poll - <i>Configuration</i>
          </app-column-header>
        </div>

        <div class="w3-row w3-margin-top">
          <select v-model="config.audience" class="app-select-input app-color-2-br dark-3">
            <option value="ANYONE_WITH_LINK">Anyone with the link</option>
          </select>
        </div>
        <div class="w3-row w3-center bottom-row">
          <app-button class="w3-right" @click="create()">create!</app-button>
        </div>
        <app-error-panel
          :show="errorCreatingPoll"
          :message="errorCreatingPollMsg">
        </app-error-panel>

      </div>

    </div>
  </div>
</template>

<script>
import PollConfigSelect from '@/components/styled/PollConfigSelect'
import { getDefaultConfig } from '@/support/newPollEmptyElements'

export default {
  components: {
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
