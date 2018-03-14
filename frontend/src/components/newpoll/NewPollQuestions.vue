<template lang="html">
  <div class="w3-container content-container this-container">

    <router-link :to="{ name: fromTemplate ? 'NewPollTemplate' : 'OrganizationPolls'}"
      class="w3-row disp-block w3-margin-bottom w3-hide-large">
      <app-back-button class=""></app-back-button>
    </router-link>

    <div class="central-column-container dark-1">

      <router-link :to="{ name: fromTemplate ? 'NewPollTemplate' : 'OrganizationPolls'}"
        class="w3-hide-medium w3-hide-small back-button-in-column">
        <app-back-button class=""></app-back-button>
      </router-link>

      <div class="w3-row column-header">
        <app-column-header>
          {{ $t('NEW-POLL')}} - <i>{{ $t('CUSTOMIZATION') }}</i>
        </app-column-header>
      </div>

      <!-- Title -->
      <div class="w3-row w3-margin-top">
        <app-poll-text-input
          v-model="poll.title"
          placeholder="Poll Title"
          :restorable="fromTemplate"
          :showAsInput="customTitle || !fromTemplate"
          @set-custom-value="setCustomTitle()"
          @custom-value-back="customTitleBack()">
        </app-poll-text-input>
        <app-error-panel :show="showErrors && poll.title === ''"
          message="poll title cannot be empty">
        </app-error-panel>
      </div>

      <!-- Description -->
      <div class="w3-row w3-margin-top">
        <app-poll-text-input
          v-model="poll.description"
          placeholder="Poll Description"
          :restorable="fromTemplate"
          :useTextArea="true"
          :showAsInput="customDescription || !fromTemplate"
          @set-custom-value="setCustomDescription()"
          @custom-value-back="customDescriptionBack()">
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
            </div>
            <div class="w3-col l10 s12">
              <!-- Axis -->
              <div class="w3-row w3-margin-bottom">
                <app-poll-text-input
                  v-model="axis.title"
                  placeholder="Axis title"
                  :restorable="fromTemplate"
                  :showAsInput="axis.custom || !fromTemplate"
                  @set-custom-value="customAxis(axis.id)"
                  @custom-value-back="customAxisBack(axis.id)">
                </app-poll-text-input>
                <app-error-panel :show="showErrors && axis.title === ''"
                  message="axis title cannot be empty">
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
                  @remove="removeQuestion(axis, question)"
                  :restorable="fromTemplate"
                  @set-custom-question="customQuestion(axis.id, question.id)"
                  @custom-question-back="customQuestionBack(axis.id, question.id)">
                </app-poll-question-input>

                <app-error-panel :show="showErrors && question.text === ''"
                  :message="$t('QUESTION-TEXT-NOT-EMPTY')">
                </app-error-panel>
              </div>

              <div v-if="axis.custom" class="w3-row add-question-row">
                <app-plus-button @click="newQuestion(axis)" class="w3-right"></app-plus-button>
                <span class="w3-right">{{ $t('ADD-QUESTION') }}</span>
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
          <span class="w3-left">{{ $t('ADD-DEGREE') }}</span>
          <app-plus-button @click="newAxis()" class="w3-left"></app-plus-button>
        </div>
      </div>
      <div class="w3-row">
        <app-error-panel :show="showErrorsBottom"
          :message="$t('ERRORS-IN-POLL')">
        </app-error-panel>
        <app-button @click="next()" class="w3-right">{{ $t('NEXT') }}</app-button>
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

