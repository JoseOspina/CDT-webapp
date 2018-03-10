<template lang="html">
  <div v-if="question" class="">
    <div v-if="restorable && !question.custom" class="w3-row">
      <div class="w3-col m6">
        <!-- <i @click="$emit('custom-question')" class="w3-left fa fa-pencil" aria-hidden="true"></i> -->
        <p>{{ question.text }}</p>
      </div>
      <div class="w3-col m6">
        {{ question.type }}
        {{ question.weight }}
      </div>
    </div>
    <div v-else class="">
      <div class="w3-row">
        <div class="btns-container app-color-2-co">
          <div class="w3-left cursor-pointer">
            <i class="fa fa-caret-up" aria-hidden="true"></i>
          </div>
          <div class="w3-left cursor-pointer">
            <i class="fa fa-times-circle" aria-hidden="true"></i>
          </div>
          <div class="w3-left cursor-pointer">
            <i class="fa fa-caret-down" aria-hidden="true"></i>
          </div>
        </div>
      </div>
      <div class="w3-row">
        <div class="w3-col m9 w3-margin-bottom">
          <app-poll-text-input
            v-model="question.text"
            placeholder="Question"
            :restorable="restorable"
            :useTextArea="true">
          </app-poll-text-input>
        </div>
        <div class="w3-col m3 select-col w3-container">
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
  </div>
</template>

<script>
import PollTextInput from '@/components/newpoll/PollTextInput'

export default {
  components: {
    'app-poll-text-input': PollTextInput
  },
  props: {
    value: {
      type: Object
    },
    showAsInput: {
      type: Boolean,
      default: true
    },
    restorable: {
      type: Boolean,
      default: false
    },
    placeholder: {
      type: String,
      default: ''
    },
    useTextArea: {
      type: Boolean,
      default: false
    }
  },

  computed: {
    question () {
      return this.value
    }
  }
}
</script>

<style scoped>

.btns-container {
  font-size: 18px;
  width: 90px;
  margin: 0 auto;
  overflow: auto;
}

.btns-container > div {
  width: 30px;
  text-align: center;
}

.btns-container > div:hover {
  color: #FFDE17;
}

.percentage {
  margin-left: 50px;
}

</style>
