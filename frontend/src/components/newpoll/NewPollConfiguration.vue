<template lang="html">
  <div class="w3-container">
    <h3>Poll Configuration</h3>
    <div class="w3-row w3-margin-top">
      <select v-model="poll.config.audience" class="w3-input">
        <option value="ANYONE_WITH_LINK">Anyone with the link</option>
        <option value="ANY_MEMBER">Any member in the organization</option>
        <option value="SPECIFIC_MEMBERS">Select specific members</option>
      </select>
    </div>
    <hr>
    <div class="w3-row w3-center">
      <button @click="create()" type="button" name="button">create</button>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    orgId: {
      type: String
    }
  },

  data () {
    return {
    }
  },

  computed: {
  },

  methods: {
    create () {
      var ok = true
      if (this.poll.config.audience === '') {
        this.showErrors = true
      }

      if (ok) {
        /* save in global state */
        this.$store.commit('setNewPoll', this.poll)
        this.axios.post('/1/organization/' + this.orgId + '/poll', this.poll)
      }
    }
  },

  created () {
    /* works with a local copy of the poll state */
    this.poll = JSON.parse(JSON.stringify(this.$store.state.newpoll.poll))
  }
}
</script>

<style lang="css">
</style>
