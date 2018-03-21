<template lang="html">
  <div class="w3-container content-container">
    <div class="w3-row content-title-div">
      <div class="w3-left">
        <h4><b>{{ $t('ORG-MEMBERS') }}</b></h4>
      </div>
    </div>

    <div class="w3-row-padding organization-members-row">
      <div v-for="member in this.members" :key="member.id" class="w3-row member-row">
        {{ member.email }}
      </div>

      <div v-if="this.members.length === 0" class="w3-row w3-center w3-margin-top">
        <i>{{ $t('NO-MEMBERS-EXIST')}}</i>
      </div>

      <div class="w3-row member-row w3-margin-top">
        <div class="w3-left input-div dark-2">
          <input v-model="newMemberEmail" placeholder="email"
            class="w3-input w3-round app-color-2-br" type="text" name="" >
        </div>
        <div class="w3-left btn-div">
          <app-plus-button @click="addMember()"></app-plus-button>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
export default {
  data () {
    return {
      members: [],
      newMemberEmail: ''
    }
  },

  computed: {
    orgId () {
      return this.$route.params.orgId
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

      if (ok) {
        var memberDto = {
          email: this.newMemberEmail
        }
        this.axios.put('/1/organization/' + this.orgId + '/member', memberDto).then((response) => {
          if (response.data.result === 'success') {
            this.update()
          }
        })
      }
    }
  },

  mounted () {
    this.update()
  }
}
</script>

<style>

.organization-members-row {
  padding-top: 10vh;
  padding-left: 10vw;
  padding-right: 10vw;
}

.organization-members-row .input-div {
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

</style>
