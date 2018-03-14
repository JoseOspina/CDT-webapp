<template lang="html">
  <div class="w3-row">
    <div class="w3-row header-container w3-card-2">

      <div class="w3-col m3 s12">
        <router-link v-if="showHome" :to="{ name: 'OrganizationContent', params: {} }"
          class="circular-button w3-center cursor-pointer app-color-white-1 disp-block">
          <i class="fa fa-home" aria-hidden="true"></i><span> {{ orgName }}</span>
        </router-link>
      </div>

      <div class="w3-col m6 s12 w3-center">
        <img class="logo" src="./../assets/logo-white.png" alt="">
      </div>

      <div class="w3-col m3 s12 user-container">
        <div class="w3-right cursor-pointer app-color-white-1">
          <i @click="logout()" class="fa fa-sign-out" aria-hidden="true"></i>
        </div>
        <router-link :to="{ name: 'Organizations', params: {} }"
          class="w3-right w3-margin-right cursor-pointer app-color-white-1 disp-block">
          {{ userNickname }}
        </router-link>
      </div>
    </div>
    <div class="w3-row w3-center">
      <app-error-panel
        :show='organizationCreationError'
        :message="$t('HEADER-ORG-CREATION')">
      </app-error-panel>
    </div>
  </div>
</template>

<script>
import loggedUser from '@/mixins/loggedUser'

export default {
  mixins: [ loggedUser ],

  data () {
    return {
      organizationCreationError: false,
      orgIdSelected: ''
    }
  },

  computed: {
    orgs () {
      return this.$store.getters.organizations
    },
    showHome () {
      if (this.$route.params.orgId) {
        return true
      }
    },
    orgName () {
      if (this.$store.state.user.profile) {
        for (var ix in this.$store.state.user.profile.organizations) {
          if (this.$store.state.user.profile.organizations[ix].id === this.$route.params.orgId) {
            return this.$store.state.user.profile.organizations[ix].name
          }
        }
      }
      return ''
    }
  },

  watch: {
    '$route' () {
      if (this.$route.params) {
        if (this.$route.params.orgId !== '') {
          this.orgIdSelected = this.$route.params.orgId
        } else {
          this.orgIdSelected = ''
        }
      } else {
        this.orgIdSelected = ''
      }
    }
  },

  methods: {
    goToOrg () {
      if (this.orgIdSelected) {
        if (this.orgIdSelected !== '') {
          console.log('redirecting to organization content id: ' + this.orgIdSelected)
          this.$router.push({name: 'OrganizationContent', params: {orgId: this.orgIdSelected}})
        }
      }
    }
  },

  created () {
    if (this.$route.params) {
      if (this.$route.params.orgId !== '') {
        this.orgIdSelected = this.$route.params.orgId
      }
    }
  }
}
</script>

<style scoped>

.w3-col {
  min-height: 1px;
}

.circular-button {
  padding-top: 21px;
}

.circular-button .fa {
  font-size: 32px;
}

.select-container {
  padding-top: 18px;
  padding-left: 18px;
  font-size: 16px;
  width: 70%;
}

.select-container select {
  width: 100%;
}

.user-container {
  padding-top: 23px;
  padding-right: 12px;
}

.user-container .fa {
  font-size: 32px;
}

</style>
