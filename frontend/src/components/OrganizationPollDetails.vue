<template lang="html">
  <div v-if="poll !== null && details !== null" class="">
    <div class="w3-row w3-display-container">
      <app-column-header>
        {{ $t('POLL-DETAILS') }}
      </app-column-header>
      <div @click="$emit('edit')" class="edit-btn w3-display-right cursor-pointer">
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
          <b>{{ details.answerBatches.length }}</b>
        </div>
        <div class="w3-row w3-center">
          {{ $t('ANSWERS') }}
        </div>
      </div>
    </div>

    <div v-if="poll.config.audience === 'ANYONE_WITH_LINK'" class="w3-row w3-margin-top">
      <span class="w3-margin-left w3-small anouncement">{{ $t('LINK-TO-POLL') }}:</span>
      <input class="rounded-input dark-3 app-color-2-br" type="text" name="" :value="pollAnswerUrl">
    </div>
    <div v-if="poll.config.audience === 'ANY_MEMBER'" class="w3-row w3-margin-top w3-center">
      <span class="w3-margin-left w3-small anouncement">{{ $t('POLL-URL-SENT-TO-MEMBERS') }}</span>
    </div>

    <div v-if="details.answerBatches.length > 0" class="">
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
              v-if="question.type !== 'CONTEXT_TEXT'"
              :question="question"
              :stats="getQuestionResultStats(question.id)"
              :textAnswers="getQuestionTextResults(question.id)">
            </app-poll-question-results>
          </div>
        </div>
      </div>
      <div class="w3-row w3-center">
        <button class="w3-button app-button" @click="downloadHtml()">{{ $t('DOWNLOAD HTML') }}</button>
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
</template>

<script>
import RadarChart from '@/components/RadarChart'
import PollQuestionResults from '@/components/PollQuestionResults'

