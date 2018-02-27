import Vue from 'vue'

const state = {
  organization: null
}

const getters = {
}

const mutations = {
  setOrganization: (state, payload) => {
    state.organization = payload
  }
}

const actions = {
  refreshOrganization: (context) => {
    if (context.state.organization !== null) {
      context.dispatch('updateOrganization', context.state.organization.id)
    }
  },
  updateOrganization: (context, id) => {
    console.log('updating organization')
    /* user profile */

    Vue.axios.get('/1/organization/' + id).then((response) => {
      if (response.data.result === 'success') {
        context.commit('setOrganization', response.data.data)
      } else {
        console.log(response.data.message)
      }
    }).catch((error) => {
      console.log(error)
    })
  }
}

export default {
  state,
  getters,
  mutations,
  actions
}
