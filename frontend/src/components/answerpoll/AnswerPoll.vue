<template lang="html">
  <div v-if="poll" class="">
    <div class="w3-row header-container w3-center">
      <img class="logo" src="./../../assets/logo-white.png" alt="">
    </div>
    <div class="content-container">
      <app-answer-poll :poll="poll"></app-answer-poll>
    </div>

  </div>
</template>

<script>
import Poll from '@/components/answerpoll/Poll'

export default {
  components: {
    'app-answer-poll': Poll
  },

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
    getPoll () {
      this.axios.get('/1/poll/' + this.pollId).then((response) => {
        if (response.data.result === 'success') {
          this.poll = response.data.data
        }
      })
    }
  },

  created () {
    this.getPoll()
  }
}
</script>

<style scoped>

.content-container {
}

.answer-poll-container {
  color: black;
}

</style>
