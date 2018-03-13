<template lang="html">
  <div class="w3-container content-container">
    <div class="w3-row">
      <div class="w3-left content-title-div">
        <h4><b>{{ $t('ORG-POLLS') }}</b></h4>
      </div>
      <app-plus-button class="w3-left w3-margin-left" @click="newPoll()"></app-plus-button>
    </div>

    <div class="w3-row-padding organization-polls-row">
      <div v-for="poll in this.polls" :key="poll.id" class="app-card-container">
        <app-poll-card :poll="poll"></app-poll-card>
      </div>
    </div>

    <div v-if="this.polls.length === 0" class="w3-row w3-center w3-margin-top">
      <i>{{ $t('NO-POLLS-CREATED')}}</i>
    </div>
  </div>
</template>

<script>
import PollCard from '@/components/PollCard'

export default {
  components: {
    'app-poll-card': PollCard
  },

  data () {
    return {
      polls: [],
      errorGettingPolls: false
    }
  },

  computed: {
    orgId () {
      return this.$route.params.orgId
    }
  },

  watch: {
    '$route' () {
      this.update()
    }
  },

  methods: {
    update () {
      this.axios.get('/1/organization/' + this.orgId + '/polls').then((response) => {
        if (response.data.result === 'success') {
          this.polls = response.data.data
        } else {
          this.errorGettingPolls = true
        }
      })
    },
    newPoll () {
      this.$router.push({name: 'NewPoll'})
    }
  },

  mounted () {
    this.update()
  }
}
</script>

<style>

.new-poll-button {
  padding: 0px;
  font-size: 32px;
  line-height: 32px;
}

.organization-polls-row {
  padding-top: 10vh;
  padding-left: 10vw;
  padding-right: 10vw;
}

</style>
