import Vue from 'vue'
import AuthService from '@/auth/AuthService.js'
const auth = new AuthService()

const state = {
  auth: auth,
  authenticated: false,
  profile: null
}

const getters = {
  isAuthenticated: (state) => {
    return state.authenticated
  },
  organizations: (state) => {
    if (state.profile !== null) {
      return state.profile.organizations
    } else {
      return []
    }
  }
}

const mutations = {
  updateAuthenticated: (state) => {
    state.authenticated = auth.authenticated
  },
  setAuthenticated: (state, payload) => {
    state.authenticated = payload
  },
  setProfile: (state, payload) => {
    state.profile = payload
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
    context.state.auth.handleAuthentication(context)
  },
  updateProfile: (context) => {
    console.log('updating profile')
    /* user profile */
    if (context.state.authenticated) {
      Vue.axios.get('/1/user/myProfile').then((response) => {
        if (response.data.result === 'success') {
          context.commit('setProfile', response.data.data)
        } else {
          if (response.data.message === 'anonymous user') {
            context.commit('setAuthenticated', false)
          }
        }
      }).catch((error) => {
        console.log(error)
        context.dispatch('logout')
      })
    }
  }
}

export default {
  state,
  getters,
  mutations,
  actions
}
