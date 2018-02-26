import Vue from 'vue'
import Router from 'vue-router'

import RootView from '@/components/RootView'
import LoginCallback from '@/components/LoginCallback'

import LandingPage from '@/components/LandingPage'
import AppView from '@/components/AppView'

import Dashboard from '@/components/Dashboard'
import AnswerView from '@/components/AnswerView'

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
    },
    {
      path: '/landing',
      name: 'LandingPage',
      component: LandingPage
    },
    {
      path: '/app',
      component: AppView,
      children: [
        {
          path: '/',
          name: 'AppView',
          redirect: 'dashboard'
        },
        {
          path: 'dashboard',
          name: 'Dashboard',
          component: Dashboard
        },
        {
          path: 'answer',
          name: 'AnswerView',
          component: AnswerView
        }
      ]
    }
  ]
})
