<template lang="html">
  <div class="w3-card-2  w3-white w3-round-large">
    <div class="">
      <router-link :to="{ name: 'OrganizationPoll', params: {pollId: poll.id} }">
        <h3>{{ poll.title }}</h3>
      </router-link>
      <p>{{ poll.description }}</p>
    </div>
  </div>
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

<style lang="css">
</style>
