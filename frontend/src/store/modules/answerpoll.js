const getAnswerIx = function (questionId, answers) {
  for (var ix in answers) {
    if (answer.questionId === questionId) {
      return ix
    }
    return -1
  }
}

const state = {
  answers: []
}

const getters = {
}

const mutations = {
  addAnswer: (state, answer) => {
    var ix = getAnswerIx(answer.questionId, state.answers)
    if (ix == -1) {
      state.answers.push(answer)
    } else {
      state.answers[ix] = answer
    }
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
