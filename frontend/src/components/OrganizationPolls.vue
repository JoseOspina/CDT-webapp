<template lang="html">
  <div class="">
    Organization Polls
    <button @click="newPoll()" type="button" name="button">new poll</button>
  </div>
</template>

<script>
export default {
  methods: {
    newPoll () {
      this.$router.push({name: 'NewPoll'})
    }
  }
}
</script>

<style lang="css">
</style>
