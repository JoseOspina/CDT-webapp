<template lang="html">
  <router-link :to="{ name: 'OrganizationPoll', params: {pollId: poll.id} }"
    class="w3-card-2 w3-round-large poll-card">
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

.poll-card {
  background-color: #DADADA;
  color: #231F20;
  display: block;
  padding: 6px 18px;
}

</style>
