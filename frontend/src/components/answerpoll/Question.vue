<template lang="html">
  <div class="">
    <div class="w3-col m6 question-text">
      {{ question.text }}
    </div>
    <div class="w3-col m6">
      <app-rating-selector v-if="question.type === 'RATE_1_5'" @input="questionAnswered($event)"></app-rating-selector>
      <textarea v-if="question.type === 'TEXT'" class="w3-input w3-border" @input="questionTextAnswered($event)" name="name" rows="3"></textarea>
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
    },
    questionTextAnswered (event) {
      this.$store.commit('addAnswer', { questionId: this.question.id, text: event.target.value })
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
