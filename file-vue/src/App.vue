<template>
  <div>
    <!--    <uploaderComponent></uploaderComponent>-->
    <!--    <div style="height: 100px"></div>-->
    <SearchFileList></SearchFileList>
<!--    <div style="height: 300px"></div>-->
<!--    <strategy-component></strategy-component>-->
  </div>
</template>

<script>
import uploaderComponent from "./UploaderComponent";
import fileListComponent from "./FileListComponent";
import StrategyComponent from "./StrategyComponent";
import SearchComponent from "./SearchComponent";
import SearchFileList from "./SearchFileList";
export default {
  name: 'app',
  components: {
    uploaderComponent,
    fileListComponent,
    StrategyComponent,
    SearchComponent,
    SearchFileList,
  },
  data () {
    return {
      msg: 'Welcome to Your Vue.js App'
    }
  }
}
</script>

<style>

</style>
