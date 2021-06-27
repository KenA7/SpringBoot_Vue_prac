const Mock =require('mockjs')

const Random = Mock.Random


let Result = {
    code:200,
    msg: '登录成功',
    data:null
}
Mock.mock('/captcha','get',()=>{

    Result.data ={
        token: Random.string(32)
    }
    return Result
})

Mock.mock('/logout','post',()=>{
  return  Result
})
Mock.mock('/login','post',()=>{
    Result.code = 200
    Result.msg = 'test'
    return  Result
})


Mock.mock('/nav','get',()=>{

    let nav= [
        {
            title: 'Manage System',
            name: 'Manage System',
            icon: 'el-icon-s-operation',
            path: '',
            component:'',
            children: [
                {
                    title: 'Manage User',
                    name: 'Manage User',
                    icon: 'el-icon-s-custom',
                    component:'/sys/User',
                    index:'1',
                    path: '/users'
                },
                {
                    title: 'Manage Role',
                    name: 'Manage Role',
                    icon: 'el-icon-files',
                    component:'sys/Role',
                    index:'2',
                    path: '/roles'
                },
                {
                    title: 'Manage Menu',
                    name: 'Manage Menu',
                    icon: 'el-icon-menu',
                    component:'/sys/Menu',
                    index:'3',
                    path: '/menus'
                },

            ]
        }
    ]
    let authorizations = []

    Result.data={
        nav: nav,
        authorizations: authorizations
    }

    return  Result
})
