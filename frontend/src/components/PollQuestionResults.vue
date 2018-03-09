<template lang="html">
  <div class="question-results-container w3-row app-color-2-br">
    <div class="w3-col m6 question-text">
      {{ question.text }}?
    </div>
    <div class="w3-col m6 stats-cell w3-center">
      <span v-if="question.type === 'RATE_1_5'" class="">
        min: {{ stats[0].toFixed(1) }} - mean: {{ stats[1].toFixed(1) }} - max: {{ stats[2].toFixed(1) }}
      </span>
      <div v-if="question.type === 'TEXT'" class="">
        <p v-for="(answer, ix) in textAnswers" :key="ix">{{ answer }}</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    question: {
      type: Object
    },
    stats: {
      type: Array,
      default: () => { return [] }
    },
    textAnswers: {
      type: Array,
      default: () => { return [] }
    }
  }
}
</script>

<style scoped>

.question-results-container {
  border-style: solid;
  border-top-style: none;
  border-width: 2px;
}

.question-text {
  padding: 3px 12px;
}

.stats-cell {
  padding: 3px 12px;
}

</style>
