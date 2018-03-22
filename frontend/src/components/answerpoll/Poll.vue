<template lang="html">
  <div class="poll-fill-container">

    <div v-if="secretValid" class="">
      <div v-if="axisIx == -1 || alreadyFilled || answerSucessful" class="title-page">
        <div class="central-column-container dark-1">
          <div class="w3-row title-container">
            <h1><b>{{ poll.title }}</b></h1>
            <vue-markdown class="marked-text" :source="poll.description"></vue-markdown>
          </div>
          <div v-if="!alreadyFilled && !answerSucessful" class="w3-row start-button">
            <app-button @click="start()" class="margin-0-auto">{{ $t('START') }}</app-button>
          </div>
          <div v-else class="w3-row rounded-input app-color-1-br w3-margin-top anouncement">
            {{ answerSucessful ? $t('ANSWERS-SUCCESS') : $t('ALREADY-FILLED') }}
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
            <app-button v-if="!isLast" @click="next()" class="margin-0-auto">{{ $t('NEXT') }}</app-button>
            <app-button v-else @click="sendItentSet()" class="margin-0-auto">{{ $t('SEND') }}!</app-button>
          </div>
          <div v-if="sendIntent" class="w3-row w3-center">
            <div class="w3-center dark-3 check-text">
              {{ $t('ARE-YOU-SURE-SUBMIT') }}
            </div>
            <div class="margin-0-auto" style="display: inline-block">
              <app-button @click="sendItentReset()" class="w3-left w3-margin-right">{{ $t('CANCEL') }}</app-button>
              <app-button @click="send()" class="w3-left">{{ $t('CONFIRM') }}!</app-button>
            </div>

          </div>
        </div>
      </div>
    </div>
    <div v-else class="w3-center">
      <div v-if="checkingSecret" class="">
        <h3>{{ $t('PLEASE-WAIT') }} ...</h3>
      </div>
      <div v-else class="">
        <h3>{{ $t('NOT-AUTHORIZED-SURVEY') }}</h3>
      </div>
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
      sendIntent: false,
      secretCheckedOk: false,
      checkingSecret: false
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
    },
    secretValid () {
      return this.poll.config.audience === 'ANYONE_WITH_LINK' || this.secretCheckedOk
    }
  },

  methods: {
    start () {
      this.axisIx = 0
      this.scrollToTop()
    },
    next () {
      if (this.axisIx < this.poll.axes.length - 1) {
        this.axisIx += 1
        this.scrollToTop()
      }
    },
    scrollToTop () {
      document.body.scrollTop = 0
      document.documentElement.scrollTop = 0
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
    checkSecret () {
      this.checkingSecret = true
      this.axios.get('/1/poll/' + this.poll.id + '/secretValid/' + this.$route.query.secret).then((response) => {
        if (response.data.result === 'success') {
          this.checkingSecret = false
          this.secretCheckedOk = response.data.data
        }
      })
    },
    send () {
      var params = {}
      if (this.poll.config.audience === 'ANY_MEMBER') {
        params = {
          secret: this.$route.query.secret
        }
      }

      this.axios.post('/1/poll/' + this.poll.id + '/answer', this.$store.state.answerpoll.answers, { params: params }).then((response) => {
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
    if (this.poll.config.audience === 'ANY_MEMBER') {
      this.checkSecret()
    }
  }
}
</script>

<style scoped>

.start-button {
  margin-top: 10vh;
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
