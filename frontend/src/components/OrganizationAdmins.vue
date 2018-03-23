<template lang="html">
  <div class="w3-container content-container">
    <div class="w3-row content-title-div">
      <div class="w3-left">
        <h4><b>{{ $t('ORG-ADMINS') }}</b></h4>
      </div>
    </div>

    <div class="w3-row-padding organization-members-row">

      <div class="new-member-row">
        <div class="w3-row member-row">
          {{ $t('ADD-NEW-ADMIN')}}
          <div class="w3-left input-div dark-2">
            <input v-model="newAdminEmail" placeholder="email" ref="newEmail"
              class="w3-input w3-round app-color-2-br" type="text" name="" >
          </div>
          <div class="w3-left btn-div">
            <app-plus-button @click="addAdmin()"></app-plus-button>
          </div>
        </div>

        <app-error-panel :show="addAdminError"
          :message="addAdminMsg">
        </app-error-panel>
      </div>

      <div v-for="admin in this.admins" :key="admin.id" class="w3-row member-row">
        <div class="w3-left input-div dark-2">
          <div class="email-box w3-border app-color-2-br">
            {{ admin.email }}
          </div>
        </div>
        <div class="w3-left btn-div">
          <app-plus-button class="remove-btn" @click="removeAdmin(admin)"></app-plus-button>
        </div>
      </div>

      <app-error-panel :show="removeAdminError"
        :message="removeAdminMsg">
      </app-error-panel>

    </div>

  </div>
</template>

<script>
export default {
  data () {
    return {
      admins: [],
      newAdminEmail: '',
      addAdminError: false,
      addAdminMsg: '',
      removeAdminError: false,
      removeAdminMsg: ''
    }
  },

  computed: {
    orgId () {
      return this.$route.params.orgId
    }
  },

  methods: {
    update () {
      this.axios.get('/1/organization/' + this.orgId + '/admins').then((response) => {
        if (response.data.result === 'success') {
          this.admins = response.data.data
        }
      })
    },
    addAdmin () {
      var ok = true
      if (this.newAdminEmail === '') {
        ok = false
      }

      if (ok) {
        var adminDto = {
          email: this.newAdminEmail
        }
        this.axios.put('/1/organization/' + this.orgId + '/admin', adminDto).then((response) => {
          console.log(response.data)
          if (response.data.result === 'success') {
            this.newAdminEmail = ''
            this.update()
          } else {
            this.addAdminError = true
            this.addAdminMsg = response.data.message
          }
        })
      }
    },
    removeAdmin (admin) {
      if (this.admins.length === 1) {
        this.removeAdminError = true
        this.removeAdminMsg = 'Cannot delete all admins of an organization'
        return
      }
      this.axios.delete('/1/organization/' + this.orgId + '/admin/' + admin.id).then((response) => {
        if (response.data.result === 'success') {
          this.update()
        }
      })
    },
    atKeydown (e) {
      if (this.$refs.newEmail === document.activeElement) {
        /* enter */
        if (e.keyCode === 13) {
          e.preventDefault()
          this.addAdmin()
        }
      }
    }
  },

  mounted () {
    this.update()
    window.addEventListener('keydown', this.atKeydown)
  },

  destroyed () {
    window.removeEventListener('keydown', this.atKeydown)
  }
}
</script>

<style>

.organization-members-row {
  padding-top: 5vh;
  padding-left: 10vw;
  padding-right: 10vw;
}

.organization-members-row .new-member-row {
  margin-bottom: 5vh;
}

.member-row {
  margin-bottom: 15px;
}

.member-row .input-div {
  width: calc(100% - 50px);
  padding-right: 20px;
}

.input-div input {
  border-style: solid;
  border-width: 2px;
  background-color: #2F2F2F;
  color: white;
}

.organization-members-row .btn-div {
  padding-top: 3px;
  width: 50px;
}

.organization-members-row .remove-btn {
  transform: rotate(45deg);
}

.email-box {
  min-height: 40px;
  padding: 6px 12px;
  border-radius: 3px;
  border-width: 2px !important;
}

</style>
