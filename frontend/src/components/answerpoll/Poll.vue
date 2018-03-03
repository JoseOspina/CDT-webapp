<template lang="html">
  <div class="w3-container">
    <div class="w3-row">
     <h1>{{ poll.title }}</h1>
    </div>
    <div class="w3-row">
      <p>{{ poll.description }}</p>
    </div>
    <div v-if="!alreadyFilled && !answerSucessful" class="">
      <app-answer-axis v-for="axis in poll.axes" :key="axis.id" :axis="axis" class="">
      </app-answer-axis>
      <hr>
      <div class="w3-row w3-center">
        <button @click="send()" type="button" name="button">Send</button>
      </div>
    </div>
    <div v-if="alreadyFilled && !answerSucessful" class="">
      Thanks for having filled the poll!
    </div>
    <div v-if="answerSucessful" class="">
      Answer succesful!
    </div>
  </div>
</template>

<script>
import Axis from '@/components/answerpoll/Axis'

export default {
  components: {
    'app-answer-axis': Axis
  },

  props: {
    poll: {
      type: Object
    }
  },

  data () {
    return {
      alreadyFilled: false,
      answerSucessful: false,
      answerError: false,
      answerErrorMsg: ''
    }
  },

  computed: {
    filledCookieId () {
      return 'POLL-' + this.poll.id
    },
    filledValue () {
      return 'FILLED'
    }
  },

  methods: {
    send () {
      this.axios.post('/1/poll/' + this.poll.id + '/answer', this.$store.state.answerpoll.answers).then((response) => {
        if (response.data.result === 'success') {
          this.answerSucessful = true
          this.$cookie.set(this.filledCookieId, this.filledValue, 365)
        } else {
          this.answerError = true
          this.answerErrorMsg = response.data.message
        }
      }).catch((error) => {
        this.answerError = true
        this.answerErrorMsg = error.message
      })
    }
  },

  created () {
    if (this.$cookie.get(this.filledCookieId) === this.filledValue) {
      this.alreadyFilled = true
    }
  }
}
</script>

<style lang="css">
</style>
