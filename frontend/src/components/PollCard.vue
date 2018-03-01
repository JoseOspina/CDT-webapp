<template lang="html">
  <div class="w3-card-2  w3-white w3-round-large">
    <div class="">
      <h3>{{ poll.title }}</h3>
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
