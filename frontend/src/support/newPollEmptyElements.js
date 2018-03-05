const getEmptyQuestion = function () {
  return {
    id: Date.now().toString(),
    text: '',
    type: 'RATE_1_5',
    weight: 100,
    custom: true
  }
}

const getEmptyAxis = function () {
  return {
    id: Date.now().toString(),
    title: '',
    description: '',
    questions: [ getEmptyQuestion() ],
    custom: true
  }
}

const getEmptyPoll = function () {
  return {
    id: Date.now().toString(),
    title: '',
    description: '',
    axes: [ getEmptyAxis() ],
    config: {
      audience: 'ANYONE_WITH_LINK'
    }
  }
}

export {
  getEmptyQuestion,
  getEmptyAxis,
  getEmptyPoll
}
