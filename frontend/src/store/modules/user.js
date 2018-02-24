import AuthService from '@/auth/AuthService.js'
const auth = new AuthService()

const state = {
  auth: auth,
  authenticated: false
}

const getters = {
  isAuthenticated: (state) => {
    return state.authenticated
  }
}

const mutations = {
  setAuthenticated: (state, payload) => {
    state.authenticated = payload
  }
}

const actions = {
  login: (context) => {
    context.state.auth.login()
  },
  logout: (context) => {
    context.state.auth.logout()
  },
  handleAuthentication: (context) => {
    context.state.auth.handleAuthentication()
  }
}

export default {
  state,
  getters,
  mutations,
  actions
}
