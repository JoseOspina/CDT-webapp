<template lang="html">
  <div v-if="poll !== null" class="">
    <app-editable-poll v-model="poll" :fromTemplate="true"></app-editable-poll>
    <div class="w3-row button-row">
      <app-button @click="save()" class="w3-right">save</app-button>
      <app-button @click="$emit('cancel')" class="w3-right">cancel</app-button>
    </div>
  </div>
</template>

<script>
import EditablePoll from '@/components/EditablePoll'

export default {

  components: {
    'app-editable-poll': EditablePoll
  },

  props: {
    pollId: {
      type: String,
      default: ''
    }
  },

  data () {
    return {
      poll: null
    }
  },

  methods: {
    update () {
      if (this.pollId !== '') {
        this.axios.get('/1/poll/' + this.pollId).then((response) => {
          if (response.data.result === 'success') {
            this.poll = response.data.data
          }
        })
      }
    },

    save () {
      this.axios.put('/1/poll/' + this.pollId, this.poll).then((response) => {
        if (response.data.result === 'success') {
          this.$emit('edited')
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

.button-row {
  margin-top: 70px;
}

.button-row .w3-right {
  margin-left: 20px;
}

</style>
