<template lang="html">
  <div class="w3-container content-container this-container">

    <router-link :to="{ name: 'OrganizationPolls'}"
      class="w3-row disp-block w3-margin-bottom w3-hide-large">
      <app-back-button class=""></app-back-button>
    </router-link>

    <div class="central-column-container dark-1">

      <router-link :to="{ name: fromTemplate ? 'NewPollTemplate' : 'OrganizationPolls'}"
        class="w3-hide-medium w3-hide-small back-button-in-column">
        <app-back-button class=""></app-back-button>
      </router-link>

      <div class="w3-row">
        <app-column-header>
          New Poll - <i>Customization</i>
        </app-column-header>
      </div>

      <!-- Title -->
      <div class="w3-row w3-margin-top">
        <app-poll-text-input
          v-model="poll.title"
          placeholder="Poll Title"
          :restorable="fromTemplate">
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
          :useTextArea="true">
        </app-poll-text-input>
      </div>

      <!-- Axes -->
      <div class="axes-container">
        <div v-for="axis in poll.axes" :key="axis.id" class="w3-row axis-container w3-margin-top">

          <div class="w3-col l2 s12 axis-number-col w3-center w3-margin-bottom">
            <h4>Axis 1</h4>
          </div>
          <div class="w3-col l10 s12">
            <!-- Axis -->
            <div class="w3-row w3-margin-bottom">
              <app-poll-text-input
                v-model="axis.title"
                placeholder="Axis title"
                :restorable="fromTemplate">
              </app-poll-text-input>
              <app-error-panel :show="showErrors && axis.title === ''"
                message="axis title cannot be empty">
              </app-error-panel>
              <app-error-panel :show="axis.title.length > 25"
                message="axis title should be shorter">
              </app-error-panel>
            </div>

            <!-- Questions -->
            <div v-for="(question, ixq) in axis.questions" :key="question.id" class="question-container w3-margin-top">
              <app-poll-question-input v-model="axis.questions[ixq]"></app-poll-question-input>
              <app-error-panel :show="showErrors && question.text === ''"
                message="question text cannot be empty">
              </app-error-panel>
            </div>

            <div class="w3-row w3-margin-top question-container">
              <button @click="newQuestion(axis)" type="button" name="button">new question</button>
            </div>
            <app-error-panel :show="!axisWeightsOk(axis.id)"
              message="weights of rate questions must sum 100%">
            </app-error-panel>
            <app-error-panel :show="!axisHasARate(axis.id)"
              message="at least one question must be rated">
            </app-error-panel>

          </div>
        </div>
        <div class="w3-row w3-margin-top axis-container">
          <button @click="newAxis()" type="button" name="button">new axis</button>
        </div>
      </div>
      <hr>
      <div class="w3-row w3-center">
        <button @click="next()" type="button" name="button">contine</button>
      </div>
    </div>
  </div>
</template>

<script>
import PollTextInput from '@/components/newpoll/PollTextInput'
import PollQuestionInput from '@/components/newpoll/PollQuestionInput'
import { getEmptyQuestion, getEmptyAxis } from '@/support/newPollEmptyElements'
import { getElIx } from '@/support/general'

export default {
  components: {
    'app-poll-text-input': PollTextInput,
    'app-poll-question-input': PollQuestionInput
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
      showErrors: false
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

      if (questionIx !== -1) {
        this.poll.axes[axisIx].questions.splice(questionIx, 1)
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

        if (axis.title.length > 25) {
          ok = false
        }

        for (var ixQuest in axis.questions) {
          var question = axis.questions[ixQuest]
          if (question.text === '') {
            ok = false
          }
        }
      }

      if (ok) {
        this.$store.commit('setNewPoll', this.poll)
        this.$store.commit('setEditingNewPoll', true)

        this.$router.push({name: 'NewPollConfiguration'})
      } else {
        this.showErrors = true
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

</style>
