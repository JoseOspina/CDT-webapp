<template lang="html">
  <div v-if="poll" class="">
    Poll details
    <h3>{{ poll.title }}</h3>
    <p>{{ poll.description }}</p>
  </div>
</template>

<script>
export default {
  data () {
    return {
      poll: null
    }
  },
  computed: {
    pollId () {
      return this.$route.params.pollId
    }
  },

  methods: {
    update () {
      this.axios.get('/1/poll/' + this.pollId).then((response) => {
        if (response.data.result === 'success') {
          this.poll = response.data.data
        }
      })
    }
  },

  created () {
    this.update()
  }
}
</script>

<style lang="css">
</style>
