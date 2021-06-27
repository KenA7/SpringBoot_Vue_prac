<template>
  <div class="login-container">
    <el-form ref="loginForm" :model="loginForm" v-loading="loading" :rules="rules" label-position="left" label-width="80px" class="login-form">
      <div class="avatar_box">
        <img src="../assets/logo.png"/>
      </div>
      <div>
        <el-form-item label="用户名" prop="username" class="form-item">
            <el-input v-model="loginForm.username" placeholder="邮箱/用户名" prefix-icon="el-icon-s-custom"></el-input>
        </el-form-item>
      <el-form-item label="密码" prop="password" class="form-item">
        <el-input v-model="loginForm.password" type="password" placeholder="密码" prefix-icon="el-icon-lock"></el-input>
      </el-form-item>
      <el-form-item label="验证码" prop="captchaCode" class="form-item">
        <div>
          <el-input placeholder="验证码" v-model="loginForm.captchaCode" style="width: 60%;"></el-input>
          <el-image :src="captchaImg"  class="captcha-img" @click="getCaptcha"></el-image>
        </div>
      </el-form-item>
      <el-checkbox v-model="check">Remember password</el-checkbox>
      <el-button @click="submitForm('loginForm')" type="primary">Login</el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
import qs from 'qs'
  export default {
    data() {
      return {
        loginForm: {
          username: 'ken',
          password: '1234',
          token: '',
          captchaCode:'222'
        },
        loading: false,
        check: false,
        rules: {
          username: {required: true, message: "请输入用户名", trigger: 'blur'},
          password: {required: true, message: "请输入密码", trigger: 'blur'},
          captchaCode: {required: true, message: "请输入验证码", trigger: 'blur'}
        },
        captchaImg:''
      }
    },
    methods: {
      submitForm(formName) {
        const _this = this
        _this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$axios.post('/login?'+ qs.stringify(this.loginForm)).then(res => {
                const jwt = res.headers['authorization']
                this.$store.commit('SET_TOKEN',jwt)
              this.$router.push('/home')
             this.$message.success("登录成功")
            })
          } else {
            return false;
          }
        });
      },
      getCaptcha(){
        this.$axios.get('/captcha').then((res)=>{
            this.loginForm.token = res.data.data.token
            this.captchaImg = res.data.data.base64Image
            // this.loginForm.captchaCode = ''
        })
      }
    },
    rememberAccount(){
      const _this = this
      if(_this.check){
        // const jwt = res.headers['Authorization']
        // this.$store.commit('SET_TOKEN',jwt)
      }
    },
    created() {
      // 自调用方法
      this.getCaptcha()
    }
  }
</script>
<style>
.login-container{
  background-color: #2b4b6b;
  height: 100%;
  width: 100%;
}
.avatar_box{
  width: max-content;
  height: max-content;
  border: 5px solid #eee;
  border-radius: 50%;
  padding: 10px;
  box-shadow: 0 0 10px #ddd;
  position: relative;
  left: 50%;
  margin-bottom: -25%;
  background-color: #0ee;
  transform: translate(-50%,-50%);
}
.avatar_box img{
  width: 100%;
  height: 100%;
  border-radius: 5px;
  margin-top: 1%;

}
.form-item{
  margin-top: 5%;
}
.captcha-item{
  width: 40%;
  margin-top: 5%;
}
.captcha-img{
  width: 35%;
  height: 40px;
  float: right;
}
.el-button{
  width: 100%;
}
.el-checkbox{
  margin: 0 0 15px 0;
}
.login-form{
  background-color: #ffffff;
  padding: 40px;
  border-radius: 5px;
  box-shadow: 0 0 10px #ddd;
  border: 3px solid #eee;
  width: 30%;
  top:50%;
  left: 50%;
  transform: translate(-50%,-50%);
  position: absolute;
}

</style>
