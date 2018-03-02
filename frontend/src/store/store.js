import Vue from 'vue'
import Vuex from 'vuex'

import user from './modules/user'
import organization from './modules/organization'
import newpoll from './modules/newpoll'
import answerpoll from './modules/answerpoll'

Vue.use(Vuex)

export const store = new Vuex.Store({
  state: {},

  modules: {
    user,
    organization,
    newpoll,
    answerpoll
  }
})
