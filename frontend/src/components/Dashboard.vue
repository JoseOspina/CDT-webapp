<template lang="html">
  <div class="">
    <app-header></app-header>
    <div class="dashboard-container">
      <div class="dashboard-content-back">
        <div class="dashboard-content">
          <router-view></router-view>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Header from '@/components/Header'
export default {
  components: {
    'app-header': Header
  },

  created () {
    if (!this.$store.state.user.authenticated) {
      this.$router.push({name: 'LandingPage'})
    }
  }
}
</script>

<style scoped>

.dashboard-container {
  background-color: #3F3E3E;
}

.dashboard-content-back {
  margin: 0 auto;
  max-width: 1200px;
  background: linear-gradient(180deg, #313232, #363536);
}

.dashboard-content {
  width: 100%;
  background: url('./../assets/background-2A-low.png');
  background-size: cover;
}

</style>
