<template>
  <div style="text-align: center">
    <search-component></search-component>
    <div style="height: 30px"></div>
    <file-list-component></file-list-component>
  </div>
</template>

<script>
import SearchComponent from "./SearchComponent";
import FileListComponent from "./FileListComponent";
export default {
  name: "SearchFileList",
  components: {
    FileListComponent,
    SearchComponent
  }
}
</script>

<style scoped>

</style>
