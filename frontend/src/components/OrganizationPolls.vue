<template lang="html">
  <div class="">
    Organization Polls
    <button @click="newPoll()" type="button" name="button">new poll</button>
    <div class="w3-row-padding">
      <div v-for="poll in this.polls" :key="poll.id" class="w3-col s4 w3-margin-bottom">
        <app-poll-card :poll="poll"></app-poll-card>
      </div>
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

<style lang="css">
</style>
