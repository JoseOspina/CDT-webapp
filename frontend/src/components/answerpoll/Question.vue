<template lang="html">
  <div class="">
    <div class="w3-col m6 question-text">
      {{ question.text }}
    </div>
    <div class="w3-col m6">
      <app-rating-selector @input="questionAnswered($event)"></app-rating-selector>
    </div>
  </div>
</template>

<script>
import RatingSelector from '@/components/answerpoll/RatingSelector'

export default {
  components: {
    'app-rating-selector': RatingSelector
  },
  props: {
    question: {
      type: Object
    }
  },
  methods: {
    questionAnswered (rate) {
      this.$store.commit('addAnswer', { questionId: this.question.id, rate: rate })
    }
  }
}
</script>

<style scoped>

.question-text {
  text-align: right;
  padding-right: 20px;
}

</style>
