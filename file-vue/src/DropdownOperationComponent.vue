<template>
    <el-dropdown @command="handleCommand">
      <span class="el-dropdown-link">
        操作<i class="el-icon-arrow-down el-icon--right"></i>
      </span>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item command="command_download">下载</el-dropdown-item>
        <el-dropdown-item command="command_delete">删除</el-dropdown-item>
        <el-dropdown-item command="command_rename">重命名</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
</template>

<script>
export default {
  name: "DropdownOperationComponent",
  data() {
    return{}
  },
  methods: {
    handleCommand(command) {
      this.$message('click on item ' + command);
    }
  }
}
</script>

<style scoped>
.el-dropdown-link {
  cursor: pointer;
  color: #409EFF;
}
.el-icon-arrow-down {
  font-size: 12px;
}
</style>
