<template lang="html">
  <div v-if="poll" class="">
    <div class="w3-row">
      {{ poll.title }}
    </div>
    <div class="w3-row">
      {{ poll.description }}
    </div>
    <div class="">
      <div v-for="axis in poll.axes" :key="axis.id" class="">
        <div class="w3-row">
          {{ axis.title }}
        </div>
        <div class="w3-row">
          {{ axis.description }}
        </div>

        <div v-for="question in axis.questions" :key="question.id" class="w3-row">
          <div class="w3-col m8">
            {{ question.text }}
          </div>
          <div class="w3-col m4">
            answer
          </div>
        </div>
      </div>
    </div>
    <div class="w3-row w3-center">
      <button type="button" name="button">Send</button>
    </div>
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

<style lang="css">
</style>
