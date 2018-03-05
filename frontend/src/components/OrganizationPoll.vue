<template lang="html">
  <div v-if="poll !== null && details !== null" class="">
    Poll details
    <h3>{{ poll.title }}</h3>
    <p>{{ poll.description }}</p>
    <button v-if="!poll.isTemplate" @click="makeTemplate(true)" type="button" name="button">make template</button>
    <button v-else @click="makeTemplate(false)" type="button" name="button">remove template</button>
    <br><br>
    Number of answers: <b>{{ details.numberOfAnswers }}</b>
    <div v-if="details.numberOfAnswers > 0" class="">
      <app-radar-chart v-if="chartData.length > 0" :chartData="chartData"></app-radar-chart>
    </div>
  </div>
</template>

<script>
import RadarChart from '@/components/RadarChart'
export default {
  components: {
    'app-radar-chart': RadarChart
  },

  data () {
    return {
      poll: null,
      details: null,
      errorMakingTemplate: false,
      errorMakingTemplateMsg: ''
    }
  },

  computed: {
    pollId () {
      return this.$route.params.pollId
    },
    chartData () {
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
            var sum = 0
            var min = 999
            var max = -1
            for (var ixR in questionResult.answersRates) {
              var rate = questionResult.answersRates[ixR]
              sum += rate
              if (rate < min) {
                min = rate
              }
              if (max > rate) {
                max = rate
              }
            }
            var mean = sum / questionResult.answersRates.length
            meanCombined += mean * questionResult.weight / 100.0
          }
        }
        layerData.push({area: axisResults.axisTitle, value: meanCombined})
      }
      return [layerData]
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
            }
          })
        }
      }
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

<style lang="css">
</style>
