<template lang="html">
  <div v-if="poll !== null && details !== null" class="">
    Poll details
    <h3>{{ poll.title }}</h3>
    <p>{{ poll.description }}</p>
    Number of answers: <b>{{ details.numberOfAnswers }}</b>
  </div>
</template>

<script>
export default {
  data () {
    return {
      poll: null,
      details: null
    }
  },
  computed: {
    pollId () {
      return this.$route.params.pollId
    }
  },

  methods: {
    update () {
      if (this.pollId) {
        if (this.pollId !== '') {
          this.axios.get('/1/poll/' + this.pollId).then((response) => {
            if (response.data.result === 'success') {
              this.poll = response.data.data
              return this.axios.get('/1/poll/' + this.poll.id + '/details')
            }
          }).then((response) => {
            if (response.data.result === 'success') {
              this.details = response.data.data
            }
          })
        }
      }
    }
  },

  created () {
    this.update()
  }
}
</script>

<style lang="css">
</style>
