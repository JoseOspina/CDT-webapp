// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import { store } from './store/store'

import axios from 'axios'
import VueAxios from 'vue-axios'

import VueI18n from 'vue-i18n'

import ErrorPanel from '@/components/global/ErrorPanel.vue'

import { translations } from '@/lang'

Vue.config.productionTip = false

Vue.use(VueAxios, axios)

axios.interceptors.request.use(
  function (config) {
    config.headers.common.Authorization = 'Bearer ' + localStorage.getItem('id_token')
    return config
  },
  function (error) {
    return Promise.reject(error)
  }
)

Vue.use(VueI18n)
export const i18n = new VueI18n({
  locale: 'en', // set locale
  fallbackLocale: 'en',
  messages: translations
})

Vue.component('app-error-panel', ErrorPanel)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  i18n,
  components: { App },
  template: '<App/>'
})
