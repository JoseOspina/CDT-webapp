const state = {
  poll: null,
  editing: false
}

const getters = {
}

const mutations = {
  setNewPoll: (state, payload) => {
    state.poll = payload
  },
  setEditingNewPoll: (state, payload) => {
    state.editing = payload
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
