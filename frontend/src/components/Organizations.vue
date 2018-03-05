<template lang="html">
  <div class="">
    <div class="w3-row-padding middle-container">
      <div v-for="organization in organizations" class="w3-card-2 w3-col m4 w3-padding w3-margin-bottom w3-margin-left">
        <router-link :to="{ name: 'OrganizationPolls', params: {orgId: organization.id} }">{{ organization.name }}</router-link>
        {{ organization.description }}
      </div>
    </div>
  </div>
</template>

<script>
export default {
  computed: {
    organizations () {
      return this.$store.getters.organizations
    }
  }
}
</script>

<style scoped>
</style>
