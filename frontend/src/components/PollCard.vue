<template lang="html">
  <router-link :to="{ name: 'OrganizationPoll', params: {pollId: poll.id} }"
    class="w3-card-2 app-card w3-round-large poll-card w3-display-container">
    <h3><b>{{ poll.title }}</b></h3>
    <p>{{ poll.description }}</p>
    <div v-if="poll.isTemplate" class="w3-display-topright template-tag app-color-1-bg w3-padding">
      IS {{ poll.isPublicTemplate ? 'PUBLIC' : '' }} TEMPLATE
    </div>
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

.template-tag {
  border-top-right-radius: 16px;
}

</style>
