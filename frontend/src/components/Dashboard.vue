<template lang="html">
  <div class="">
    <div class="w3-row header-container w3-padding">
      <select class="" name="">
        <option value="id1">My Organization</option>
        <option value="id1">My Other Organization</option>
      </select>
      <button type="button" name="button">+</button>
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
      CONTENT
    </div>
  </div>
</template>

<script>
import loggedUser from '@/mixins/loggedUser'

export default {
  mixins: [ loggedUser ],

  data () {
    return {
      organizationCreationError: false
    }
  },

  methods: {
    createOrganization () {
      this.axios.post('/1/organization/create').then((response) => {
        if (response.data.reuslt !== 'success') {
          this.organizationCreationError = true
        }
      })
    }
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
