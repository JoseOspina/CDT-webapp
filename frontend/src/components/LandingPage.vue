<template lang="html">
  <div class="content-container this-container">
    <div class="central-column-container dark-1">
      <div class="w3-row w3-center">

      </div>
      <div class="w3-row w3-center">
        <img class="logo" src="./../assets/logo-white.png" alt="">
      </div>
      <div class="w3-row btn-row">
        <app-button v-if="!isAuthenticated" @click="login()"
          class="margin-0-auto">Start!</app-button>
      </div>
      <div class="w3-row w3-center">

      </div>
    </div>
  </div>
</template>

<script>
import loggedUser from '@/mixins/loggedUser'

export default {
  mixins: [ loggedUser ]
}
</script>

<style scoped>

.this-container {
  background: url('./../assets/background-2.png');
  background-color: #3F3E3E;
  height: 100vh;
}

.central-column-container {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: calc(100vh - 85px);
}

.btn-row {
  margin-top: 35px;
}

.logo {
  width: 350px;
}

</style>
