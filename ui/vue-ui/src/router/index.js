import Vue from 'vue'
import VueRouter from 'vue-router'

import Home from '../components/Home.vue'
import Login from "../components/Login";
import PasswordUpdate from "../views/sys/PasswordUpdate";
import User from "../views/sys/User";

import axios from "axios";
import store from "../store";
import Element from "element-ui";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Index',
    component: Home
  },
  {
    path: '/home',
    name: 'Home',
    component: Home,
    children: [
      {
        path: '/update',
        name: 'Password',
        component: PasswordUpdate
      }
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
//根据权限动态绑定路由
router.beforeEach((to,from,next)=>{

  let hasRoute = store.state.menus.hasRoutes

  let token = localStorage.getItem("token")

  if(to.path ==='/login') {
    next()
  }
  else if(!token){
    next({path:'/login'})
    Element.Message.error("尚未登录，请先登录")
  } else if(token && !hasRoute){
    axios.get('/sys/menu/nav',{
      headers:{
        Authorization: localStorage.getItem('token')
      }
    }).then(res=>{
      // 拿到menu list
      store.commit("setMenuList",res.data.data.nav)
      // 拿到用户权限
      store.commit("setRoleList",res.data.data.authorizations)
      // 动态绑定路由
      let newRoutes = router.options.routes

      res.data.data.nav.forEach(menu => {
        if (menu.children) {
          menu.children.forEach(e => {

            // 转成路由
            let route = menuToRoute(e)

            // 吧路由添加到路由管理中
            if (route) {
              newRoutes[1].children.push(route)
            }

          })
        }
      })

      // 映射到router

      router.addRoutes(newRoutes)
      hasRoute = true
      store.commit("changeRouteStatus", hasRoute)
    })
  }


  next()
})

// 导航转成路由
const menuToRoute = (menu) => {

  if (!menu.component) {
    return null
  }

  let route = {
    name: menu.name,
    path: menu.path,
    meta: {
      icon: menu.icon,
      title: menu.title
    }
  }
  route.component = () => import('@/views/' + menu.component +'.vue')

  return route
}

export default router
