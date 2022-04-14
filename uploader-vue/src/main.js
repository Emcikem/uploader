import Vue from 'vue'
import App from './App.vue'

import uploader from 'vue-simple-uploader'

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

Vue.use(ElementUI)
Vue.use(uploader)

new Vue({
  el: '#app',
  render: h => h(App)
})
