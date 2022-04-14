<template>
  <div>
    <uploaderComponent></uploaderComponent>
    <div style="height: 100px"></div>
    <SearchComponent></SearchComponent>
    <div style="height: 20px"></div>
    <file-list-component></file-list-component>
    <div style="height: 300px"></div>
    <strategy-component></strategy-component>
  </div>
</template>

<script>
import uploaderComponent from "./UploaderComponent";
import fileListComponent from "./FileListComponent";
import StrategyComponent from "./StrategyComponent";
import SearchComponent from "./SearchComponent";
export default {
  name: 'app',
  components: {
    uploaderComponent,
    fileListComponent,
    StrategyComponent,
    SearchComponent,
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
