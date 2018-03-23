import Vue from 'vue'
import router from './../../router'

const state = {
  lock: null,
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
  setAuthenticated: (state, payload) => {
    state.authenticated = payload
  },
  setProfile: (state, payload) => {
    state.profile = payload
  },
  setLock: (state, payload) => {
    state.lock = payload
  }
}

const actions = {
  initLock: (context, lock) => {
    context.commit('setLock', lock)
  },
  handleAuthentication: (context) => {
    context.state.auth.handleAuthentication(context)
  },
  updateProfile: (context) => {
    /* user profile */
    if (context.state.authenticated) {
      console.log('updating profile')
      Vue.axios.get('/1/user/myProfile').then((response) => {
        if (response.data.result === 'success') {
          context.commit('setProfile', response.data.data)
        } else {
          if (response.data.message === 'anonymous user') {
            router.push({name: 'LandingPage'})
            context.commit('setAuthenticated', false)
          }
        }
      }).catch((error) => {
        console.log(error)
        context.dispatch('logout')
        router.push({name: 'LandingPage'})
      })
    }
  },
  logoutUser: (context) => {
    localStorage.removeItem('access_token')
    localStorage.removeItem('id_token')
    context.commit('setAuthenticated', false)
    context.commit('setProfile', null)
    router.push({name: 'LandingPage'})
  }
}

export default {
  state,
  getters,
  mutations,
  actions
}
