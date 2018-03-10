<template lang="html">
  <div class="w3-container this-container content-container">
    <router-link :to="{ name: 'OrganizationPolls'}"
      class="w3-row disp-block w3-margin-bottom w3-hide-large">
      <app-back-button class=""></app-back-button>
    </router-link>

    <div class="central-column-container dark-1">

      <router-link :to="{ name: 'OrganizationPolls'}"
        class="w3-hide-medium w3-hide-small back-button-in-column">
        <app-back-button class=""></app-back-button>
      </router-link>

      <div class="w3-row">
        <app-column-header>
          New Poll - <i>Select a Template</i>
        </app-column-header>
      </div>

      <div v-for="template in templates" :key="template.id"
        @click="selectTemplate(template)"
        class="w3-card-4 app-card disp-block w3-margin-top cursor-pointer" :class="{'w3-blue': isSelected(template)}">
        <h1>{{ template.title }}</h1>
        <p>{{ template.description }}</p>
      </div>

      <app-error-panel
        :show="showErrors && !isTemplateSelected"
        message="no template has been selected">
      </app-error-panel>

      <div class="w3-row w3-margin-top">
        <app-button @click="next()" class="w3-right w3-margin-left">Next</app-button>
        <app-button @click="skip()" class="w3-right">Skip</app-button>
      </div>

    </div>
  </div>
</template>

<script>
import { getEmptyPoll } from '@/support/newPollEmptyElements'
export default {
  data () {
    return {
      templates: [],
      selected: null,
      errorFlag: false,
      errorMsg: '',
      showErrors: false
    }
  },

  computed: {
    orgId () {
      return this.$route.params.orgId
    },
    isTemplateSelected () {
      return this.selected !== null
    }
  },

  methods: {
    selectTemplate (template) {
      this.selected = template
    },
    isSelected (template) {
      if (this.selected == null) {
        return false
      } else {
        return template.id === this.selected.id
      }
    },
    next () {
      var ok = true

      if (!this.isTemplateSelected) {
        this.showErrors = true
        ok = false
      }

      if (ok) {
        this.axios.get('/1/poll/' + this.selected.id).then((response) => {
          if (response.data.result === 'success') {
            this.$store.commit('setFromTemplate', true)
            this.$store.commit('setNewPoll', response.data.data)
            this.$router.push({name: 'NewPollQuestions'})
          } else {
            this.errorFlag = true
            this.errorMsg = response.data.message
          }
        })
      }
    },
    skip () {
      this.$store.commit('setFromTemplate', false)
      this.$store.commit('setNewPoll', getEmptyPoll())
      this.$router.push({name: 'NewPollQuestions'})
    }
  },

  created () {
    this.axios.get('/1/organization/' + this.orgId + '/templates').then((response) => {
      if (response.data.result === 'success') {
        this.templates = response.data.data
      } else {
        this.errorFlag = true
        this.errorMsg = response.data.message
      }
    })
  }
}
</script>

<style lang="css">
</style>
