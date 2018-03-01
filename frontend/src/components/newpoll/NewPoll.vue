<template lang="html">
  <router-view :orgId="orgId"></router-view>
</template>

<script>
import { getEmptyPoll } from '@/support/newPollEmptyElements'
export default {
  props: {
    orgId: {
      type: String
    }
  },

  created () {
    this.axios.get('/1/organization/' + this.orgId + '/hasTemplates').then((response) => {
      if (response.data.result === 'success') {
        var hasTemplates = response.data.data
        if (hasTemplates) {
          console.log('templates available')
          this.$router.push({name: 'NewPollTemplate'})
        } else {
          console.log('templates not available')
          if (!this.$store.state.newpoll.editing) {
            this.$store.commit('setNewPoll', getEmptyPoll())
          }
          this.$router.push({name: 'NewPollQuestions'})
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

<style lang="css">
</style>
