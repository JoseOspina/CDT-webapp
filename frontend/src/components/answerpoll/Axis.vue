<template lang="html">
  <div class="">
    <div class="w3-row rounded-input axis-title app-color-1-br">
      <h3>{{ axis.title }}</h3>
    </div>
    <div class="w3-row questions-row">
      <div class="w3-row question-container app-color-2-br"
        v-for="(question, ixq) in axis.questions" :key="question.id">
        <div class="w3-row">
          <b>{{ ixq + 1}}.</b>
        </div>
        <app-answer-question :question="question">
        </app-answer-question>
      </div>
    </div>
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

.axis-title {
  border-radius: 16px;
}

.questions-row {
  width: 90%;
  margin: 0 auto;
  padding-top: 35px;
}

.question-container {
  padding-bottom: 20px;
  margin-bottom: 20px;
  min-height: 85px;
  border-bottom-style: solid;
  border-bottom-width: 2px;
}

</style>
