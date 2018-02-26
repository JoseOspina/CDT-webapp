<template lang="html">
  <div class="">
    <h1>login callback</h1>
  </div>
</template>

<script>
export default {
  computed: {
  },
  created () {
    this.$store.dispatch('handleAuthentication')
  }
}
</script>

<style lang="css">
</style>
