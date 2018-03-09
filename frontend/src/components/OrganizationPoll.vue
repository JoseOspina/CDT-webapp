<template lang="html">
  <div v-if="poll !== null && details !== null" class="w3-container organization-poll-container">
    <div class="column-container">
      <div class="back-button cursor-pointer">
        <div class="back-button-content">
          <i class="fa fa-chevron-left" aria-hidden="true"></i>
        </div>
      </div>

      <div class="w3-row">
        <app-new-poll-header>
          Poll Details
        </app-new-poll-header>
      </div>

      <div class="w3-row">
        <div class="w3-col m8">
          <div class="w3-row w3-center title-container">
            <h3>{{ poll.title }}</h3>
            <p>{{ poll.description }}</p>
          </div>
          <div class="w3-row-padding">
            <div class="w3-col m6 w3-center">
              <button class="w3-button app-button" @click="makeTemplate(!poll.isTemplate)">{{ poll.isTemplate ? 'remove' : 'mark as'}} template</button>
            </div>
            <div class="w3-col m6 w3-center">
              <button v-if="poll.isTemplate" class="w3-button app-button" @click="makeTemplatePublic(!poll.isPublicTemplate)">make it {{ poll.isPublicTemplate ? 'private' : 'public' }}</button>
            </div>
          </div>
          <div v-if="poll.isTemplate" class="w3-row w3-margin-top w3-padding message-panel w3-round">
            <span>this poll can be used as a template when creating new polls in {{ poll.isPublicTemplate ? 'any' : 'this' }} organization</span>
          </div>
        </div>
        <div class="w3-col m4">
          <div class="w3-row w3-center n-answers-div">
            <b>{{ details.numberOfAnswers }}</b>
          </div>
          <div class="w3-row w3-center">
            answers
          </div>
        </div>
      </div>
      <div class="w3-row">
        <input type="text" name="" :value="pollAnswerUrl">
      </div>
      <div v-if="details.numberOfAnswers > 0" class="">
        <hr>
        <div class="w3-row">
          <app-radar-chart v-if="chartData.length > 0" :chartData="chartData"></app-radar-chart>
        </div>
        <div class="w3-row">
          <h4>Detailed Results:</h4>
          <div v-for="axis in poll.axes" :key="axis.id" class="w3-row">
            <app-poll-question-input
              :value="axis.title"
              :showAsInput="false">
            </app-poll-question-input>

            <div v-for="question in axis.questions" :key="question.id" class="w3-row question-result-row">
              <app-poll-question-results
                :question="question"
                :stats="getQuestionResultStats(question.id)"
                :textAnswers="getQuestionTextResults(question.id)">
              </app-poll-question-results>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import RadarChart from '@/components/RadarChart'
import AppButton from '@/components/styled/AppButton'
import NewPollHeader from '@/components/styled/NewPollHeader'
import PollQuestionInput from '@/components/styled/PollQuestionInput'
import PollQuestionResults from '@/components/PollQuestionResults'

export default {
  components: {
    'app-radar-chart': RadarChart,
    'app-button': AppButton,
    'app-new-poll-header': NewPollHeader,
    'app-poll-question-input': PollQuestionInput,
    'app-poll-question-results': PollQuestionResults
  },

  data () {
    return {
      poll: null,
      details: null,
      chartData: [],
      errorMakingTemplate: false,
      errorMakingTemplateMsg: ''
    }
  },

  computed: {
    pollId () {
      return this.$route.params.pollId
    },
    pollAnswerUrl () {
      return window.location.host + '/app/answer/' + this.poll.id
    }
  },

  methods: {
    update () {
      if (this.pollId) {
        if (this.pollId !== '') {
          this.axios.get('/1/poll/' + this.pollId).then((response) => {
            if (response.data.result === 'success') {
              console.log('updating poll data')
              this.poll = response.data.data
              return this.axios.get('/1/poll/' + this.poll.id + '/details')
            }
          }).then((response) => {
            if (response.data.result === 'success') {
              this.details = response.data.data
              this.updateStats()
              this.updateChartData()
            }
          })
        }
      }
    },
    updateChartData () {
      if (this.details === null) {
        return []
      }
      var layerData = []
      for (var ixA in this.details.axesResults) {
        var axisResults = this.details.axesResults[ixA]
        var meanCombined = 0
        for (var ixQ in axisResults.questionResults) {
          var questionResult = axisResults.questionResults[ixQ]
          if (questionResult.questionType === 'RATE_1_5') {
            meanCombined += questionResult.mean * questionResult.weight / 100.0
          }
        }
        layerData.push({area: axisResults.axisTitle, value: meanCombined})
      }
      this.chartData = [layerData]
    },
    updateStats () {
      for (var ixA in this.details.axesResults) {
        var axisResults = this.details.axesResults[ixA]
        for (var ixQ in axisResults.questionResults) {
          var questionResult = axisResults.questionResults[ixQ]
          this.updateQuestionStats(questionResult.questionId)
        }
      }
    },
    updateQuestionStats (questionId) {
      var questionResult = this.getQuestionResult(questionId)
      if (questionResult.questionType === 'RATE_1_5') {
        var sum = 0
        var min = 999
        var max = -1
        for (var ixR in questionResult.answersRates) {
          var rate = questionResult.answersRates[ixR]
          sum += rate
          if (rate < min) {
            min = rate
          }
          if (rate > max) {
            max = rate
          }
        }

        var mean = sum / questionResult.answersRates.length
        questionResult.min = min
        questionResult.mean = mean
        questionResult.max = max
      }
    },
    getQuestionResult (questionId) {
      for (var ixA in this.details.axesResults) {
        var axisResults = this.details.axesResults[ixA]
        for (var ixQ in axisResults.questionResults) {
          var questionResult = axisResults.questionResults[ixQ]
          if (questionResult.questionId === questionId) {
            return questionResult
          }
        }
      }
    },
    getQuestionResultStats (questionId) {
      var result = this.getQuestionResult(questionId)
      return [ result.min, result.mean, result.max ]
    },
    getQuestionTextResults (questionId) {
      return this.getQuestionResult(questionId).answersTexts
    },
    makeTemplate (value) {
      this.axios.put('/1/poll/' + this.pollId + '/makeTemplate', {},
        {
          params: {
            isTemplate: value
          }
        }).then((response) => {
        if (response.data.result === 'success') {
          this.update()
        } else {
          this.errorMakingTemplate = true
          this.errorMakingTemplateMsg = response.data.message
        }
      })
    }
  },

  created () {
    this.update()
  }
}
</script>

<style scoped>

.organization-poll-container {
  min-height: calc(100vh - 66px);
  background: url('./../assets/background-1.png') center left / cover no-repeat;
  background-color: #2F2F2F;
  color: white;
}

.n-answers-div {
  color: #FFDE17;
  font-size: 55px;
  padding-top: 22px;
}

.title-container {
  padding: 6px 0px;
}

.message-panel {
  background-color: #3F3E3E;
}

.question-result-row {
  width: calc(100% - 50px);
  margin: 0 auto;
}

</style>
