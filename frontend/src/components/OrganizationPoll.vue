<template lang="html">
  <div v-if="poll !== null && details !== null" class="">
    Poll details
    <h3>{{ poll.title }}</h3>
    <p>{{ poll.description }}</p>
    Number of answers: <b>{{ details.numberOfAnswers }}</b>

    <app-radar-chart :chartData="chartData"></app-radar-chart>
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
      chartData:
        [
          [
            {area: 'Alignemnt ', value: 3},
            {area: 'Tranparency', value: 2},
            {area: 'Openness ', value: 1},
            {area: 'Purpose ', value: 2}
          ]
        ]
    }
  },

  computed: {
    pollId () {
      return this.$route.params.pollId
    }
  },

  methods: {
    update () {
      if (this.pollId) {
        if (this.pollId !== '') {
          this.axios.get('/1/poll/' + this.pollId).then((response) => {
            if (response.data.result === 'success') {
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
    }
  },

  created () {
    this.update()
  }
}
</script>

<style lang="css">
</style>
