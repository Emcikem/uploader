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
          <DropdownOperationComponent
            v-show="scope.row.identifier===identifier">
          </DropdownOperationComponent>
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

    <div style="margin-top: 20px">
      <el-button @click="downLoad">下载</el-button>
      <el-button @click="deleteFile">删除</el-button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import Bus from './bus.js'
import DropdownOperationComponent from "./DropdownOperationComponent";
export default {
  name: "FileListComponent",
  components: {
    DropdownOperationComponent,
  },
  data() {
    return {
      pageNo: 0,
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
    downLoad() {
      alert('下载')
    },
    deleteFile() {
      alert('删除')
    },
    formatTime(updateTime) {
      return updateTime == null ? new Date() : updateTime
    },
    formatSize(totalSize) {
      return (totalSize == null ? 0 : totalSize / 1024).toFixed(2) + "MB";
    },
    handleCurrentChange(val) {
      this.pageNo = val
      this.handleChange()
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.handleChange()
    },
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
      this.pageNo = 0
      this.pageSize = 10
      this.total = 0
      this.keyWord = ''
    }
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

</style>
