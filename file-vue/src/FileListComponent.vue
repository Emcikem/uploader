<template>
  <div style="text-align: center">
    <el-table
      ref="multipleTable"
      :data="tableData"
      border
      tooltip-effect="dark"
      style="margin: auto;"
      @cell-mouse-enter="handleMouseEnter"
      @selection-change="handleSelectionChange">
      <el-table-column
        type="selection">
      </el-table-column>
      <el-table-column
        label="文件名">
        <template v-slot="scope">
          {{scope.row.fileName}}
          <el-dropdown @command="(command)=>{handleCommand(command, scope.row)}" v-show="scope.row.identifier===identifier">
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
      </el-table-column>

      <el-table-column
        label="文件大小">
        <template v-slot="scope">{{ formatSize(scope.row.totalSize) }}</template>
      </el-table-column>
      <el-table-column
        label="修改日期"
        show-overflow-tooltip>
        <template v-slot="scope">{{ formatTime(scope.row.updateTime) }}</template>
      </el-table-column>
    </el-table>
    <el-pagination
      style="white-space: normal !important;"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page.sync="pageNo"
      :page-sizes="[10, 50, 100]"
      :page-size="pageSize"
      layout="sizes, prev, pager, next"
      :total="total">
    </el-pagination>
  </div>
</template>

<script>
import { formatDate } from '@/utils/date'
import axios from "axios";
import Bus from './bus.js'
export default {
  name: "FileListComponent",
  data() {
    return {
      pageNo: 1,
      pageSize: 10,
      total: 0,
      tableData: [],
      multipleSelection: [],
      keyWord: '',
      identifier: ''
    }
  },
  methods: {
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    // 当前页修改时
    handleCurrentChange(val) {
      this.pageNo = val
      this.handleChange()
    },
    // 每页个数修改时
    handleSizeChange(val) {
      this.pageNo = 1
      this.pageSize = val
      this.handleChange()
    },
    // 查询数据
    handleChange() {
      axios.get('http://110.40.220.211:8989/search/fileList', {
        params: {
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          keyWord: this.keyWord
        }
      }).then((res) => {
        if (res.data.success) {
          let result = res.data.data
          this.total = result.total
          this.pageNo = result.pageNo
          this.pageSize = result.pageSize
          this.tableData = result.content
        }
      }).catch(err => console.log(err))
    },
    // 鼠标进入时的操作
    handleMouseEnter(row) {
      this.identifier = row.identifier
    },
    initPage() {
      this.pageNo = 1
      this.pageSize = 10
      this.total = 0
      this.keyWord = ''
    },
    formatTime(updateTime) {
      return updateTime == null ? 'null' : formatDate(new Date(updateTime), 'yyyy-MM-dd hh:mm:ss')
    },
    formatSize(size) {
      let unit;
      let units = [ 'B', 'KB', 'MB', 'GB', 'TB' ];
      while ( (unit = units.shift()) && size > 1024 ) {
        size = size / 1024;
      }
      return (unit === 'B' ? size : size.toFixed( 2)) + unit;
    },
    // 下拉菜单
    handleCommand(command, row) {
      if (command === 'command_delete') {
        this.deleteFile(row.identifier)
      } else if (command === 'command_rename') {
        this.reNameFileMessageBox(row.identifier)
      } else if (command === 'command_download') {
        this.downLoadFile(row.identifier)
      }
    },
    // 删除文件
    deleteFile(identifier) {
      axios.delete('http://110.40.220.211:8989/option/delete', {
        params: { identifier: identifier}
      }).then((res) => {
        if (res.data.success) {
          this.$message({message: '删除成功', type: 'success'})
          // TODO:数据进行更新，有个问题：你修改的是数据库的数据，但查询的是es的数据，canal同步时出错了怎么办
        } else {
          this.$message({message: '删除成功', type: 'error'})
        }
      }).catch(err => console.log(err))
    },
    // 重命名
    reNameFile(identifier, name) {
      axios.get('http://110.40.220.211:8989/option/reName', {
        params: { identifier: identifier, name: name}
      }).then((res) => {
        if (res.data.success) {
          this.$message({message: '重命名成功', type: 'success'})
        } else {
          this.$message({message: '重命名失败', type: 'error'})
        }
      }).catch(err => console.log(err))
    },
    reNameFileMessageBox(identifier) {
      this.$prompt('重命名', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
      }).then(({ value }) => {
        this.reNameFile(identifier, value)
      }).catch(err => console.log(err))
    },
    downLoadFile(identifier) {
      axios.get('http://110.40.220.211:8989/download/direct', {
        params: { identifier: identifier}
      }).then((res) => {
        console.log(res)
        if (res.status === 200) {
          let a = document.createElement('a');
          a.download = 'file';
          a.href = "http://110.40.220.211:8989/download/direct?identifier=" + identifier
          a.click();
          this.$message({message: '下载成功', type: 'success'})
        } else {
          this.$message({message: '下载失败', type: 'error'})
        }
      }).catch(err => console.log(err))
    },
  },
  created: function () {
    this.handleChange()
    // 搜索框搜索的监听事件，兄弟组件通信
    Bus.$on('deliverSearchWord', keyWord => {
      this.initPage()
      this.keyWord = keyWord
      this.handleChange()
    })
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
