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

      <div class="w3-row column-header">
        <app-column-header>
          {{ $t('NEW-POLL') }} - <i>{{ $t('SELECT-TEMPLATE') }}</i>
        </app-column-header>
      </div>

      <div class="w3-row">
        <div class="buttons-container">
          <div @click="searchPublic = !searchPublic" class="w3-left w3-padding fa-container cursor-pointer">
            <i class="fa fa-circle rating-dot" :class="{'light-co': searchPublic}" aria-hidden="true"></i>
          </div>
          <div class="w3-left w3-margin-left text">{{ $t('SEARCH-PUBLIC-TEMPLATES')}}</div>
        </div>
      </div>

      <div class="templates-container app-color-2-br">
        <div v-for="template in templates" :key="template.id"
          @click="selectTemplate(template)"
          class="card-template cursor-pointer" :class="{'card-template-selected': isSelected(template)}">
          <h4>{{ template.title }}</h4>
          <vue-markdown class="marked-text" :source="template.description.length < 200 ? template.description : template.description.slice(0,200) + ' ...'"></vue-markdown>
        </div>
      </div>

      <app-error-panel
        :show="showErrors && !isTemplateSelected"
        :message="$t('NO-TEMP-SELECTED')">
      </app-error-panel>

      <div class="w3-row bottom-row">
        <app-button @click="next()" class="w3-right w3-margin-left">{{ $t('NEXT') }}</app-button>
        <app-button @click="skip()" class="w3-right">{{ $t('SKIP') }}</app-button>
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
      showErrors: false,
      searchPublic: false
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

  watch: {
    searchPublic () {
      this.update()
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
            var pollData = response.data.data
            pollData.templateId = pollData.id
            this.$store.commit('setNewPoll', pollData)
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
    },
    update () {
      this.axios.get('/1/organization/' + this.orgId + '/templates', { params: { searchPublic: this.searchPublic } }).then((response) => {
        if (response.data.result === 'success') {
          this.templates = response.data.data
        } else {
          this.errorFlag = true
          this.errorMsg = response.data.message
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

.buttons-container {
  width: 100%;
  margin: 0 auto;
}

.buttons-container .fa-container {
  width: 50px;
}

.buttons-container .text {
  padding-top: 7px;
  width: calc(100% - 70px);
}

.templates-container {
  margin-top: 30px;
  border-style: solid;
  border-radius: 3px;
  padding: 12px 12px;
  height: 60vh;
  overflow-y: auto;
}

.card-template {
  background-color: rgba(255, 255, 255, 0.23);
  border-radius: 12px;
  padding: 12px 18px;
  margin-bottom: 30px;
}

.card-template-selected {
  background-color: #009B9C;
}

</style>
