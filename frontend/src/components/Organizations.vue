<template lang="html">
  <div class="w3-container content-container this-container">
    <div class="w3-row">
      <h4><b>My Organizations</b></h4>
    </div>
    <div class="w3-row-padding middle-container">
      <div v-for="organization in organizations" :key="organization.id"
        class="w3-col l4 m6 s12 w3-margin-bottom app-card-container">
        <router-link class="w3-card-4 app-card disp-block"
          :to="{ name: 'OrganizationPolls', params: {orgId: organization.id} }">
          <b>{{ organization.name }}</b>
          {{ organization.description }}
        </router-link>
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

.this-container {
  background: url('./../assets/background-3.png') center;
  background-color: #3F3E3E;
}

.middle-container {
  padding-top: 20px;
}

</style>
