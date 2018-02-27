<template lang="html">
  <div class="">
    <h1>Organization content</h1>
  </div>
</template>

<script>
import loggedUser from '@/mixins/loggedUser'

export default {
  mixins: [ loggedUser ],

  props: {
    id: {
      type: String
    }
  },

  data () {
    return {
      organization: null
    }
  },

  watch: {
    id () {
      this.update()
    }
  },

  methods: {
    update () {
      if (this.id !== '') {
        this.axios.get('/1/organization/' + this.id).then((response) => {
          if (response.data.result === 'success') {
            this.organization = response.data.data
          }
        })
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
