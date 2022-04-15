<template>
  <div style="display: flex; justify-content: center">
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="120px"  class="demo-ruleForm">
      <el-form-item label="存储地址" prop="folderPath">
        <el-input v-model="ruleForm.folderPath" style="width: 200px"></el-input>
      </el-form-item>
      <el-form-item label="存储类型" prop="storeType">
        <el-select v-model="ruleForm.storeType" placeholder="请选择存储类型">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="是否分片存储" prop="shouldMerge">
        <el-switch v-model="ruleForm.shouldMerge"></el-switch>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">保存</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "StrategyComponent",
  data() {
    return {
      options: [{
        label: '本地存储',
        value: 'LOCAL'
      }, {
        label: 'OSS存储',
        value: 'OSS'
      }],
      ruleForm: {
        folderPath: '',
        storeType: '',
        shouldMerge: false,
      },
      rules: {
        storeType: [
          { required: true, message: '请选择存储类型', trigger: 'change' }
        ],
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          axios.post('http://110.40.220.211:8989/strategy/save', {
            shouldMerge: this.ruleForm.shouldMerge,
            folderPath: this.ruleForm.folderPath,
            storeType: this.ruleForm.storeType,
          }).then((res) => {
            if (res.data.success) {
              this.messages('保存成功', 'success')
            } else {
              this.messages(res, 'error')
            }
          }).catch(function (error) {
            console.log(error)
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    messages(message, type) {
      this.$message({
        message: message,
        type: type
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  },
  created: function () {
    axios.get('http://110.40.220.211:8989/strategy/query')
    .then((res) => {
      if (res.data.success) {
        let result = res.data.data
        this.ruleForm.shouldMerge = result.shouldMerge
        this.ruleForm.folderPath = result.folderPath
        this.ruleForm.storeType = result.storeType == null ? '本地存储' : result.storeType
      }
    }).catch(err => console.log(err))
  }
}
</script>

<style scoped>
</style>
