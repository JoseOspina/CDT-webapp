<template lang="html">
  <div class="">
    <div class="question-text w3-col m8 s12">
      <vue-markdown class="marked-text" :source="question.text"></vue-markdown>
    </div>
    <div v-if="question.type === 'RATE_1_5'" class="w3-col m4 s12">
      <app-rating-selector class="margin-0-auto" :valueInit="questionAnswer()" @input="questionAnswered($event)"></app-rating-selector>
    </div>
    <div v-if="question.type === 'TEXT'" class="w3-col m12 s12">
      <textarea ref="textarea"
        class="w3-margin-top rounded-input dark-2 app-color-2-br answer-textarea"
        :value="questionAnswer()"
        @input="questionTextAnswered($event)" name="name" rows="3">
      </textarea>
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
      this.$store.commit('addAnswer', { questionId: this.question.id, rate: rate, type: 'RATE_1_5' })
    },
    questionAnswer () {
      return this.$store.getters.getAnswer(this.question.id)
    },
    questionTextAnswered (event) {
      this.checkHeight(event)
      this.$store.commit('addAnswer', { questionId: this.question.id, text: event.target.value, type: 'TEXT' })
    },
    checkHeight (event) {
      if (this.$refs.textarea) {
        if (event.target.value !== '') {
          /* resize text area */
          if (this.$refs.textarea.scrollHeight > 90) {
            this.$refs.textarea.style.height = (this.$refs.textarea.scrollHeight) + 'px'
          }
        } else {
          this.$refs.textarea.style.height = '90px'
        }
      }
    }
  }
}
</script>

<style scoped>

.answer-textarea {
  resize: vertical;
  min-height: 50px;
}

</style>
