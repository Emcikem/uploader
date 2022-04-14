<template>
  <div style="text-align: center">
    <el-image
      style="width: 120px; height: 187px"
      :src="url"></el-image>
    <div>
      <el-input
        v-model="searchWord"
        placeholder="请输入内容"
        @change="searchFile"
        style="width: 400px">
      </el-input>
      <el-button
        icon="el-icon-search"
        type="primary"
        style="margin-left: -6px"
        @click="searchFile"
        plain>搜索</el-button>
    </div>
  </div>
</template>

<script>
import Bus from './bus'
export default {
  name: "SearchComponent",
  data() {
    return {
      searchWord: '',
      url: 'https://upload-bbs.mihoyo.com/upload/2021/09/27/193478274/f1ddfac8718873f18c9c37aa679b0ebf_5098519949483858668.gif?x-oss-process=image//auto-orient,0/interlace,1/format,gif'
    }
  },
  methods: {
    searchFile() {
      Bus.$emit('deliverSearchWord', this.searchWord)
    }
  }
}
</script>

<style scoped>
</style>
