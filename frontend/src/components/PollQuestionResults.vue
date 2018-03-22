<template lang="html">
  <div class="question-results-container w3-row app-color-2-br">
    <div class="w3-col m6 question-text">
      {{ question.text }}
    </div>
    <div class="w3-col m6 stats-cell w3-center">
      <span v-if="question.type === 'RATE_1_5'" class="">
        min: {{ stats[0].toFixed(1) }} - mean: {{ stats[1].toFixed(1) }} - max: {{ stats[2].toFixed(1) }}
      </span>
      <div v-if="question.type === 'TEXT'" class="">
        <div @click="next()" class="w3-left arrow-div cursor-pointer">
          <i class="fa fa-chevron-left" aria-hidden="true"></i>
        </div>
        <div class="w3-left text-div">
          {{ textAnswers[ix] }}
        </div>
        <div @click="prev()" class="w3-left arrow-div cursor-pointer">
          <i class="fa fa-chevron-right" aria-hidden="true"></i>
        </div>

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
  },

  data () {
    return {
      ix: 0
    }
  },

  methods: {
    next () {
      this.ix = this.ix < this.textAnswers.length - 1 ? this.ix + 1 : this.textAnswers.length - 1
    },
    prev () {
      this.ix = this.ix > 0 ? this.ix - 1 : 0
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

.arrow-div {
  width: 20px;
}

.arrow-div:hover {
  color: #339470;
}

.text-div {
  width: calc(100% - 40px);
}

.stats-cell {
  padding: 3px 12px;
}

</style>
