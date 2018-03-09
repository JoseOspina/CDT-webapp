<template lang="html">
  <router-link :to="{ name: 'OrganizationPoll', params: {pollId: poll.id} }"
    class="w3-card-2 app-card w3-round-large poll-card">
    <h3><b>{{ poll.title }}</b></h3>
    <p>{{ poll.description }}</p>
  </router-link>
</template>

<script>
export default {
  props: {
    poll: {
      type: Object
    }
  }
}
</script>

<style scoped>

</style>
