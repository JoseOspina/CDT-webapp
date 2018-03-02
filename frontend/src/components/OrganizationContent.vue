<template lang="html">
  <div class="">
    <router-view></router-view>
  </div>
</template>

<script>
import loggedUser from '@/mixins/loggedUser'

export default {
  mixins: [ loggedUser ],

  props: {
  },

  data () {
    return {
    }
  },

  methods: {
  },

  created () {
  }

}
</script>

<style lang="css">
</style>
