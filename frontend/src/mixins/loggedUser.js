export default {
  computed: {
    isAuthenticated () {
      return this.$store.getters.isAuthenticated
    },
    userNickname () {
      if (this.$store.state.user.profile != null) {
        return this.$store.state.user.profile.nickname
      } else {
        return ''
      }
    }
  },
  methods: {
    login () {
      this.$store.dispatch('login')
    },
    logout () {
      this.$store.dispatch('logout')
    }
  }
}
