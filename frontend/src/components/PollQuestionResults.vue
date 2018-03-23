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
        <div @click="prev()" class="w3-left arrow-div cursor-pointer app-color-2-co">
          <i v-if="hasPrevious" class="fa fa-chevron-left" aria-hidden="true"></i>
        </div>
        <div class="w3-left text-div">
          {{ textAnswers[ix] }}
        </div>
        <div @click="next()" class="w3-left arrow-div cursor-pointer app-color-2-co">
          <i v-if="hasNext" class="fa fa-chevron-right" aria-hidden="true"></i>
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

  computed: {
    hasNext () {
      return this.ix < this.textAnswers.length - 1
    },
    hasPrevious () {
      return this.ix > 0
    }
  },

  methods: {
    next () {
      console.log(this.hasNext)
      this.ix = this.hasNext ? this.ix + 1 : this.ix
    },
    prev () {
      this.ix = this.hasPrevious ? this.ix - 1 : this.ix
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
  width: 15px;
  min-height: 1px;
}

.arrow-div:hover {
  color: #339470;
}

.text-div {
  width: calc(100% - 30px);
}

.stats-cell {
  padding: 3px 12px;
}

</style>
