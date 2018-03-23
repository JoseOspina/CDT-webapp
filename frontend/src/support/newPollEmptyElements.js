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
    includeInPlot: true,
    description: '',
    questions: [ getEmptyQuestion() ],
    custom: true
  }
}

const getDefaultConfig = function () {
  return {
    audience: 'ANYONE_WITH_LINK'
  }
}

const getEmptyPoll = function () {
  return {
    id: Date.now().toString(),
    title: '',
    description: '',
    axes: [ getEmptyAxis() ],
    custom: true
  }
}

export {
  getEmptyQuestion,
  getEmptyAxis,
  getEmptyPoll,
  getDefaultConfig
}
