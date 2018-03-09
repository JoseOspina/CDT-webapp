<template lang="html">
  <router-view></router-view>
</template>

<script>
import { getEmptyPoll } from '@/support/newPollEmptyElements'
export default {
  computed: {
    orgId () {
      return this.$route.params.orgId
    }
  },

  created () {
    this.axios.get('/1/organization/' + this.orgId + '/hasTemplates').then((response) => {
      if (response.data.result === 'success') {
        var hasTemplates = response.data.data
        if (hasTemplates) {
          console.log('templates available')
          this.$router.replace({name: 'NewPollTemplate'})
        } else {
          console.log('templates not available')
          if (!this.$store.state.newpoll.editing) {
            this.$store.commit('setNewPoll', getEmptyPoll())
          }
          this.$router.replace({name: 'NewPollQuestions'})
        }
      } else {
        console.log(response.data.message)
        this.errorFlag = true
        this.errorMsg = response.data.message
      }
    })
  }
}
</script>

<style>

.new-poll-container {
  min-height: calc(100vh - 65px);
  background: url('./../../assets/background-2.png') center left / cover no-repeat;
  background-color: #2F2F2F;
  color: white;
  padding-top: 42px;
}

</style>
