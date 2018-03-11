<template lang="html">
  <div v-if="poll" class="content-container this-container">
    <app-answer-poll :poll="poll"></app-answer-poll>
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

.this-container {
  background: url('./../../assets/background-1.png') center;
  background-color: #3F3E3E;
}

.content-container {
  padding-top: 0px;
}

.answer-poll-container {
  color: black;
}

</style>
