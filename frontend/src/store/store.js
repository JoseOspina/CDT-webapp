import Vue from 'vue'
import Vuex from 'vuex'

import user from './modules/user'
import organization from './modules/organization'

Vue.use(Vuex)

export const store = new Vuex.Store({
  state: {},

  modules: {
    user,
    organization
  }
})
