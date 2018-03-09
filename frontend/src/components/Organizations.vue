<template lang="html">
  <div class="w3-container content-container this-container">
    <div class="w3-row">
      <h4><b>My Organizations</b></h4>
    </div>
    <div class="w3-row-padding middle-container">
      <router-link v-for="organization in organizations" :key="organization.id"
        :to="{ name: 'OrganizationPolls', params: {orgId: organization.id} }"
        class="w3-card-2 w3-col m4 w3-padding w3-margin-bottom w3-margin-left app-card">
        <b>{{ organization.name }}</b>
        {{ organization.description }}
      </router-link>

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

.this-container {
  background: url('./../assets/background-3.png') center left / cover no-repeat;
  background-color: #3F3E3E;
}

.app-card {
  background-color: white;
  border-radius: 16px;
  color: #2F2F2F;
}

</style>
