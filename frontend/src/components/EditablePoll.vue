<template lang="html">
  <div class="">
    <!-- Title -->
    <div class="w3-row w3-margin-top">
      <app-poll-text-input
        v-model="poll.title"
        :placeholder="$t('POLL-TITLE')">
      </app-poll-text-input>
      <app-error-panel :show="showErrors && poll.title === ''"
        :message="$t('POLL-TITLE-CANNOT-BE-EMPTY')">
      </app-error-panel>
    </div>

    <!-- Description -->
    <div class="w3-row w3-margin-top">
      <app-poll-text-input
        v-model="poll.description"
        :placeholder="$t('POLL-DESCRIPTION')"
        :useTextArea="true">
      </app-poll-text-input>
    </div>

    <!-- Axes -->
    <div class="axes-container">
      <div v-for="(axis, ixA) in poll.axes" :key="axis.id" class="axis-container w3-margin-top">
        <div v-if="poll.axes.length > 1" class="w3-row">
          <app-move-btns
            @move-up="moveUpAxis(axis)"
            @remove="removeAxis(axis)"
            @move-down="moveDownAxis(axis)">
          </app-move-btns>
        </div>
        <div class="w3-row">
          <div class="w3-col l2 s12 axis-number-col w3-center w3-margin-bottom">
            <h4>{{ $t('DEGREE')}} {{ ixA + 1 }}</h4>
            <div @click="toggleInclude(axis)" class="w3-center cursor-pointer" :class="{'light-co': axis.includeInPlot}">
              <div class="">
                <i class="fa fa-circle rating-dot" aria-hidden="true"></i>
              </div>
              <div class="noselect">
                <span v-if="axis.includeInPlot">{{ $t('INCLUDED-IN-PLOT')}}</span>
                <span v-else>{{ $t('REMOVED-FROM-PLOT')}}</span>
              </div>
            </div>
          </div>

          <div class="w3-col l10 s12">
            <!-- Axis -->
            <div class="w3-row w3-margin-bottom">
              <app-poll-text-input
                v-model="axis.title"
                :placeholder="$t('DEGREE-TITLE')">
              </app-poll-text-input>
              <app-error-panel :show="showErrors && axis.title === ''"
                :message="$t('AXIS-CANNOT-BE-EMPTY')">
              </app-error-panel>
            </div>

            <!-- Questions -->
            <div v-for="(question, ixq) in axis.questions" :key="question.id"
              class="question-container">

              <app-poll-question-input
                v-model="axis.questions[ixq]"
                :showMoveBtns="axis.questions.length > 1"
                @move-up="moveUpQuestion(axis, question)"
                @move-down="moveDownQuestion(axis, question)"
                @remove="removeQuestion(axis, question)">
              </app-poll-question-input>

              <app-error-panel :show="showErrors && question.text === ''"
                :message="$t('QUESTION-TEXT-NOT-EMPTY')">
              </app-error-panel>
            </div>

            <div class="w3-row add-question-row">
              <app-plus-button @click="newQuestion(axis)" class="w3-right"></app-plus-button>
              <div class="w3-right label">{{ $t('ADD-QUESTION') }}</div>
            </div>
            <app-error-panel :show="!axisWeightsOk(axis.id)"
              :message="$t('WEIGHT-SUM-100')">
            </app-error-panel>
            <app-error-panel :show="!axisHasARate(axis.id)"
              :message="$t('AT-LEAST-ONE-QUESTION')">
            </app-error-panel>

          </div>
        </div>

      </div>
      <div class="w3-row w3-margin-top new-axis-row">
        <div class="w3-left label">{{ $t('ADD-DEGREE') }}</div>
        <app-plus-button @click="newAxis()" class="w3-left"></app-plus-button>
      </div>
    </div>
  </div>
</template>

<script>
import PollTextInput from '@/components/newpoll/PollTextInput'
import PollQuestionInput from '@/components/newpoll/PollQuestionInput'
import MoveBtns from '@/components/newpoll/MoveBtns'
import { getEmptyQuestion, getEmptyAxis } from '@/support/newPollEmptyElements'
import { getElIx } from '@/support/general'
import editablePollChecks from '@/mixins/editablePollChecks.js'

