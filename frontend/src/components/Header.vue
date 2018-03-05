<template lang="html">
  <div class="w3-row">
    <app-new-org-modal
      v-if="newOrgModal"
      @close="newOrgModal = false">
    </app-new-org-modal>
    <div class="w3-row header-container w3-padding">
      <select v-model="orgIdSelected" class="" name="">
        <option v-for="org in orgs" :key="org.id" :value="org.id">{{ org.name }}</option>
      </select>
      <router-link :to="{ name: 'OrganizationPolls', params: {} }">polls</router-link>
      <button @click="newOrgModal = true" type="button" name="button">+</button>
      <span class="">
        {{ userNickname }}
      </span>
      <button @click="logout()" type="button" name="button">logout</button>
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

<style lang="css">
</style>
