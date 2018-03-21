<template lang="html">
  <div v-if="poll !== null && details !== null"
    class="w3-container content-container">

    <router-link :to="{ name: 'OrganizationPolls'}"
      class="w3-row disp-block w3-margin-bottom w3-hide-large">
      <app-back-button class=""></app-back-button>
    </router-link>

    <div class="central-column-container dark-1">

      <router-link :to="{ name: 'OrganizationPolls'}"
        class="w3-hide-medium w3-hide-small back-button-in-column">
        <app-back-button class=""></app-back-button>
      </router-link>

      <div class="w3-row w3-display-container">
        <app-column-header>
          {{ $t('POLL-DETAILS') }}
        </app-column-header>
        <div @click="editPoll()" class="edit-btn w3-display-right cursor-pointer">
          <i class="fa fa-pencil" aria-hidden="true"></i>
        </div>
      </div>

      <div class="w3-row w3-margin-top">
        <div class="w3-col m8">
          <div class="w3-row w3-padding title-container dark-3 w3-round-large">
            <h3>{{ poll.title }}</h3>
            <vue-markdown class="marked-text" :source="poll.description.length < 400 ? poll.description : poll.description.slice(0, 399) + ' ...'"></vue-markdown>
          </div>
          <div class="w3-row-padding w3-margin-top">

            <div class="w3-col m6 bullet-input">
              <div @click="localIsTemplate = !localIsTemplate" class="w3-left w3-padding fa-container cursor-pointer">
                <i class="fa fa-circle rating-dot" :class="{'light-co': localIsTemplate}" aria-hidden="true"></i>
              </div>
              <div class="w3-left w3-margin-left text">{{ $t('IS-TEMPLATE')}}</div>
            </div>
            <div v-if="poll.isTemplate" class="w3-col m6 bullet-input">
              <div @click="localIsTemplatePublic = !localIsTemplatePublic" class="w3-left w3-padding fa-container cursor-pointer">
                <i class="fa fa-circle rating-dot" :class="{'light-co': localIsTemplatePublic}" aria-hidden="true"></i>
              </div>
              <div class="w3-left w3-margin-left text">{{ $t('PUBLIC') }}</div>
            </div>
          </div>
          <div v-if="poll.isTemplate" class="w3-row w3-margin-top w3-padding dark-3 anouncement w3-round">
            <span v-if="poll.isPublicTemplate"><i>{{ $t('IS-TEMPLATE-TEXT') }}</i></span>
            <span v-else><i>{{ $t('IS-PUBLIC-TEXT') }}</i></span>
          </div>
        </div>
        <div class="w3-col m4">
          <div class="w3-row w3-center n-answers-div">
            <b>{{ details.numberOfAnswers }}</b>
          </div>
          <div class="w3-row w3-center">
            {{ $t('ANSWERS') }}
          </div>
        </div>
      </div>
      <div class="w3-row w3-margin-top">
        <span class="w3-margin-left w3-small anouncement">{{ $t('LINK-TO-POLL') }}:</span>
        <input class="rounded-input dark-3 app-color-2-br" type="text" name="" :value="pollAnswerUrl">
      </div>
      <div v-if="details.numberOfAnswers > 0" class="">
        <hr>
        <h4>{{ $t("RESULTS") }}:</h4>
        <div class="w3-row w3-round-large">
          <app-radar-chart v-if="chartData.length > 0" :chartData="chartData"></app-radar-chart>
        </div>
        <div class="w3-row w3-margin-top">
          <h4>{{ $t('DETAILED-RESULTS') }}:</h4>
          <div v-for="axis in poll.axes" :key="axis.id" class="w3-row axis-row">
            <div class="rounded-input axis-title app-color-2-br">
             <h4>{{ axis.title }}</h4>
            </div>

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

      <div class="bottom-row">
        <div class="w3-row w3-center">
          <button class="w3-button app-button-danger" @click="deleteIntent()">{{ $t('DELETE') }}</button>
        </div>
        <div v-if="deleteTemplateIntent" class="w3-row w3-center w3-margin-top">
          <button class="w3-button app-button" @click="deleteTemplateIntent = false">{{ $t('CANCEL') }}</button>
          <button class="w3-button app-button-danger" @click="deletePoll()">{{ $t('CONFIRM') }}</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import RadarChart from '@/components/RadarChart'
