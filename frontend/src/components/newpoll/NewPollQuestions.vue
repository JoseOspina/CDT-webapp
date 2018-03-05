<template lang="html">
  <div class="w3-container">
    <h3>Prepare Poll</h3>
    <input v-model="poll.title" placeholder="Polls Title" class="w3-input w3-round" type="text" name="" value="">
    <app-error-panel :show="showErrors && poll.title === ''"
      message="poll title cannot be empty">
    </app-error-panel>
    <textarea v-model="poll.description" placeholder="Poll Description" class="w3-input w3-round w3-margin-top"></textarea>
    <div class="axes-container">
      <div v-for="(axis, ix) in poll.axes" :key="axis.id" class="axis-container w3-margin-top">
        <button v-if="ix > 0" @click="removeAxis(axis)" type="button" name="button">remove axis</button>
        <input v-model="axis.title" placeholder="Axis title" class="w3-input w3-round">
        <app-error-panel :show="showErrors && axis.title === ''"
          message="axis title cannot be empty">
        </app-error-panel>
        <app-error-panel :show="axis.title.length > 25"
          message="axis title should be shorter">
        </app-error-panel>
        <div v-for="(question, ixq) in axis.questions" :key="question.id" class="question-container w3-margin-top">
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
    }
  },

  data () {
    return {
      poll: null,
      showErrors: false
    }
  },

  methods: {
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
        var hasRates = false
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
