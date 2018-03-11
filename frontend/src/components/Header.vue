<template lang="html">
  <div class="w3-row header-container">
    <app-new-org-modal
      v-if="newOrgModal"
      @close="newOrgModal = false">
    </app-new-org-modal>
    <div class="w3-row header-container">

      <div class="w3-col m3 s12">
        <router-link v-if="showHome" :to="{ name: 'OrganizationContent', params: {} }"
          class="circular-button w3-center cursor-pointer app-color-white-1 disp-block">
          <i class="fa fa-home" aria-hidden="true"></i>
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
        :message="$t('organizationCreationErrorMessage')">
      </app-error-panel>
    </div>
  </div>
</template>

<script>
import loggedUser from '@/mixins/loggedUser'
import NewOrgModal from '@/components/NewOrgModal'

export default {
  mixins: [ loggedUser ],

  components: {
    'app-new-org-modal': NewOrgModal
  },

  data () {
    return {
      organizationCreationError: false,
      newOrgModal: false,
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

.header-container {
  background: linear-gradient(to right, #FFDE17, #009B9C, #009B9C);
  min-height: 70px;
  color: white;
  font-size: 18px;
}

.w3-col {
  min-height: 1px;
}

.logo {
  margin-top: 16px;
  height: 40px;
}

.circular-button {
  padding-top: 8px;
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
  padding-top: 20px;
  padding-right: 12px;
}

.user-container .fa {
  font-size: 32px;
}

</style>