export default {
  components: {
    'app-radar-chart': RadarChart,
    'app-poll-question-results': PollQuestionResults
  },

  props: {
    pollId: {
      type: String,
      default: ''
    }
  },

  data () {
    return {
      poll: null,
      details: null,
      chartData: [],
      axesResults: [],
      errorMakingTemplate: false,
      errorMakingTemplateMsg: '',
      localIsTemplate: false,
      localIsTemplatePublic: false,
      loaded: false,
      deleteTemplateIntent: false
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

  computed: {
    pollAnswerUrl () {
      return window.location.host + '/#/app/answer/' + this.poll.id
    }
  },

  methods: {
    update () {
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
            this.updateAxesResults()
            this.updateStats()
            this.updateChartData()
          }
        })
      }
    },
    updateAxesResults () {
      /* rearrange answer batches per axis and per question instead of as
         per batch to do stats analysis and build the radial plot  */
      this.axesResults = []
      for (var ixA in this.poll.axes) {
        var thisResults = {}
        let axis = this.poll.axes[ixA]

        thisResults.axis = axis
        thisResults.questionResults = []

        for (var ixQ in axis.questions) {
          var questionResult = {}
          let question = axis.questions[ixQ]

          questionResult.question = question
          questionResult.answers = this.getAllAnswersToQuestion(question.id)

          thisResults.questionResults.push(questionResult)
        }

        this.axesResults.push(thisResults)
      }
    },
    getAllAnswersToQuestion (questionId) {
      var thisAnswers = []
      for (var ixB in this.details.answerBatches) {
        let thisBatch = this.details.answerBatches[ixB]
        let thisAnswer = thisBatch.answers.find((e) => {
          return e.questionId === questionId
        })
        if (thisAnswer !== null) {
          thisAnswers.push(thisAnswer)
        }
      }
      return thisAnswers
    },
    updateChartData () {
      var layerData = []

      for (var ixA in this.axesResults) {
        var axisResults = this.axesResults[ixA]

        if (axisResults.axis.includeInPlot) {
          var meanCombined = 0
          var totalWeight = 0

          /* recheck total weight */
          for (var ixQ1 in axisResults.questionResults) {
            var questionResult1 = axisResults.questionResults[ixQ1]
            if (questionResult1.question.type === 'RATE_1_5') {
              totalWeight += questionResult1.question.weight
            }
          }

          for (var ixQ2 in axisResults.questionResults) {
            var questionResult = axisResults.questionResults[ixQ2]
            if (questionResult.question.type === 'RATE_1_5') {
              meanCombined += questionResult.stats.mean * questionResult.question.weight / totalWeight
            }
          }

          layerData.push({
            area: axisResults.axis.title.length < 20 ? axisResults.axis.title : axisResults.axis.title.slice(0, 19) + '..',
            value: meanCombined
          })
        }
      }
      this.chartData = [ layerData ]
    },
    updateStats () {
      for (var ixA in this.axesResults) {
        var axisResults = this.axesResults[ixA]
        for (var ixQ in axisResults.questionResults) {
          var questionResult = axisResults.questionResults[ixQ]
          this.$set(axisResults.questionResults, ixQ, this.updateQuestionStats(questionResult.question.id))
        }
        this.$set(this.axesResults, ixA, axisResults)
      }
    },
    updateQuestionStats (questionId) {
      var questionResult = this.getQuestionResult(questionId)
      if (!questionResult) {
        return
      }

      if (questionResult.question.type === 'RATE_1_5') {
        /* remove undefined answers (empty answers) and extract rates */
        let rates = questionResult.answers.filter(e => e).map(e => e.rate)
        var sum = 0
        var min = 999
        var max = -1
        for (var ixR in rates) {
          var rate = rates[ixR]
          sum += rate
          if (rate < min) {
            min = rate
          }
          if (rate > max) {
            max = rate
          }
        }

        var mean = sum / rates.length
        questionResult.stats = {}
        questionResult.stats.min = min
        questionResult.stats.mean = mean
        questionResult.stats.max = max
      }

      return questionResult
    },
    getQuestionResult (questionId) {
      for (var ixA in this.axesResults) {
        var axisResults = this.axesResults[ixA]
        for (var ixQ in axisResults.questionResults) {
          var questionResult = axisResults.questionResults[ixQ]
          if (questionResult.question.id === questionId) {
            return questionResult
          }
        }
      }
    },
    getQuestionResultStats (questionId) {
      let questionResult = this.getQuestionResult(questionId)
      if (questionResult.question.type === 'RATE_1_5') {
        return questionResult.stats
      }
      return null
    },
    getQuestionTextResults (questionId) {
      let questionResult = this.getQuestionResult(questionId)
      if (questionResult.question.type === 'TEXT') {
        return questionResult.answers.filter(e => e).map(e => e.text)
      }
      return []
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
    downloadHtml () {
      let table = ''
      table += '<table>\r\n'
      for (let ixAx in this.poll.axes) {
        let axis = this.poll.axes[ixAx]
        table += '<tr><td><b>' + axis.title + '</b></td></tr>\r\n'
        for (let ixQ in axis.questions) {
          let question = axis.questions[ixQ]
          table += '<tr>\r\n'
          table += '<td>' + question.text + '</td>\r\n'

          for (let ixB in this.details.answerBatches) {
            let batch = this.details.answerBatches[ixB]

            /* search for this question answer */
            let answer = null
            for (let ixAw in batch.answers) {
              let thisAnswer = batch.answers[ixAw]
              if (thisAnswer.questionId === question.id) {
                answer = thisAnswer
                break
              }
            }

            let answerStr = ''
            if (answer !== null) {
              switch (question.type) {
                case 'RATE_1_5':
                  answerStr = answer.rate.toString()
                  break

                case 'TEXT':
                  answerStr = answer.text
                  break
              }
            }
            table += '<td>' + answerStr + '</td>\r\n'
          }
          table += '</tr>\r\n'
        }
      }
      table += '</table>\r\n'

      var dateObj = new Date()
      var monthNum = dateObj.getUTCMonth() + 1
      var monthStr = (monthNum <= 9 ? '0' + monthNum : monthNum).toString()
      var dayStr = (dateObj.getUTCDate() <= 9 ? '0' + dateObj.getUTCDate() : dateObj.getUTCDate()).toString()
      var dateStr = dateObj.getUTCFullYear().toString() + monthStr + dayStr

      let htmlContent = 'data:text/plain;charset=utf-8,'
      htmlContent += '<!DOCTYPE html>\r\n'
      htmlContent += '<html>\r\n'
      htmlContent += '<head>\r\n'
      htmlContent += '<meta charset="utf-8">\r\n'
      htmlContent += '<title>Results for ' + this.poll.title + ' Poll at ' + dateObj.toString() + '</title>\r\n'
      htmlContent += '<style>\r\n'
      htmlContent += 'table { border: 1px solid black;}\r\n'
      htmlContent += 'table, th, td { border: 1px solid black; border-collapse: collapse; padding: 8px;}\r\n'
      htmlContent += '</style>\r\n'
      htmlContent += '</head>\r\n'
      htmlContent += '<body>\r\n'
      htmlContent += table
      htmlContent += '</body>\r\n'
      htmlContent += '</html>\r\n'

      var encodedUri = encodeURI(htmlContent)
      var link = document.createElement('a')
      link.setAttribute('href', encodedUri)
      link.setAttribute('download', dateStr + '-poll_results.html')
      document.body.appendChild(link) // Required for FF

      link.click() // This will download the data file named "my_data.csv".
      /*
      axes.forEach((axisRes) => {
        csvContent += axisRes.axis.title
        axisRes.questionResults.forEach((questionRes) => {
          switch (questionRes.questionType) {
            case 'RATE_1_5':
              if (questionRes.answersRates.length === 0) {
                break
              }
              csvContent += '\r\n' + questionRes.questionText + ','
              csvContent += questionRes.answersRates.join(',')
              break

            case 'TEXT':
              if (questionRes.answersTexts.length === 0) {
                break
              }
              let textsClean = []
              questionRes.answersTexts.forEach((text) => {
                textsClean.push(text.split(',').join('-'))
              })
              csvContent += '\r\n' + questionRes.questionText + ','
              csvContent += textsClean.join(',')
              break

            default:
              break
          }
        })
        csvContent += '\r\n\r\n'
      })
      */
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
  color: rgba(255, 255, 255, 0.23);
}

.edit-btn:hover {
  color: white;
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
