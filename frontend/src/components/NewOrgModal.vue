<template lang="html">
  <div class="w3-modal">
    <div class="w3-modal-content dark-3">
      <div class="w3-card-4 dark-3">

        <div class="close-div w3-display-topright w3-xlarge cursor-pointer app-color-white-1" @click="closeThis()">
          <i class="fa fa-times" aria-hidden="true"></i>
        </div>

        <div class="w3-border-bottom">
          <h2>{{ $t('CREATE-NEW-ORG') }}</h2>
        </div>

        <div class="form-container">

          <div class="w3-container form ">
            <div class="w3-row">
              <label for=""><b>{{ $t('NAME') }}:</b></label>
              <input v-model="name" class="rounded-input app-color-2-br dark-3" type="text" name="" value="">
            </div>
            <app-error-panel
              :show="name === '' && showErrors"
              :message="$t('TITLE-NOT-EMPTY')">
            </app-error-panel>
            <div class="w3-row w3-margin-top">
              <label for=""><b>{{ $t('DESCRIPTION') }}:</b></label>
              <app-markdown-editor v-model="description" class="rounded-input app-color-2-br dark-3">
              </app-markdown-editor>
            </div>
          </div>

          <div class="w3-row-padding bottom-btns-row">
            <div class="w3-col m6">
              <button type="button" class="w3-button app-button-light"
                @click="closeThis()">
                {{ $t('CANCEL') }}
              </button>
            </div>
            <div class="w3-col m6">
              <button type="button" class="w3-button app-button"
                @click="accept()">
                {{ $t('ACCEPT') }}
              </button>
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
      errorWhenCreatingMsg: '',
      showErrors: false
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
      var ok = true

      if (this.name === '') {
        ok = false
        this.showErrors = true
      }

      if (ok) {
        var orgDto = {
          name: this.name,
          description: this.description
        }
        this.axios.post('/1/organization/create', orgDto).then((response) => {
          if (response.data.result === 'success') {
            this.$store.dispatch('updateProfile')
            this.closeThis()
            console.log('og created:' + response.data.data)
            console.log(response)
            this.$router.push({name: 'OrganizationPolls', params: {orgId: response.data.elementId}})
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
}
</script>

<style scoped>

.w3-modal {
  display: block;
}

.w3-modal-content {
  border-radius: 22px;
}

.w3-modal-content .w3-card-4 {
  border-radius: 22px;
  padding: 22px 36px;
}

.close-div {
  margin-right: 10px;
}

.form-container {
  padding-top: 22px;
}

.button-bar .w3-bar-item {
  width: 110px;
  font-size: 12px;
}

.bottom-btns-row {
  margin-top: 22px;
}

.bottom-btns-row button {
  width: 100%;
}

</style>
