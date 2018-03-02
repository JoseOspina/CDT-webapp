<template lang="html">
  <div class="w3-container">
    <div class="w3-row">
     <h1>{{ poll.title }}</h1>
    </div>
    <div class="w3-row">
      <p>{{ poll.description }}</p>
    </div>
    <div class="">
      <app-answer-axis v-for="axis in poll.axes" :key="axis.id" :axis="axis" class="">
      </app-answer-axis>
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

  methods: {
    send () {
      this.axios.post('/1/poll/' + poll.id + '/answer', this.$store.state.answerpoll.answers).then((response) => {

      })
    }
  }
}
</script>

<style lang="css">
</style>
