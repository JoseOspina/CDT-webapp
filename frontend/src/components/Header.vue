<template lang="html">
  <div class="w3-row header-container">
    <app-new-org-modal
      v-if="newOrgModal"
      @close="newOrgModal = false">
    </app-new-org-modal>
    <div class="w3-row w3-center header-container w3-padding">

      <div class="w3-left">
        <div class="w3-left w3-margin-right select-container">
          <select v-model="orgIdSelected" class="" name="">
            <option v-for="org in orgs" :key="org.id" :value="org.id">{{ org.name }}</option>
          </select>
        </div>
        <div @click="newOrgModal = true" class="w3-left circular-button cursor-pointer">
          <i class="fa fa-plus-circle" aria-hidden="true"></i>
        </div>
      </div>

      <router-link class="" :to="{ name: 'Organizations', params: {} }">
        <img class="logo" src="./../assets/logo-white.png" alt="">
      </router-link>

      <div class="w3-right user-container">
        <div class="w3-left w3-margin-right">
          {{ userNickname }}
        </div>
        <div class="w3-left cursor-pointer">
          <i @click="logout()" class="fa fa-sign-out" aria-hidden="true"></i>
        </div>
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
    }
  },

  watch: {
    orgIdSelected () {
      this.goToOrg()
    },
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
  min-height: 65px;
  color: white;
  font-size: 18px;
  /*background-image: -moz-linear-gradient(center bottom, rgb(14,173,173) 33%, rgb(0,255,255) 67% );*/
}

.logo {
  margin-top: 6px;
  height: 40px;
}

.circular-button {
  padding-top: 2px;
  font-size: 32px;
  color: #3F3E3E;
}

.circular-button:hover {
  color: #CCCCCC;
}

.select-container {
  padding-top: 12px;
  font-size: 16px;
}

.select-container select {
  min-width: 150px;
  background-color: #3F3E3E;
  border-radius: 16px;
  border-width: 0px;
  color: white;
  padding: 4px 16px;
}

.user-container {
  padding-top: 12px;
}

</style>
