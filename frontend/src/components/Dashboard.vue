<template lang="html">
  <div class="">
    <app-new-org-modal
      v-if="newOrgModal"
      @close="newOrgModal = false">
    </app-new-org-modal>
    <div class="w3-row header-container w3-padding">
      <select v-model="orgIdSelected" class="" name="">
        <option v-for="org in orgs" :key="org.id" :value="org.id">{{ org.name }}</option>
      </select>
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
    <div class="w3-row middle-container">
      <app-organization-content :id="orgIdSelected"></app-organization-content>
    </div>
  </div>
</template>

<script>
import loggedUser from '@/mixins/loggedUser'
import NewOrgModal from '@/components/NewOrgModal'
import OrganizationContent from '@/components/OrganizationContent'

export default {
  mixins: [ loggedUser ],

  components: {
    'app-new-org-modal': NewOrgModal,
    'app-organization-content': OrganizationContent
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

  methods: {
  }
}
</script>

<style scoped>

.header-container {
  background-color: rgb(125, 100, 164);
}

.middle-container {
  background-color: cyan;
}

</style>
