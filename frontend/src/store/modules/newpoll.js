const state = {
  poll: null
}

const getters = {
}

const mutations = {
  setNewPoll: (state, payload) => {
    state.poll = payload
  }
}

const actions = {
}

export default {
  state,
  getters,
  mutations,
  actions
}
