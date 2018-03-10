<template lang="html">
  <div class="w3-container content-container this-container">
    <div class="w3-row">
      <div class="w3-left w3-margin-right">
        <h4><b>Organization Polls</b></h4>
      </div>
      <div class="w3-left new-poll-button cursor-pointer app-color-white-1">
        <i @click="newPoll()" class="fa fa-plus-circle" aria-hidden="true"></i>
      </div>
    </div>

    <div class="w3-row-padding organization-polls-row">
      <div v-for="poll in this.polls" :key="poll.id" class="w3-col l4 m6 s12 w3-margin-bottom">
        <app-poll-card :poll="poll"></app-poll-card>
      </div>
    </div>

    <div v-if="this.polls.length === 0" class="w3-row w3-center w3-margin-top">
      <i>no polls have been created</i>
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

.this-container {
  background: url('./../assets/background-1.png') center left;
  background-color: #3F3E3E;
}

.new-poll-button {
  padding: 0px;
  font-size: 32px;
  line-height: 32px;
}

.organization-polls-row {
  padding: 22px 0px;
}

</style>
