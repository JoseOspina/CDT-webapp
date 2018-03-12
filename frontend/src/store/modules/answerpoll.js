const getAnswerIx = function (questionId, answers) {
  for (var ix in answers) {
    if (answers[ix].questionId === questionId) {
      return ix
    }
  }
  return -1
}

const state = {
  answers: []
}

const getters = {
  getAnswer: (state) => (questionId) => {
    var ix = getAnswerIx(questionId, state.answers)
    if (ix !== -1) {
      if (state.answers[ix].type === 'RATE_1_5') {
        return state.answers[ix].rate
      }
      if (state.answers[ix].type === 'TEXT') {
        return state.answers[ix].text
      }
    }
  }
}

const mutations = {
  addAnswer: (state, answer) => {
    var ix = getAnswerIx(answer.questionId, state.answers)
    if (ix === -1) {
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
