<template lang="html">
  <div class="">
    <div class="w3-row">
      <h3>{{ axis.title }}</h3>
    </div>
    <div class="w3-row">
      <p>{{ axis.description }}</p>
    </div>
    <app-answer-question
      v-for="question in axis.questions" :key="question.id"
      :question="question" class="w3-row question-container">
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

<style scoped>

.question-container {
  margin-bottom: 20px;
}

</style>
