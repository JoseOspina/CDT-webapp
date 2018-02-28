const getEmptyQuestion = function () {
  return {
    id: Date.now().toString(),
    text: ''
  }
}

const getEmptyAxis = function () {
  return {
    id: Date.now().toString(),
    title: '',
    description: '',
    questions: [ getEmptyQuestion() ]
  }
}

const getEmptyPoll = function () {
  return {
    id: Date.now().toString(),
    title: '',
    description: '',
    axes: [ getEmptyAxis() ]
  }
}

export {
  getEmptyQuestion,
  getEmptyAxis,
  getEmptyPoll
}
