
<template lang="html">
  <div class="content-container this-container">
    <div class="central-column-container dark-1">
      <div class="w3-row w3-center">

      </div>
      <div class="w3-row w3-center">
        <img class="logo-landing" src="./../assets/logo-white.png" alt="">
      </div>
      <div class="w3-row btn-row">
        <app-button @click="start()"
          class="margin-0-auto">{{ $t('START-MESSAGE') }}</app-button>
      </div>
      <div class="w3-row w3-center">
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
}

.central-column-container {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 70vh;
}

.btn-row {
  margin-top: 35px;
}

.logo-landing {
  width: 350px;
}

</style>
