<template lang="html">
  <div class="w3-container">
    <h3>Prepare Poll</h3>

    <!-- Title -->
    <div v-if="fromTemplate && !customTitle" class="w3-row">
      <i @click="customTitle = true" class="w3-left fa fa-pencil" aria-hidden="true"></i>
      <h2 class="w3-left">{{ poll.title }}</h2>
    </div>
    <div v-else class="w3-row">
      <i v-if="fromTemplate" @click="customTitle = false" class="w3-left fa fa-undo" aria-hidden="true"></i>
      <input v-model="poll.title" placeholder="Polls Title" class="w3-input w3-round" type="text" name="" value="">
      <app-error-panel :show="showErrors && poll.title === ''"
        message="poll title cannot be empty">
      </app-error-panel>
    </div>

    <!-- Description -->
    <div v-if="fromTemplate && !customDescription" class="w3-row">
      <i @click="customDescription = true" class="w3-left fa fa-pencil" aria-hidden="true"></i>
      <p>{{ poll.description }}</p>
    </div>
    <div v-else class="w3-row">
      <i v-if="fromTemplate" @click="customDescription = false" class="w3-left fa fa-undo" aria-hidden="true"></i>
      <textarea v-model="poll.description" placeholder="Poll Description" class="w3-input w3-round w3-margin-top w3-border"></textarea>
    </div>

    <!-- Axes -->
    <div class="axes-container">
      <div v-for="(axis, ix) in poll.axes" :key="axis.id" class="axis-container w3-margin-top">

        <!-- Axis -->
        <div v-if="fromTemplate && !axis.custom" class="w3-row">
          <i @click="customAxis(axis.id)" class="w3-left fa fa-pencil" aria-hidden="true"></i>
          <h3>{{ axis.title }}</h3>
        </div>
        <div v-else class="">
          <i v-if="fromTemplate && axis.custom" @click="axis.custom = false" class="w3-left fa fa-undo" aria-hidden="true"></i>
          <button v-if="ix > 0" @click="removeAxis(axis)" type="button" name="button">remove axis</button>
          <input v-model="axis.title" placeholder="Axis title" class="w3-input w3-round">
          <app-error-panel :show="showErrors && axis.title === ''"
            message="axis title cannot be empty">
          </app-error-panel>
          <app-error-panel :show="axis.title.length > 25"
            message="axis title should be shorter">
          </app-error-panel>
        </div>

        <!-- Questions -->
        <div v-for="(question, ixq) in axis.questions" :key="question.id" class="question-container w3-margin-top">

          <div v-if="fromTemplate && !question.custom" class="w3-row">
            <i @click="customQuestion(axis.id, question.id)" class="w3-left fa fa-pencil" aria-hidden="true"></i>
            <p>{{ question.text }}</p>
          </div>
          <div v-else class="">
            <i v-if="fromTemplate && question.custom" @click="question.custom = false" class="w3-left fa fa-undo" aria-hidden="true"></i>
            <button v-if="ixq > 0" @click="removeQuestion(axis, question)" type="button" name="button">remove question</button>
            <div class="w3-row-padding">
              <div class="w3-col m6">
                <textarea v-model="question.text" placeholder="Question" class="w3-input w3-round"></textarea>
              </div>
              <div class="w3-col m6 select-col">
                <div class="w3-row-padding">
                  <div class="w3-col s6">
                    <select v-model="question.type" class="w3-input">
                      <option value="RATE_1_5">Rate 1 to 5</option>
                      <option value="TEXT">Open text</option>
                    </select>
                  </div>
                  <div v-if="question.type === 'RATE_1_5'" class="w3-col s6">
                    <input v-model.number="question.weight" class="w3-input" type="text" name="" value="">
                  </div>
                </div>
              </div>
            </div>
            <app-error-panel :show="showErrors && question.text === ''"
              message="question text cannot be empty">
            </app-error-panel>
          </div>
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
      <div class="w3-row w3-margin-top axis-container">
        <button @click="newAxis()" type="button" name="button">new axis</button>
      </div>
    </div>
    <hr>
    <div class="w3-row w3-center">
      <button @click="next()" type="button" name="button">contine</button>
    </div>
  </div>
</template>

<script>
import { getEmptyQuestion, getEmptyAxis } from '@/support/newPollEmptyElements'
import { getElIx } from '@/support/general'

export default {
  computed: {
    orgId () {
      return this.$route.params.orgId
    },
    fromTemplate () {
      return this.$store.state.newpoll.fromTemplate
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
    customAxis (axisId) {
      var ixA = getElIx(axisId, this.poll.axes)
      if (ixA !== -1) {
        this.poll.axes[ixA].custom = true
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
  padding-left: 30px;
}

.question-container {
  padding-left: 30px;
}

.select-col {
  padding-top: 15px;
}

</style>
