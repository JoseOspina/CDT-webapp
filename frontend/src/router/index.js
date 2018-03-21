import Vue from 'vue'
import Router from 'vue-router'

import RootView from '@/components/RootView'
import LoginCallback from '@/components/LoginCallback'

import LandingPage from '@/components/LandingPage'
import AppView from '@/components/AppView'

import Dashboard from '@/components/Dashboard'

import Organizations from '@/components/Organizations'
import OrganizationContent from '@/components/OrganizationContent'
import OrganizationPolls from '@/components/OrganizationPolls'
import OrganizationPoll from '@/components/OrganizationPoll'

import NewPoll from '@/components/newpoll/NewPoll'
import NewPollTemplate from '@/components/newpoll/NewPollTemplate'
import NewPollQuestions from '@/components/newpoll/NewPollQuestions'
import NewPollConfiguration from '@/components/newpoll/NewPollConfiguration'

import AnswerPoll from '@/components/answerpoll/AnswerPoll'

Vue.use(Router)

export default new Router({
  mode: 'hash',
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
          component: Dashboard,
          children: [
            { path: '/', name: 'Dashboard', redirect: 'orgs' },
            { path: 'orgs', name: 'Organizations', component: Organizations },
            {
              path: 'org/:orgId',
              component: OrganizationContent,
              children: [
                { path: '/', name: 'OrganizationContent', redirect: 'polls' },
                { path: 'polls', name: 'OrganizationPolls', component: OrganizationPolls },
                { path: 'poll/:pollId', name: 'OrganizationPoll', component: OrganizationPoll },
                { path: 'poll/:pollId/edit', name: 'EditPoll', component: OrganizationPoll },
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
            }
          ]
        },
        {
          path: 'answer/:pollId',
          name: 'AnswerPoll',
          component: AnswerPoll
        }
      ]
    }
  ]
})