export default {
  components: {
    'app-poll-text-input': PollTextInput,
    'app-poll-question-input': PollQuestionInput,
    'app-move-btns': MoveBtns
  },
  computed: {
    orgId () {
      return this.$route.params.orgId
    },
    fromTemplate () {
      return this.$store.state.newpoll.fromTemplate
    },
    pollG () {
      return this.$store.state.newpoll.poll
    }
  },

  data () {
    return {
      poll: null,
      customTitle: false,
      customDescription: false,
      showErrors: false,
      showErrorsBottom: false
    }
  },

  methods: {
    setCustomTitle () {
      this.customTitle = true
      this.poll.custom = false
    },
    setCustomDescription () {
      this.customDescription = true
      this.poll.custom = false
    },
    customTitleBack () {
      this.poll.title = this.pollG.title
      this.customTitle = false
      this.poll.custom = this.customDescription
    },
    customDescriptionBack () {
      this.poll.description = this.pollG.description
      this.customDescription = false
      this.poll.custom = this.customTitle
    },
    customAxis (axisId) {
      var ixA = getElIx(axisId, this.poll.axes)
      if (ixA !== -1) {
        this.poll.axes[ixA].custom = true
      }
    },
    customAxisBack (axisId) {
      // Undo the local editions of the poll and overwrite with the global version
      var ixA = getElIx(axisId, this.poll.axes)
      var ixAG = getElIx(axisId, this.pollG.axes)
      if ((ixA !== -1) && (ixAG !== -1)) {
        this.poll.axes.splice(ixA, 1, JSON.parse(JSON.stringify(this.pollG.axes[ixAG])))
        this.poll.axes[ixA].custom = false
      }
    },
    customQuestion (axisId, questionId) {
      var axisIx = getElIx(axisId, this.poll.axes)
      if (axisIx === -1) {
        return
      }
      var questionIx = getElIx(questionId, this.poll.axes[axisIx].questions)

      if (questionIx !== -1) {
        this.poll.axes[axisIx].questions[questionIx].custom = true
      }
    },
    customQuestionBack (axisId, questionId) {
      // Undo the local editions of the poll and overwrite with the global version
      var axisIx = getElIx(axisId, this.poll.axes)
      var axisIxG = getElIx(axisId, this.pollG.axes)

      if ((axisIx === -1) || (axisIxG === -1)) {
        return
      }
      var questionIx = getElIx(questionId, this.poll.axes[axisIx].questions)
      var questionIxG = getElIx(questionId, this.pollG.axes[axisIx].questions)

      if ((questionIx !== -1) || (questionIxG !== -1)) {
        this.poll.axes[axisIx].questions.splice(questionIx, 1, JSON.parse(JSON.stringify(this.pollG.axes[axisIxG].questions[questionIxG])))
        this.poll.axes[axisIx].questions[questionIx].custom = false
      }
    },
    newAxis () {
      this.poll.axes.push(getEmptyAxis())
    },
    axisWeightsOk (axisId) {
      var ixA = getElIx(axisId, this.poll.axes)
      if (ixA !== -1) {
        var axis = this.poll.axes[ixA]
        var weight = 0
        var hasRates = false
        for (var ixQ in axis.questions) {
          var question = axis.questions[ixQ]
          if (question.type === 'RATE_1_5') {
            hasRates = true
            weight += question.weight
          }
        }
        if (!hasRates) {
          return true
        } else {
          return Math.abs(weight - 100) < 0.001
        }
      }
    },
    axisHasARate (axisId) {
      var ixA = getElIx(axisId, this.poll.axes)
      if (ixA !== -1) {
        var axis = this.poll.axes[ixA]
        for (var ixQ in axis.questions) {
          var question = axis.questions[ixQ]
          if (question.type === 'RATE_1_5') {
            return true
          }
        }
        return false
      }
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
    },
    next () {
      var ok = true

      if (this.poll.title === '') {
        ok = false
      }

      for (var ixAxis in this.poll.axes) {
        var axis = this.poll.axes[ixAxis]
        if (axis.title === '') {
          ok = false
        }

        for (var ixQuest in axis.questions) {
          var question = axis.questions[ixQuest]
          if (question.text === '') {
            ok = false
          }
        }

        if (!this.axisWeightsOk(axis.id)) {
          // ok = false
        }

        if (!this.axisHasARate(axis.id)) {
          ok = false
        }
      }

      if (ok) {
        this.$store.commit('setNewPoll', this.poll)
        this.$store.commit('setEditingNewPoll', true)

        this.$router.push({name: 'NewPollConfiguration'})
      } else {
        this.showErrors = true
        this.showErrorsBottom = true
        setTimeout(() => {
          this.showErrorsBottom = false
        }, 3000)
      }
    }
  },

  created () {
    /* works with a local copy of the poll state */
    this.poll = JSON.parse(JSON.stringify(this.$store.state.newpoll.poll))
  }
}
</script>

<style scoped>

.axes-container {
}

.axis-container {
  padding-top: 22px;
}

.axis-number-col {
  padding-top: 12px;
}

.question-container {
}

.add-question-row > span, .new-axis-row > span {
  font-size: 18px;
  padding-top: 6px;
  padding-right: 22px;
}

</style>
