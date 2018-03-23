
<template lang="html">
  <div class="content-container this-container">
    <div class="gradient-container">
      <div class="central-column-container">
        <div class="w3-row top-row w3-center">

        </div>
        <div class="w3-row w3-center">
          <img class="logo-landing" src="./../assets/logo-white.png" alt="">
        </div>
        <div class="w3-row btn-row">
          <app-button @click="start()"
          class="margin-0-auto">{{ $t('START') }}</app-button>
        </div>
        <div class="w3-row w3-center">
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import loggedUser from '@/mixins/loggedUser'

export default {
  mixins: [ loggedUser ],

  methods: {
    start () {
      if (this.isAuthenticated) {
        this.$router.push({name: 'Dashboard'})
      } else {
        this.login()
      }
    }
  }
}
</script>

<style scoped>

.this-container {
  background: url('./../assets/background-2.png');
  background-color: #3F3E3E;
  height: 100vh;
  padding: 0px;
}

.gradient-container {
  background: radial-gradient(rgba(0, 0, 0, 1), rgba(0, 0, 0, 0.8), rgba(0, 0, 0, 0.0));
  height: 100vh;
  padding-top: 15vh;
}

.central-column-container {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 70vh;
 }

.top-row {
  margin-top: 15vh;
}

.btn-row {
  margin-top: 15vh;
  transform: scale(0.85);
}

.logo-landing {
  width: 250px;
}

</style>
