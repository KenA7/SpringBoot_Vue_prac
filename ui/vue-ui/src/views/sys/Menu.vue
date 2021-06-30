<template>
  <div>
    <el-form :inline="true"  class="demo-form-inline" style="text-align: left">
      <el-form-item>
        <el-button type="primary" @click="dialogVisible = true">新增</el-button>
      </el-form-item>
    </el-form>
<!--    数据报告-->
    <el-table
        :data="tableData"
        style="width: 100%;"
        row-key="id"
        border
        default-expand-all
        :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
      <el-table-column
          prop="name"
          label="名称"
          sortable
          width="130">
      </el-table-column>
      <el-table-column
          prop="perms"
          label="权限编码"
          sortable
          width="120">
      </el-table-column>
      <el-table-column
          prop="icon"
          label="图标">
      </el-table-column>
      <el-table-column
          prop="type"
          label="类型">
        <template slot-scope="scope">
          <el-tag size="small" v-if="scope.row.type ===0">目录</el-tag>
          <el-tag size="small" v-if="scope.row.type ===1" type="success">菜单</el-tag>
          <el-tag size="small" v-if="scope.row.type ===2" type="info">按钮</el-tag>
        </template>
      </el-table-column>
      <el-table-column
          prop="path"
          label="菜单url">
      </el-table-column>
      <el-table-column
          prop="component"
          label="菜单组件">
      </el-table-column>
      <el-table-column
          prop="orderNum"
          label="排序号">
      </el-table-column>
      <el-table-column
          prop="status"
          label="状态"
          width="100">
        <template slot-scope="scope">
          <el-tag size="small" v-if="scope.row.status ===1" type="success">正常</el-tag>
          <el-tag size="small" v-if="scope.row.status ===0" type="danger">禁用</el-tag>
        </template>
      </el-table-column>
      <el-table-column
          label="操作"
          width="150px"
      >
        <template slot-scope="scope" >
          <el-button
              size="mini"
              @click="getMenuInfo(scope.row), dialogVisible = true">编辑</el-button>
          <template>
            <el-popconfirm title="确定要删除这条数据吗?" @confirm="deleteMenu(scope.row.id)">
              <el-button slot="reference" type="danger" size="mini" style="margin-left: 3px" >删除</el-button>
            </el-popconfirm>
          </template>
        </template>
      </el-table-column>
    </el-table>

<!--新增对话框-->
    <el-dialog
        title="菜单信息"
        :visible.sync="dialogVisible"
        width="40%"
        :before-close="handleClose"
        id="dialog_box"
      >
      <el-form ref="editForm" :model="editForm" label-width="80px" style="text-align: left;padding: 20px" :rules="formRules">
        <el-form-item label="上级菜单" prop="parentId">
          <el-select placeholder="请输入上级菜单" v-model="editForm.parentId" v-if="editForm.parentId? disabled:null">
            <template v-for="item in tableData">
              <el-option :label="item.name" :value="item.id"></el-option>
              <template v-for="subItem in item.children">
                <el-option :label="subItem.name" :value="subItem.id">
                  <span> {{'-'+subItem.name}}</span>
                </el-option>
              </template>
            </template>
          </el-select>
        </el-form-item>
        <el-form-item label="菜单名称"  prop="name" width="100px">
          <el-input v-model="editForm.name" placeholder="请输入菜单名称"></el-input>
        </el-form-item>
        <el-form-item label="权限编码"  prop="perms" width="100px">
          <el-input v-model="editForm.perms" placeholder="请输入权限编码" ></el-input>
        </el-form-item>
        <el-form-item label="图标" prop="icon" width="100px">
          <el-input v-model="editForm.icon" placeholder="请输入图标编码"></el-input>
        </el-form-item>
        <el-form-item label="菜单URL" prop="path" width="100px">
            <el-input v-model="editForm.path" placeholder="请输入菜单URL" :disabled="false" ></el-input>
        </el-form-item>
        <el-form-item label="菜单组件" prop="component" width="100px">
          <el-input v-model="editForm.component" placeholder="请输入菜单组件"></el-input>
        </el-form-item>
        <el-form-item label="类型" prop="type" width="100px">
          <template>
            <el-radio v-model="editForm.type" :label="0">目录</el-radio>
            <el-radio v-model="editForm.type" :label="1">菜单</el-radio>
            <el-radio v-model="editForm.type" :label="2">按钮</el-radio>
          </template>
        </el-form-item>
        <el-form-item label="状态" prop="status" width="100px">
          <template>
            <el-radio v-model="editForm.status" :label="1">正常</el-radio>
            <el-radio v-model="editForm.status" :label="0">禁用</el-radio>
          </template>
        </el-form-item>
        <el-form-item label="排序号" prop="orderNum" width="100px">
          <template>
            <el-input-number v-model="editForm.orderNum"  label="描述文字" width="100px"></el-input-number>
          </template>
        </el-form-item>
        <el-form-item class="edit-form-button">
          <el-button type="primary" @click="submitForm('editForm')">立即创建</el-button>
          <el-button @click="submitForm('editForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>


</template>
<script>
export default {
  data() {
    return {
      dialogVisible: false,
      disabled:true,
      editForm: {
        parentId: '',
        name: '',
        perms:'',
        icon: '',
        path: '',
        component: '',
        type: '',
        status: '',
        orderNum:''
      },
      formRules:{
          parentId: [
            {required:true,message:"请选择上级菜单",trigger:'blur'}
          ],
        name: [
          {required:true,message:"请选择菜单名称",trigger:'blur'}
        ],
        orderNum: [
          {required:true,message:"请输入排序号",trigger:'blur'}
        ],
        type:[
          {required:true,message:"请选择类型",trigger:'blur'}
        ],
        status:[
          {required:true,message:"请选择状态",trigger:'blur'}
        ]
      },
      tableData: []
    }
  },
  created() {
    this.getMenuTree()
  },
  methods: {
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            this.editForm=''
            done();
          })
          .catch(_ => {});
    },
    getMenuTree(){
        this.$axios.get('/sys/menu/list').then(res=>{
            this.tableData = res.data.data
        })
    },
    getMenuInfo(row){
      this.$axios.get('/sys/menu/info/' + row.id).then(res=>{
        this.editForm = res.data.data
        this.dialogVisible = true
      })
    },
    submitForm(formName){
      const _this = this
      _this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post('/sys/menu/' + (this.editForm.id?'update' : 'save'), this.editForm)
              .then(res => {

                this.$message({
                  showClose: true,
                  message: '操作成功',
                  type: 'success',
                  onClose:() => {
                    this.getMenuTree()
                  }
                });

                this.dialogVisible = false
              })
        } else {
          return false;
        }
      });
    },
    deleteMenu(id){
      this.$axios.delete('/sys/menu/delete/'+id).then(res=>{
        if(res.data.code===200){
          this.$message({
            showClose: true,
            message: '操作成功',
            type: 'success',
            onClose:() => {
              this.getMenuTree()
            }
          });
        }
      })
    }

  }
}
</script>
<style>
.el-dialog{
  border-radius: 20px;
  padding: 5px;
}
.el-table td, .el-table th{
  padding: 5px 0;
}
.edit-form-button{
  float: right;
}

</style>