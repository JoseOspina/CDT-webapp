const getElIx = function (id, arr) {
  for (var ix in arr) {
    if (arr[ix].id === id) {
      return ix
    }
  }
  return -1
}

export {
  getElIx
}
