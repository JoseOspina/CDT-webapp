<template lang="html">
  <div class="w3-container content-container this-container">

    <router-link :to="{ name: fromTemplate ? 'NewPollTemplate' : 'OrganizationPolls'}"
      class="w3-row disp-block w3-margin-bottom w3-hide-large">
      <app-back-button class=""></app-back-button>
    </router-link>

    <div class="central-column-container dark-1">

      <router-link :to="{ name: fromTemplate ? 'NewPollTemplate' : 'OrganizationPolls'}"
        class="w3-hide-medium w3-hide-small back-button-in-column">
        <app-back-button class=""></app-back-button>
      </router-link>

      <div class="w3-row column-header">
        <app-column-header>
          {{ $t('NEW-POLL')}} - <i>{{ $t('CUSTOMIZATION') }}</i>
        </app-column-header>
      </div>

      <app-editable-poll
        v-model="poll"
        :pollG="pollG"
        :fromTemplate="fromTemplate"
        :showErrors="showErrors">
      </app-editable-poll>

      <div class="w3-row">
        <app-error-panel :show="showErrorsBottom"
          :message="$t('ERRORS-IN-POLL')">
        </app-error-panel>
        <app-button @click="next()" class="w3-right">{{ $t('NEXT') }}</app-button>
      </div>
    </div>
  </div>
</template>

<script>
import EditablePoll from '@/components/EditablePoll'
import editablePollChecks from '@/mixins/editablePollChecks.js'

export default {

  mixins: [ editablePollChecks ],

  components: {
    'app-editable-poll': EditablePoll
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
      showErrors: false,
      showErrorsBottom: false
    }
  },

  methods: {
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

        for (var ixQuest in axis.questions) {
          var question = axis.questions[ixQuest]
          if (question.text === '') {
            ok = false
          }
        }

        if (!this.axisWeightsOk(axis.id)) {
          ok = false
        }

        if (!this.axisHasARate(axis.id)) {
          ok = false
        }
      }

      if (ok) {
        this.$store.commit('setNewPoll', this.poll)
        this.$store.commit('setEditingNewPoll', true)

        this.$router.push({name: 'NewPollConfiguration'})
      } else {
        this.showErrors = true
        this.showErrorsBottom = true
        setTimeout(() => {
          this.showErrorsBottom = false
        }, 3000)
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

.add-question-row > span, .new-axis-row > span {
  font-size: 18px;
  padding-top: 6px;
  padding-right: 22px;
}

</style>
