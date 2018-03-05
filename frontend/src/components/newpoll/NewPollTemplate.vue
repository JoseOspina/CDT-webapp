<template lang="html">
  <div class="w3-container">
    <h3>select a template</h3>
    <div v-for="template in templates" :key="template.id" @click="selectTemplate(template)" class="w3-card-2 w3-margin-top w3-padding" :class="{'w3-blue': isSelected(template)}">
      <h1>{{ template.title }}</h1>
      <p>{{ template.description }}</p>
    </div>

    <button @click="next()" class="w3-row w3-margin-top" type="button" name="button" :disabled="selected == null">next</button>
    <button @click="skip()" class="w3-row w3-margin-top" type="button" name="button">or skip</button>
  </div>
</template>

<script>
import { getEmptyPoll } from '@/support/newPollEmptyElements'
export default {
  computed: {
    orgId () {
      return this.$route.params.orgId
    }
  },

  data () {
    return {
      templates: [],
      selected: null,
      errorFlag: false,
      errorMsg: ''
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
