<template lang="html">
  <div class="w3-modal">
    <div class="w3-modal-content dark-3">
      <div class="w3-card-4 dark-3">

        <div class="close-div w3-display-topright w3-xlarge cursor-pointer app-color-white-1" @click="closeThis()">
          <i class="fa fa-times" aria-hidden="true"></i>
        </div>

        <div class="w3-border-bottom">
          <h2>{{ $t('DELETE-ORG') }}</h2>
        </div>

        <div class="form-container">

          <div class="w3-container form ">
            {{ $t('DELETE-ORG-MSG') }}
            {{ organization.name }}?
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
            :show="errorFlag"
            :message="errorMsg">
          </app-error-panel>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    organization: {
      type: Object,
      default: null
    }
  },
  data () {
    return {
      errorFlag: false,
      errorMsg: ''
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
      this.axios.delete('/1/organization/delete/' + this.organization.id).then((response) => {
        if (response.data.result === 'success') {
          this.$store.dispatch('updateProfile')
          this.closeThis()
        } else {
          this.errorFlag = true
          this.errorMsg = response.data.message
        }
      }).catch((error) => {
        this.errorFlag = true
        this.errorMsg = error.message
      })
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
