<template lang="html">
  <div class="w3-container content-container">
    <div class="w3-row content-title-div">
      <div class="w3-left">
        <h4><b>{{ $t('ORG-MEMBERS') }}</b></h4>
      </div>
    </div>

    <div class="w3-row-padding organization-members-row">
      <div v-for="member in this.members" :key="member.id" class="w3-row member-row">
        <div class="w3-left input-div dark-2">
          <div class="email-box w3-border app-color-2-br">
            {{ member.email }}
          </div>
        </div>
        <div class="w3-left btn-div">
          <app-plus-button class="remove-btn" @click="removeMember(member)"></app-plus-button>
        </div>
      </div>

      <div v-if="this.members.length === 0" class="w3-row w3-center">
        <i>{{ $t('NO-MEMBERS-EXIST')}}</i>
      </div>

      <div class="w3-row member-row new-member-row">
        {{ $t('ADD-NEW-MEMBER')}}
        <div class="w3-left input-div dark-2">
          <input v-model="newMemberEmail" placeholder="email" ref="newEmail"
            class="w3-input w3-round app-color-2-br" type="text" name="" >
        </div>
        <div class="w3-left btn-div">
          <app-plus-button @click="addMember()"></app-plus-button>
        </div>
      </div>

      <app-error-panel :show="emailNotValidError && !newEmailValid"
        :message="$t('EMAIL-NOT-VALID')">
      </app-error-panel>
    </div>

  </div>
</template>

<script>
export default {
  data () {
    return {
      members: [],
      newMemberEmail: '',
      emailNotValidError: false
    }
  },

  computed: {
    orgId () {
      return this.$route.params.orgId
    },
    newEmailValid () {
      var re = /^(([^<>()\]\\.,;:\s@"]+(\.[^<>()\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
      return re.test(String(this.newMemberEmail).toLowerCase())
    }
  },

  methods: {
    update () {
      this.axios.get('/1/organization/' + this.orgId + '/members').then((response) => {
        if (response.data.result === 'success') {
          this.members = response.data.data
        }
      })
    },
    addMember () {
      var ok = true
      if (this.newMemberEmail === '') {
        ok = false
      }

      if (!this.newEmailValid) {
        ok = false
        this.emailNotValidError = true
      }

      if (ok) {
        var memberDto = {
          email: this.newMemberEmail
        }
        this.axios.put('/1/organization/' + this.orgId + '/member', memberDto).then((response) => {
          if (response.data.result === 'success') {
            this.newMemberEmail = ''
            this.emailNotValidError = false
            this.update()
          }
        })
      }
    },
    removeMember (member) {
      this.axios.delete('/1/member/' + member.id).then((response) => {
        console.log(response.data)
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
          this.addMember()
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

.new-member-row {
  margin-top: 5vh;
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
