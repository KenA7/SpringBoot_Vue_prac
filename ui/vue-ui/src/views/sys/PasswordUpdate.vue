<template>
<el-container>
  <el-form :model="passForm" :ref="passForm" :rules="rules" label-width="100px" label-position="left" class="update-form">
    <h3 style="text-align: center;font-size: x-large">Hi, {{passForm.username}}</h3>
    <el-form-item label="旧密码" prop="oldPass" class="update-form-item">
      <el-input type="password" placeholder="旧密码" prefix-icon="el-icon-lock" v-model="passForm.oldPass"></el-input>
    </el-form-item>
    <el-form-item label="新密码" prop="password" class="update-form-item">
      <el-input type="password" placeholder="新密码" prefix-icon="el-icon-lock" v-model="passForm.password"></el-input>
    </el-form-item>
    <el-form-item label="确认密码" prop="confirmPass" class="update-form-item">
      <el-input type="password" placeholder="请确认新密码" prefix-icon="el-icon-lock" v-model="passForm.confirmPass"></el-input>
    </el-form-item>


    <el-button type="primary" class="update-form-button" @click="submitForm('passForm')"> Change </el-button>
    <el-button type="primary" class="update-form-button" plain> Cancel </el-button>

  </el-form>
</el-container>
</template>

<script>
import Home from "../../components/Home";
export default {
  data(){
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.passForm.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return{
      passForm:{
        password:'',
        oldPass: '',
        confirmPass:'',
        username:'',
      },
      rules:{
        password:[
          {required:true, message: "请输入新密码", trigger: 'blur'}
        ],
        confirmPass:[
          {required:true, message: "请输入新密码", trigger: 'blur'},
          {validator: validatePass, trigger: 'blur'}
        ],
        oldPass:[
          {required:true, message: "请输入旧密码", trigger: 'blur'}
        ]
      }
    }
  },
methods:{

  submitForm(formName) {
    // const _this = this
    // _this.$refs[formName].validate((valid) => {
    //   if (valid) {
    //     alert('submit!');
    //   } else {
    //     console.log('error submit!!');
    //     return false;
    //   }
    // });
  }
}
}

</script>

<style scoped>

.update-form{
  width: 60%;
  height: max-content;
  position: relative;
  padding: 5%;
  left: 50%;
  transform: translate(-55%);
}
.update-form-item{
  margin-left: 20%;
  padding: 1%;
  width: max-content;
}
.update-form .el-input{
  width: 150%;
}
.update-form-button{

  width: max-content;
  margin-right: 5%;
  margin-top: 2%;
  margin-left: 35%;
}
</style>