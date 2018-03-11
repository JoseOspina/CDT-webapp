<template lang="html">
  <div class="w3-container content-container this-container">

    <app-new-org-modal
      v-if="newOrgModal"
      @close="newOrgModal = false">
    </app-new-org-modal>

    <div class="w3-row">
      <div class="w3-left content-title-div">
        <h4><b>My Organizations</b></h4>
      </div>
      <app-plus-button class="w3-left w3-margin-left" @click="newOrgModal = true"></app-plus-button>
    </div>
    <div class="w3-row-padding middle-container">
      <div v-for="organization in organizations" :key="organization.id"
        class="w3-col l4 m6 s12 w3-margin-bottom app-card-container">
        <router-link class="w3-card-4 app-card disp-block"
          :to="{ name: 'OrganizationPolls', params: {orgId: organization.id} }">
          <h4><b>{{ organization.name }}</b></h4>
          <p>{{ organization.description }}</p>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
import NewOrgModal from '@/components/NewOrgModal'

export default {

  components: {
    'app-new-org-modal': NewOrgModal
  },

  data () {
    return {
      newOrgModal: false
    }
  },

  computed: {
    organizations () {
      return this.$store.getters.organizations
    }
  }
}
</script>

<style scoped>

.this-container {
  background: url('./../assets/background-3.png') center;
  background-color: #3F3E3E;
}

.middle-container {
  padding-top: 20px;
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
