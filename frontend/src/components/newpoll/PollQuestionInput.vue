<template lang="html">
  <div v-if="question" class="">
    <div class="w3-row">
      <div v-if="restorable" class="app-color-white-1 cursor-pointer restore-btn">
        <i v-if="!question.custom" @click="$emit('set-custom-question')" class="fa fa-pencil" aria-hidden="true"></i>
        <i v-else @click="$emit('custom-question-back')" class="fa fa-undo" aria-hidden="true"></i>
      </div>
      <app-move-btns
        @move-up="$emit('move-up')"
        @move-down="$emit('move-down')"
        @remove="$emit('remove')">
      </app-move-btns>
    </div>
    <div v-if="restorable && !question.custom" class="w3-row">
      <div class="w3-col m9">
        <div class="rounded-input app-color-2-br">
          {{ question.text }}
        </div>
      </div>
      <div class="w3-col m3 select-col">
        <div class="rounded-input app-color-2-br w3-margin-bottom">
          {{ questionTypeNice(question.type) }}
        </div>
        <div v-if="question.type === 'RATE_1_5'" class="rounded-input app-color-2-br">
          {{ question.weight }} %
        </div>
      </div>
    </div>
    <div v-else class="">
      <div class="w3-col m9 w3-margin-bottom">
        <app-poll-text-input
          v-model="question.text"
          placeholder="Question"
          :restorable="false"
          :useTextArea="true">
        </app-poll-text-input>
      </div>
      <div class="w3-col m3 select-col">
        <div class="w3-row w3-margin-bottom">
          <select v-model="question.type" class="app-select-input app-color-2-br dark-3">
            <option value="RATE_1_5">Rate</option>
            <option value="TEXT">Open text</option>
          </select>
        </div>
        <div class="w3-row">
          <div v-if="question.type === 'RATE_1_5'" class="w3-display-container">
            <input v-model.number="question.weight" class="rounded-input dark-3 app-color-2-br" type="number" step="10" name="" value="">
            <span class="percentage w3-display-left app-color-2-co">%</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import PollTextInput from '@/components/newpoll/PollTextInput'
import MoveBtns from '@/components/newpoll/MoveBtns'

export default {
  components: {
    'app-poll-text-input': PollTextInput,
    'app-move-btns': MoveBtns
  },
  props: {
    value: {
      type: Object
    },
    restorable: {
      type: Boolean,
      default: false
    },
    showMoveBtns: {
      type: Boolean,
      default: false
    }
  },

  computed: {
    question () {
      return this.value
    }
  },

  methods: {
    questionTypeNice (type) {
      switch (type) {
        case 'RATE_1_5':
          return 'rate'

        case 'TEXT':
          return 'open text'
      }
    }
  }
}
</script>

<style scoped>

.percentage {
  margin-left: 50px;
}

.restore-btn {
  position: absolute;
  margin-top: 6px;
}

.select-col {
  padding-left: 10px;
}

</style>
