<template lang="html">
  <div class="w3-container">
    <div class="w3-row">
     <h1>{{ poll.title }}</h1>
    </div>
    <div class="w3-row">
      <p>{{ poll.description }}</p>
    </div>
    <div v-if="!answerSucessful" class="">
      <app-answer-axis v-for="axis in poll.axes" :key="axis.id" :axis="axis" class="">
      </app-answer-axis>
    </div>
    <div v-if="answerSucessful" class="">
      Answer succesful!
    </div>
    <hr>
    <div class="w3-row w3-center">
      <button @click="send()" type="button" name="button">Send</button>
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
      answerSucessful: false,
      answerError: false,
      answerErrorMsg: ''
    }
  },

  methods: {
    send () {
      this.axios.post('/1/poll/' + this.poll.id + '/answer', this.$store.state.answerpoll.answers).then((response) => {
        if (response.data.result === 'success') {
          this.answerSucessful = true
        } else {
          this.answerError = true
          this.answerErrorMsg = response.data.message
        }
      }).catch((error) => {
        this.answerError = true
        this.answerErrorMsg = error.message
      })
    }
  }
}
</script>

<style lang="css">
</style>
