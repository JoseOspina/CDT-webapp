import Vue from 'vue'
import Router from 'vue-router'

import RootView from '@/components/RootView'
import LoginCallback from '@/components/LoginCallback'

import LandingPage from '@/components/LandingPage'
import AppView from '@/components/AppView'

import Dashboard from '@/components/Dashboard'
import OrganizationPolls from '@/components/OrganizationPolls'
import NewPoll from '@/components/newpoll/NewPoll'
import NewPollTemplate from '@/components/newpoll/NewPollTemplate'
import NewPollQuestions from '@/components/newpoll/NewPollQuestions'
import NewPollConfiguration from '@/components/newpoll/NewPollConfiguration'

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
          component: Dashboard,
          children: [
            { path: 'polls', name: 'OrganizationPolls', component: OrganizationPolls },
            {
              path: 'newpoll',
              name: 'NewPoll',
              component: NewPoll,
              children: [
                { path: 'template', name: 'NewPollTemplate', component: NewPollTemplate },
                { path: 'questions', name: 'NewPollQuestions', component: NewPollQuestions },
                { path: 'config', name: 'NewPollConfiguration', component: NewPollConfiguration }
              ]
            }
          ]
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
