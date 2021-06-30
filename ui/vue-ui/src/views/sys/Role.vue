<template>
  <div>
    <el-form :inline="true">
      <el-form-item>
        <el-input :model="searchForm.name" placeholder="名称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button size="medium">搜索</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary"size="medium" @click="dialogVisible = true">新增</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="danger" size="medium">批量删除</el-button>
      </el-form-item>
    </el-form>

    <el-table
        ref="multipleTable"
        :data="tableData"
        tooltip-effect="dark"
        border
        style="width: 100%"
        @selection-change="handleSelectionChange">
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column
          label="头像"
          width="120">
        <template slot-scope="scope">{{ scope.row.date }}</template>
      </el-table-column>
      <el-table-column
          prop="name"
          label="用户名"
          width="120">
      </el-table-column>
      <el-table-column
          prop="address"
          label="邮箱"
          width="100">
      </el-table-column>
      <el-table-column
          prop="phone"
          label="手机号"
          width="120">
      </el-table-column>
      <el-table-column
          prop="status"
          label="状态"
          width="120">
        <template slot-scope="scope">
          <el-tag size="small" v-if="scope.row.status ===1" type="success">正常</el-tag>
          <el-tag size="small" v-if="scope.row.status ===0" type="danger">禁用</el-tag>
        </template>
      </el-table-column>
      <el-table-column
          prop="createdTime"
          label="创建时间"
          width="max-content">
      </el-table-column>
      <el-table-column
          prop="actions"
          label="操作"
          width="max-content">
        <template>
          <el-button size="mini">分配角色</el-button>
          <el-button size="mini">重置密码</el-button>
          <el-button size="mini">编辑</el-button>
          <el-button size="mini">删除</el-button>
        </template>

      </el-table-column>
    </el-table>
  </div>


</template>
<script>
export  default {
  data(){
    return {
      searchForm:{
        name:'',
      },
      tableData: [{

      }],
      multipleSelection: []
    }
  }
}
</script>
<style>
.el-table td, .el-table th {
   padding:0;
}
</style>