export default {

  mixins: [ editablePollChecks ],

  components: {
    'app-poll-text-input': PollTextInput,
    'app-poll-question-input': PollQuestionInput,
    'app-move-btns': MoveBtns
  },

  props: {
    value: {
      type: Object,
      default: null
    },
    pollG: {
      type: Object,
      default: null
    },
    fromTemplate: {
      type: Boolean,
      defaul: false
    },
    showErrors: {
      type: Boolean,
      defaul: false
    }
  },

  data () {
    return {
      customTitle: false,
      customDescription: false
    }
  },

  computed: {
    poll () {
      return this.value
    }
  },

  methods: {
    newAxis () {
      this.poll.axes.push(getEmptyAxis())
    },
    toggleInclude (axis) {
      var axisIx = getElIx(axis.id, this.poll.axes)
      if (axisIx === -1) {
        return
      }
      this.$set(this.poll.axes[axisIx], 'includeInPlot', !this.poll.axes[axisIx].includeInPlot)
    },
    moveUpAxis (axis) {
      var axisIx = getElIx(axis.id, this.poll.axes)
      if (axisIx === -1) {
        return
      }
      if (axisIx !== -1 && axisIx > 0) {
        var axisBefore = this.poll.axes[axisIx - 1]
        /* set before */
        this.poll.axes.splice(axisIx - 1, 1, this.poll.axes[axisIx])
        /* set at */
        this.poll.axes.splice(axisIx, 1, axisBefore)
      }
    },
    moveDownAxis (axis) {
      var axisIx = getElIx(axis.id, this.poll.axes)
      if (axisIx === -1) {
        return
      }
      if (axisIx !== -1 && axisIx < (this.poll.axes.length - 1)) {
        var axisAfter = this.poll.axes[axisIx + 1]
        /* set before */
        this.poll.axes.splice(axisIx + 1, 1, this.poll.axes[axisIx])
        /* set at */
        this.poll.axes.splice(axisIx, 1, axisAfter)
      }
    },
    removeAxis (axis) {
      var ix = getElIx(axis.id, this.poll.axes)
      if (ix !== -1) {
        this.poll.axes.splice(ix, 1)
      }
    },
    newQuestion (axis) {
      var ix = getElIx(axis.id, this.poll.axes)
      if (ix !== -1) {
        this.poll.axes[ix].custom = true
        this.poll.axes[ix].questions.push(getEmptyQuestion())
      }
    },
    removeQuestion (axis, question) {
      var axisIx = getElIx(axis.id, this.poll.axes)
      if (axisIx === -1) {
        return
      }
      var questionIx = getElIx(question.id, this.poll.axes[axisIx].questions)

      if (this.poll.axes[axisIx].questions.length > 1 && questionIx !== -1) {
        this.poll.axes[axisIx].questions.splice(questionIx, 1)
      }
    },
    moveUpQuestion (axis, question) {
      var axisIx = getElIx(axis.id, this.poll.axes)
      if (axisIx === -1) {
        return
      }
      var questionIx = getElIx(question.id, this.poll.axes[axisIx].questions)

      if (questionIx !== -1 && questionIx > 0) {
        var questionBefore = axis.questions[questionIx - 1]
        /* set before */
        axis.questions.splice(questionIx - 1, 1, axis.questions[questionIx])
        /* set at */
        axis.questions.splice(questionIx, 1, questionBefore)
      }
    },
    moveDownQuestion (axis, question) {
      var axisIx = getElIx(axis.id, this.poll.axes)
      if (axisIx === -1) {
        return
      }

      var questionIx = getElIx(question.id, this.poll.axes[axisIx].questions)

      if (questionIx !== -1 && questionIx < (axis.questions.length - 1)) {
        var questionAfter = this.poll.axes[axisIx].questions[questionIx + 1]
        /* set after */
        axis.questions.splice(questionIx + 1, 1, axis.questions[questionIx])
        /* set at */
        axis.questions.splice(questionIx, 1, questionAfter)
      }
    }
  }

}
</script>

<style scoped>

.add-question-row .label {
  padding-top: 6px;
  margin-right: 20px;
}

.new-axis-row .label {
  padding-top: 6px;
  margin-right: 20px;
}

</style>
