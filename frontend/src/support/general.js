const getElIx = function (id, arr) {
  for (var ix = 0; ix < arr.length; ix++) {
    if (arr[ix].id === id) {
      return ix
    }
  }
  return -1
}

export {
  getElIx
}
