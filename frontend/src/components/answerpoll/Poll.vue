<template lang="html">
  <div class="">
    <div class="w3-row">
      {{ poll.title }}
    </div>
    <div class="w3-row">
      {{ poll.description }}
    </div>
    <div class="">
      <app-answer-axis v-for="axis in poll.axes" :key="axis.id" :axis="axis" class="">
      </app-answer-axis>
    </div>
    <div class="w3-row w3-center">
      <button type="button" name="button">Send</button>
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
  }
}
</script>

<style lang="css">
</style>
