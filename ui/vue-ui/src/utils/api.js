import axios from "axios";
import router from "../router";
import Element from "element-ui";

axios.defaults.baseURL="http://localhost:9000"



const request = axios.create({
    timeout: 5000,
    headers:{
        'Content-type': "application/json; charset=utf-8"
    }
})

request.interceptors.request.use(config=>{
    config.headers['Authorization']=localStorage.getItem("token")
    return config
},error => {
    console.log(error)
    }
    )

// 拦截response 数据
request.interceptors.response.use(response=>{
    if(response.data.code === 200){
        return response
    }else{
        Element.Message.error(!response.data.msg? `Internal error`:response.data.msg)
        return Promise.reject(response.data.msg)
    }
},
    error => {
    if(error.response.data){

        error.message = error.response.data.message
    }
    if(error.response.status === 401){
        router.push('/login')
        Element.Message.error("尚未登录，请先登录")
    }
    Element.Message.error(error.message,{duration:30000})
    return Promise.reject(error)
    }

)
export default request