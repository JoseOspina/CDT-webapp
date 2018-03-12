// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import { store } from './store/store'

import axios from 'axios'
import VueAxios from 'vue-axios'
import VueCookie from 'vue-cookie'

import VueI18n from 'vue-i18n'
import Datepicker from 'vuejs-datepicker'

import ErrorPanel from '@/components/styled/ErrorPanel.vue'
import { translations } from '@/lang'

import ColumnHeader from '@/components/styled/ColumnHeader'
import BackButton from '@/components/styled/BackButton'
import PlusButton from '@/components/styled/PlusButton'
import AppButton from '@/components/styled/AppButton'

import VueMarkdown from 'vue-markdown'
import MarkdownEditor from '@/components/MarkdownEditor.vue'

Vue.config.productionTip = false

Vue.use(VueAxios, axios)

Vue.component('app-column-header', ColumnHeader)
Vue.component('app-back-button', BackButton)
Vue.component('app-plus-button', PlusButton)
Vue.component('app-button', AppButton)

Vue.component('vue-markdown', VueMarkdown)
Vue.component('app-markdown-editor', MarkdownEditor)

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

Vue.use(VueCookie)

Vue.component('app-error-panel', ErrorPanel)
Vue.component('datepicker', Datepicker)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  i18n,
  components: { App },
  template: '<App/>'
})
