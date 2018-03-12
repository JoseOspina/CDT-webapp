<template lang="html">
  <div class="poll-fill-container">

    <div v-if="axisIx == -1 || alreadyFilled || answerSucessful" class="w3-center title-page">
      <div class="central-column-container dark-1">
        <div class="w3-row title-container">
          <h1><b>{{ poll.title }}</b></h1>
          <p>{{ poll.description }}</p>
        </div>
        <div v-if="!alreadyFilled && !answerSucessful" class="w3-row start-button">
          <app-button @click="axisIx = 0" class="margin-0-auto">Start</app-button>
        </div>
        <div v-else class="w3-row rounded-input app-color-1-br w3-margin-top anouncement">
          {{ answerSucessful ? 'Your answer has been succesfully recored, thanks!' : 'Thanks for having filled the poll!' }}
        </div>
      </div>
    </div>
    <div v-else class="">
      <div class="w3-row">
        <app-back-button @click="back()" class="w3-hide-large"></app-back-button>
      </div>
      <div class="central-column-container dark-1">
        <div class="w3-row">
          <app-back-button @click="back()"
            class="w3-hide-medium w3-hide-small back-button-in-column">
          </app-back-button>
        </div>
        <app-answer-axis :key="poll.axes[axisIx].id" :axis="poll.axes[axisIx]" class="">
        </app-answer-axis>
        <div class="w3-row start-button">
          <app-button v-if="!isLast" @click="next()" class="margin-0-auto">Next</app-button>
          <app-button v-else @click="sendItentSet()" class="margin-0-auto">Send!</app-button>
        </div>
        <div v-if="sendIntent" class="w3-row w3-center">
          <div class="w3-center dark-3 check-text">
            Are you sure you want to submit your anwers now?
          </div>
          <div class="margin-0-auto" style="display: inline-block">
            <app-button @click="sendItentReset()" class="w3-left w3-margin-right">Cancel</app-button>
            <app-button @click="send()" class="w3-left">Confirm!</app-button>
          </div>

        </div>
      </div>
    </div>

    <div v-if="alreadyFilled && !answerSucessful" class="">

    </div>
    <div v-if="answerSucessful" class="">

    </div>
  </div>
</template>

<script>
import Axis from '@/components/answerpoll/Axis'

export default {
  components: {
    'app-answer-axis': Axis
  },

  props: {
    poll: {
      type: Object
    }
  },

  data () {
    return {
      alreadyFilled: false,
      axisIx: -1,
      answerSucessful: false,
      answerError: false,
      answerErrorMsg: '',
      sendIntent: false
    }
  },

  computed: {
    filledCookieId () {
      return 'POLL-' + this.poll.id
    },
    filledValue () {
      return 'FILLED'
    },
    isLast () {
      return this.axisIx === this.poll.axes.length - 1
    }
  },

  methods: {
    next () {
      if (this.axisIx < this.poll.axes.length - 1) {
        this.axisIx += 1
      }
    },
    back () {
      this.axisIx -= 1
    },
    sendItentReset () {
      this.sendIntent = false
    },
    sendItentSet () {
      this.sendIntent = true
    },
    send () {
      this.axios.post('/1/poll/' + this.poll.id + '/answer', this.$store.state.answerpoll.answers).then((response) => {
        if (response.data.result === 'success') {
          this.answerSucessful = true
          this.$cookie.set(this.filledCookieId, this.filledValue, 365)
        } else {
          this.answerError = true
          this.answerErrorMsg = response.data.message
        }
      }).catch((error) => {
        this.answerError = true
        this.answerErrorMsg = error.message
      })
    }
  },

  created () {
    if (this.$cookie.get(this.filledCookieId) === this.filledValue) {
      this.alreadyFilled = true
    }
  }
}
</script>

<style scoped>

.start-button {
  margin-top: 10vh;
}

.title-page .w3-row:nth-child(2) {
  margin-top: 85px;
}

.title-container {
  padding: 85px 65px 20px 65px;
}

.app-button-centered {
  margin-top: 45px;
}

.poll-fill-container {
  margin: 0 auto;
}

.check-text {
  padding: 24px 36px;
  margin-top: 22px;
  margin-bottom: 22px;
}

</style>