import PollQuestionResults from '@/components/PollQuestionResults'

export default {
  components: {
    'app-radar-chart': RadarChart,
    'app-poll-question-results': PollQuestionResults
  },

  data () {
    return {
      poll: null,
      details: null,
      chartData: [],
      errorMakingTemplate: false,
      errorMakingTemplateMsg: '',
      localIsTemplate: false,
      localIsTemplatePublic: false,
      loaded: false,
      deleteTemplateIntent: false
    }
  },

  computed: {
    pollId () {
      return this.$route.params.pollId
    },
    pollAnswerUrl () {
      return window.location.host + '/#/app/answer/' + this.poll.id
    }
  },

  watch: {
    localIsTemplate () {
      if (this.loaded) {
        this.makeTemplate(this.localIsTemplate)
      }
    },
    localIsTemplatePublic () {
      if (this.loaded) {
        this.makeTemplatePublic(this.localIsTemplatePublic)
      }
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
              this.localIsTemplate = this.poll.isTemplate
              this.localIsTemplatePublic = this.poll.isPublicTemplate
              return this.axios.get('/1/poll/' + this.poll.id + '/details')
            }
          }).then((response) => {
            if (response.data.result === 'success') {
              this.details = response.data.data
              this.loaded = true
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
        var totalWeight = 0

        /* recheck total weight */
        for (var ixQ1 in axisResults.questionResults) {
          var questionResult1 = axisResults.questionResults[ixQ1]
          totalWeight += questionResult1.weight
        }

        for (var ixQ2 in axisResults.questionResults) {
          var questionResult = axisResults.questionResults[ixQ2]
          if (questionResult.questionType === 'RATE_1_5') {
            meanCombined += questionResult.mean * questionResult.weight / totalWeight
          }
        }
        layerData.push({
          area: axisResults.axisTitle.length < 20 ? axisResults.axisTitle : axisResults.axisTitle.slice(0, 19) + '..',
          value: meanCombined
        })
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
    },
    makeTemplatePublic (value) {
      this.axios.put('/1/poll/' + this.pollId + '/makePublicTemplate', {},
        {
          params: {
            isPublic: value
          }
        }).then((response) => {
        if (response.data.result === 'success') {
          this.update()
        } else {
          this.errorMakingTemplate = true
          this.errorMakingTemplateMsg = response.data.message
        }
      })
    },
    deleteIntent () {
      setTimeout(() => {
        this.deleteTemplateIntent = true
      }, 1000)
    },
    deletePoll () {
      this.axios.delete('/1/poll/' + this.pollId).then((response) => {
        if (response.data.result === 'success') {
          this.$router.push({name: 'OrganizationPolls'})
        }
      })
    },
    editPoll () {
      this.$router.push({name: 'EditPoll'})
    }
  },

  created () {
    this.update()
  }
}
</script>

<style scoped>

.edit-btn {
  padding: 8px 16px;
  margin-right: 2px;
  border-bottom-right-radius: 16px;
  font-size: 22px;
}

.edit-btn:hover {
  background-color: #d6d6d6;
  color: #2F2F2F;
}

.bullet-input .fa-container:hover {
  color: #FFDE17;
}

.bullet-input .fa {
  font-size: 18px;
}

.bullet-input .text {
  padding-top: 6px;
}

.n-answers-div {
  color: #FFDE17;
  font-size: 55px;
}

.title-container {
  padding: 16px 6px 6px 6px;;
}

.question-result-row {
  width: calc(100% - 50px);
  margin: 0 auto;
}

.axis-row {
  margin-top: 25px;
  margin-bottom: 45px;
}

.axis-title {
  border-radius: 16px;
}

.bottom-row button {
  width: 100px;
}

</style>
