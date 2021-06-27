import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

// 全局引用 element UI
import Element from 'element-ui'
import "element-ui/lib/theme-chalk/index.css"

// 全局引用 axios
import axios from "./utils/api";

// require('./mock.js')

Vue.prototype.$axios = axios //允许全局使用$axios
Vue.config.productionTip = false
Vue.use(Element)


new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
