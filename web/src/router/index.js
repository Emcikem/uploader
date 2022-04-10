import Vue from 'vue'
import Router from 'vue-router'
import FileUploader from '@/components/FileUploader'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'FileUploader',
      component: FileUploader
    }
  ]
})
