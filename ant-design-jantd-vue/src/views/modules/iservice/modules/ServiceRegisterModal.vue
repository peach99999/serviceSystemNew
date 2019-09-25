<template>
  <a-modal
    :title="title"
    :width="800"
    :visible="visible"
    :confirmLoading="confirmLoading"
    :maskClosable = "false"
    @cancel="handleClose"
    >
    <template slot="footer">
      <a-button key="back" :loading="loading" @click="handleSave">保存</a-button>
      <a-button key="submit" type="primary" :loading="loading" @click="handleSubmit(1)">
        提交
      </a-button>
    </template>
    
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="名称"
          hasFeedback >
          <a-input placeholder="请输入名称" v-decorator="[ 'name', validatorRules.name]"/>
        </a-form-item>
        <a-form-item
        :label-col="labelCol"
        :wrapper-col="wrapperCol"
        label="标签">
        <div style="float: left">
            <template v-for="(tag, index) in serviceLabel">
              <a-tooltip v-if="tag.length > 20" :key="tag" :title="tag">
                <a-tag
                  :key="tag"
                  :closable="index !== 0"
                  :afterClose="() => handleTagClose(tag)"
                >{{ `${tag.slice(0, 20)}...` }}
                </a-tag>
              </a-tooltip>
              <a-tag
                v-else
                :key="tag"
                :closable="index !== 0"
                :afterClose="() => handleTagClose(tag)"
              >{{ tag }}
              </a-tag>
            </template>
            <a-input
              v-if="tagInputVisible"
              ref="tagInput"
              type="text"
              size="small"
              :style="{ width: '78px' }"
              :value="tagInputValue"
              @change="handleInputChange"
              @blur="handleTagInputConfirm"
              @keyup.enter="handleTagInputConfirm"
            />
            <a-tag v-else @click="showTagInput" style="background: #fff; borderStyle: dashed;">
              <a-icon type="plus"/>
              New Tag
            </a-tag>
        </div>
      </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="设计人员"
          hasFeedback >
          <a-input placeholder="请输入设计人员" v-decorator="['designer', validatorRules.designer]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="单位"
          hasFeedback>
          <a-input placeholder="请输入单位" v-decorator="['designerDepartment', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="联系方式"
          hasFeedback >
          <a-input placeholder="请输入联系方式" v-decorator="['contactInformation', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="描述"
          hasFeedback >
          <a-input placeholder="请输入描述" v-decorator="['description', validatorRules.description]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="接口描述文件">
          <a-row type="flex">
            <a-col :span="21">
              <a-input placeholder="请上传接口描述文件" v-decorator="['interfaceDescriptionFileName', validatorRules.interfaceDescriptionFileId]"/>
            </a-col>
            <a-col :span="3">
              <JUpload @input="handleUploadSuccess"></JUpload>
            </a-col>
          </a-row>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="开发人员"
          hasFeedback >
          <a-input placeholder="请输入开发人员" v-decorator="['developer', validatorRules.developer]" />
        </a-form-item>
		
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import moment from "moment"
  import JUpload from '@/components/jantd/JUpload'

  export default {
    name: "ServiceRegisterModal",
    components: {
      JUpload
    },
    data () {
      return {
        title:"操作",
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        wrapperCol1: {
          xs: { span: 24 },
          sm: { span: 12 },
        },

        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules:{
        },
        url: {
          add: '/serviceInfo/add',
          edit: "/serviceInfo/edit-register",
        },
        validatorRules:{
          name:{
            rules: [
              { required: true, message: '请输入名称!' },
            ]},
          designer:{
            rules: [
              { required: true, message: '请输入设计人员!' },
            ]},
          description:{
            rules: [
              { required: true, message: '请输入描述!' },
              { min: 0, max: 64, message: '长度不超过 64 个字符', trigger: 'blur' },
            ]},
          interfaceDescriptionFileName:{
            rules: [
              { required: true, message: '请上传接口描述文件!' },
            ]},
          developer:{
            rules: [
              { required: true, message: '请输入开发人员!' },
            ]},
        },
        serviceLabel:[],
        tagInputVisible: false,
        tagInputValue:'',
        categoryId:'',
        uploadFilePath:'',
        loading:false
      }
    },
    created(){
    },
    mounted(){
    },
    methods: {
      add (categoryId) {
        this.categoryId = categoryId
        this.edit({});
      },
      // 附件上传回调
      handleUploadSuccess(value){
        console.log(value)
        this.interfaceDescriptionFilePath = ''
        let num = value.lastIndexOf('/')+1
        let name = value.substring(num)
        let fileName = name.split('_')[0]
        let fileType = name.split('.')[1]
        this.interfaceDescriptionFilePath = value;
        this.form.setFieldsValue({interfaceDescriptionFileName:fileName+'.'+fileType})
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'name','interfaceDescriptionFileName','designer','designerDepartment','contactInformation','description','developer'))
          if(record.serviceLabel){
            this.serviceLabel = record.serviceLabel.split(',');
            this.categoryId = record.categoryId;
          }
        });
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleSubmit (designerStatus) {
        if(this.serviceLabel == ''){
          this.$message.warning("请输入标签");
          return;
        }
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.loading = true;
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            formData.categoryId = this.categoryId;
            formData.serviceLabel = this.serviceLabel.join(",");
            formData.designerStatus = designerStatus;
            formData.interfaceDescriptionFilePath= this.interfaceDescriptionFilePath;
            console.log(formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.loading = false;
              that.close();
            })

          }
        })
      },
      handleClose(){
        this.close()
      },
      handleSave () {
        if(this.handleSubmit(0)){
          this.close()
        }
      },
      showTagInput() {
        this.tagInputVisible = true
        this.$nextTick(() => {
          this.$refs.tagInput.focus()
        })
      },
      handleTagClose (removedTag) {
      const serviceLabel = this.serviceLabel.filter(tag => tag !== removedTag)
      console.log(serviceLabel)
      this.serviceLabel = serviceLabel
    },
      handleInputChange(e) {
        this.tagInputValue = e.target.value
      },
       handleTagInputConfirm() {
        const inputValue = this.tagInputValue
        let serviceLabel = this.serviceLabel
        if (inputValue && !serviceLabel.includes(inputValue)) {
          serviceLabel = [...serviceLabel, inputValue]
        }

        Object.assign(this, {
          serviceLabel,
          tagInputVisible: false,
          tagInputValue: ''
        })
      },
    }
  }
</script>

<style scoped>

</style>