<template lang="html">
  <div class="">
    <h1>Dashboard</h1>
    <button v-if="!isAuthenticated" @click="login()" class="w3-button w3-blue w3-round-large">login</button>
    <button v-else @click="logout()" class="w3-button w3-blue w3-round-large">logout</button>
  </div>
</template>

<script>
import loggedUser from '@/mixins/loggedUser'

export default {
  mixins: [ loggedUser ]
}
</script>

<style lang="css">
</style>
