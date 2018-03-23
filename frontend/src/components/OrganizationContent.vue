<template lang="html">
  <div class="">
    <div class="organization-tabs">
      <router-link :to="{name: 'OrganizationPolls'}" class="w3-center app-color-1-br cursor-pointer" :class="{'tab-selected': isPolls}">
        <h4><b>{{ $t('POLLS') }}</b></h4>
      </router-link>
      <router-link :to="{name: 'OrganizationMembers'}" class="w3-center app-color-1-br cursor-pointer" :class="{'tab-selected': isMembers}">
        <h4><b>{{ $t('MEMBERS') }}</b></h4>
      </router-link>
      <router-link :to="{name: 'OrganizationAdmins'}" class="w3-center app-color-1-br cursor-pointer" :class="{'tab-selected': isAdmins}">
        <h4><b>{{ $t('ADMINS') }}</b></h4>
      </router-link>
    </div>
    <router-view></router-view>
  </div>
</template>

<script>
export default {

  computed: {
    isPolls () {
      var res = false
      this.$route.matched.forEach((e) => {
        if (e.name === 'OrganizationPolls') {
          res = true
        }
      })
      return res
    },
    isMembers () {
      var res = false
      this.$route.matched.forEach((e) => {
        if (e.name === 'OrganizationMembers') {
          res = true
        }
      })
      return res
    },
    isAdmins () {
      var res = false
      this.$route.matched.forEach((e) => {
        if (e.name === 'OrganizationAdmins') {
          res = true
        }
      })
      return res
    }
  }
}
</script>

<style scoped>

.organization-tabs {
  margin: 0 auto;
  padding-top: 5vh;
  width: 50vw;
}

.tab-selected {
  border-bottom-style: solid;
}

.organization-tabs > a {
  width: 33%;
  float: left;
}

</style>
