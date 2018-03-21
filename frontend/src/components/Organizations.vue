<template lang="html">
  <div class="w3-container content-container">

    <app-new-org-modal
      v-if="newOrgModal"
      @close="newOrgModal = false">
    </app-new-org-modal>

    <app-delete-org-modal
      v-if="deleteOrgModal"
      :organization="organizationToDelete"
      @close="deleteOrgModal = false">
    </app-delete-org-modal>

    <div class="w3-row">
      <div class="w3-left content-title-div">
        <h5><b>{{ $t('MY-ORGS')}}</b></h5>
      </div>
      <app-plus-button class="w3-left w3-margin-left" @click="newOrgModal = true"></app-plus-button>
    </div>
    <div class="w3-row-padding middle-container">
      <div v-for="organization in organizations" :key="organization.id"
        class="app-card-container w3-display-container">
        <router-link class="w3-card-4 app-card disp-block"
          :to="{ name: 'OrganizationPolls', params: {orgId: organization.id} }">
          <h4><b>{{ organization.name }}</b></h4>
          <vue-markdown class="marked-text" :source="organization.description.length < 60 ? organization.description : organization.description.slice(0, 59) + ' ...'"></vue-markdown>
        </router-link>
        <div @click="deleteOrg(organization)" class="dark-2-co delete-icon w3-display-bottomright cursor-pointer">
          <i class="fa fa-trash" aria-hidden="true"></i>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import NewOrgModal from '@/components/NewOrgModal'
import DeleteOrgModal from '@/components/DeleteOrgModal'

export default {

  components: {
    'app-new-org-modal': NewOrgModal,
    'app-delete-org-modal': DeleteOrgModal
  },

  data () {
    return {
      newOrgModal: false,
      deleteOrgModal: false,
      organizationToDelete: null
    }
  },

  computed: {
    organizations () {
      return this.$store.getters.organizations
    }
  },

  methods: {
    deleteOrg (organization) {
      this.organizationToDelete = organization
      this.deleteOrgModal = true
    }
  }
}
</script>

<style scoped>

.delete-icon {
  padding: 3px 12px 2px 12px;
  font-size: 22px;
  border-bottom-right-radius: 16px;
  border-bottom-right-radius: 16px;
}

.delete-icon:hover {
  background-color: #009B9C;
}

.middle-container {
  padding-top: 10vh;
  padding-left: 10vw;
  padding-right: 10vw;
}

.new-org-container {
  font-size: 18px;
}

.new-org-container > div {
  display: inline-block;
  vertical-align: text-top;
}

.new-org-container div:nth-child(1) {
}

</style>
