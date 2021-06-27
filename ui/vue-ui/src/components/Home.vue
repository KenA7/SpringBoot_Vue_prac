<template>
<el-container style="height: 100%; border: 1px solid #eee">
<el-aside width="200px" style="background-color: rgb(238, 241, 246);height: 100%">
      <el-menu
          :default-active="this.$store.state.menus.editableTabsValue"
          class="el-menu-vertical-demo"
          background-color="#545c64"
          text-color="#fff"
          active-text-color="#ffd04b">

        <router-link to="/home">
        <el-menu-item index="Index" @click="createTab({name:'Index',title:'首页'})">
          <i class="el-icon-s-home"></i>
          <span slot="title">首页</span>
        </el-menu-item>
        </router-link>

        <el-submenu :index="menu.name" v-for="menu in menuList" :key="menu.index">
          <template slot="title">
            <i :class="menu.icon"></i>
            <span>{{ menu.title }}</span>
          </template>
          <el-menu-item-group v-for="(subMenu,index) in menu.children" :key="subMenu.index">
            <router-link :to="subMenu.path">
            <el-menu-item class="menu-item" style="min-width: 0;" :index="subMenu.name" @click="createTab(subMenu)">
              <i :class="subMenu.icon"></i>
              <span>{{ subMenu.title }}</span>
            </el-menu-item>
            </router-link>
          </el-menu-item-group>
        </el-submenu>

      </el-menu>
</el-aside>

<el-container>
  <el-header style="text-align: right; font-size: 12px">
    <div class="header-bar">
      <el-avatar class="header-bar-item" :src="userInfo.avatar"></el-avatar>
      <el-dropdown class="header-bar-item" >
          <span class="el-dropdown-link">
            个人中心<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
        <el-dropdown-menu slot="dropdown">
          <router-link to="/update">
            <el-dropdown-item >修改密码</el-dropdown-item>
          </router-link>
          <el-dropdown-item @click.native="logout">登出</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    <span class="header-bar-item">欢迎, {{userInfo.username}}</span>
    </div>
  </el-header>

  <el-main style="padding: 0;">
    <Tabs></Tabs>
    <div style="margin: 0 15px">
      <router-view/>
    </div>

  </el-main>
</el-container>
</el-container>
</template>

<style>
.header-bar{
  float: right;
  width: max-content;
  display: flex;
  justify-content: space-around;
  align-items: center;
}
.header-bar-item{
  margin-left: 10px;
  font-size: medium;
}
.el-menu-vertical-demo{
  height: 100%;
}
.el-main{
  padding: 0;
  text-align: center;
}
.el-header {
  background-color: #17B3A3;
  color: #333;
  line-height: 60px;
}

.el-aside {
  color: #333;
}

.el-dropdown {
  vertical-align: top;
}
.el-dropdown-link {
  cursor: pointer;
  color: #2a2d36;
}
.el-icon-arrow-down {
  font-size: 12px;
}

a{
  text-decoration: none;
}
</style>

<script>
import Tabs from "../views/common/Tabs";
export default {
  components:{
    Tabs
  },
  data() {
    return{
      userInfo:{
        id:'',
        username: '',
        avatar: '',
        createdTime: ''
      },
    }
  },
  created() {
    this.getUserInfo()
  },
  computed:{
    menuList:{
      get(){
        return this.$store.state.menus.menuList
      }
    }
  },
  methods:{
    getUserInfo(){
      this.$axios.get('/sys/userInfo').then(res=>{
        this.userInfo =res.data.data
      })
    },
    logout(){
      const _this =this
      this.$axios.post('/logout').then(res=>{
        localStorage.clear()
        sessionStorage.clear()

        this.$store.commit("resetState")
        this.$message({
          message: "登出成功",
          type: "success"
        })
        _this.$router.push('/login')
      })
    },
    createTab(item){
      this.$store.commit("addTabs",item)
    },
  }
};
</script>


