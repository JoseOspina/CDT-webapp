import Vue from 'vue'
import Router from 'vue-router'

import RootView from '@/components/RootView'
import LoginCallback from '@/components/LoginCallback'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'RootView',
      component: RootView
    },
    {
      path: '/loginCallback',
      name: 'LoginCallback',
      component: LoginCallback
    }
  ]
})
