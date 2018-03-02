<template lang="html">
  <div class="">
    <div class="w3-row">
      {{ axis.title }}
    </div>
    <div class="w3-row">
      {{ axis.description }}
    </div>
    <app-answer-question v-for="question in axis.questions" :key="question.id" :question="question" class="w3-row">
    </app-answer-question>
  </div>
</template>

<script>
import Question from '@/components/answerpoll/Question'

export default {
  components: {
    'app-answer-question': Question
  },

  props: {
    axis: {
      type: Object
    }
  }
}
</script>

<style lang="css">
</style>
