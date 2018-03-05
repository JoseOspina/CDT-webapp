const state = {
  poll: null,
  editing: false,
  fromTemplate: false
}

const getters = {
}

const mutations = {
  setNewPoll: (state, payload) => {
    state.poll = payload
  },
  setEditingNewPoll: (state, payload) => {
    state.editing = payload
  },
  setFromTemplate: (state, payload) => {
    state.fromTemplate = payload
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
