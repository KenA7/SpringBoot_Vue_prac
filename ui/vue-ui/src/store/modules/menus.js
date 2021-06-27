import Vue from 'vue'
import Vuex from 'vuex'
import ta from "element-ui/src/locale/lang/ta";

Vue.use(Vuex)

export default{
    state: {
        menuList:[],
        roleList:[],
        hasRoutes:false,
        editableTabsValue: 'Index',
        editableTabs: [{
              title: '首页',
              name: 'Index',
          }]
    },
    mutations: {
        setMenuList(state,menuList){
            state.menuList = menuList
        },
        setRoleList(state,roleList){
            state.roleList =roleList
        },
        changeRouteStatus(state, hasRoutes) {
            state.hasRoutes = hasRoutes
        },
        addTabs(state,tab){
       let index = state.editableTabs.findIndex(item => item.name === tab.name)
            if(index === -1){
                state.editableTabs.push({
                    title: tab.title,
                    name: tab.name
                })
            }
            state.editableTabsValue = tab.name

        },
        resetState(state){
            state.menuList = []
            state.roleList = []
            state.hasRoutes =false
            state.editableTabs=[{
                title: '首页',
                name: 'Index'
            }]
            state.editableTabsValue = 'Index'
        }
    },
    actions: {
    },
    modules: {
    }
}
