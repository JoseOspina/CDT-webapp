<template lang="html">
  <router-link :to="{ name: 'OrganizationPoll', params: {pollId: poll.id} }"
    class="w3-card-2 app-card w3-round-large poll-card">
    <div v-if="poll.isTemplate" class="template-tag w3-padding app-color-1-bg">
      IS {{ poll.isPublicTemplate ? 'PUBLIC' : '' }} TEMPLATE
    </div>
    <div class="w3-row w3-margin">
      <h3><b>{{ poll.title }}</b></h3>
      <p>{{ poll.description }}</p>
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
  border-radius: 16px;
}

</style>
