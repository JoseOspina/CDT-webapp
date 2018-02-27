<template lang="html">
  <div class="w3-modal">
    <div class="w3-modal-content">
      <div class="w3-card-4">

        <div class="close-div w3-display-topright w3-xlarge" @click="closeThis()">
          <i class="fa fa-times" aria-hidden="true"></i>
        </div>

        <div class="w3-border-bottom">
          <h2>Create New Organization</h2>
        </div>

        <div class="form-container">

          <div class="w3-container form ">
            <div class="w3-row">
              <label for=""><b>Name:</b></label>
              <input v-model="name" class="w3-input w3-border" type="text" name="" value="">
            </div>
            <div class="">
              <label for=""><b>Description:</b></label>
              <textarea v-model="description" class="w3-input w3-border"></textarea>
            </div>
          </div>

          <div class="w3-row bottom-btns-row">
            <div class="w3-col m6">
              <button type="button" class="w3-button app-button-light" @click="closeThis()">Cancel</button>
            </div>
            <div class="w3-col m6">
              <button type="button" class="w3-button app-button" @click="accept()">Accept</button>
            </div>
          </div>

          <app-error-panel
            :show="errorWhenCreating"
            :message="errorWhenCreatingMsg">
          </app-error-panel>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      name: '',
      description: '',
      errorWhenCreating: false,
      errorWhenCreatingMsg: ''
    }
  },

  computed: {
  },

  mounted () {
  },

  methods: {
    closeThis () {
      this.$emit('close')
    },
    accept () {
      var orgDto = {
        name: this.name,
        description: this.description
      }
      this.axios.post('/1/organization/create', orgDto).then((response) => {
        if (response.data.result === 'success') {
          this.closeThis()
        } else {
          console.log(response.data)
          this.errorWhenCreating = true
          this.errorWhenCreatingMsg = response.data.message
        }
      }).catch((error) => {
        console.log(error)
        this.errorWhenCreating = true
        this.errorWhenCreatingMsg = error.message
      })
    }
  }
}
</script>

<style scoped>

.button-bar .w3-bar-item {
  width: 110px;
  font-size: 12px;
}

.bottom-btns-row button {
  width: 100%;
}

</style>
