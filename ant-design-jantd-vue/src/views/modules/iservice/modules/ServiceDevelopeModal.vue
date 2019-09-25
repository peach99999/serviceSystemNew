<template>
  <a-modal
    :title="title"
    :width="800"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @cancel="handleCancel">
    <template slot="footer">
      <a-button key="submit" :loading="loading" type="primary" @click="handleOk">保存</a-button>
    </template>
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item
          :label-col="labelCol"
          :wrapper-col="wrapperCol"
          label="服务名称"
          hasFeedback>
          <a-input placeholder="请输入名称" v-decorator="['name', {}]" disabled/>
        </a-form-item>

        <a-form-item
          :label-col="labelCol"
          :wrapper-col="wrapperCol"
          label="标签"
        >
          <div style="float: left">
            <template v-for="(tag, index) in serviceLabel">
              <a-tooltip v-if="tag.length > 20" :key="index" :title="tag">
                <a-tag
                  :key="tag"
                >{{ `${tag.slice(0, 20)}...` }}
                </a-tag>
              </a-tooltip>
              <a-tag
                v-else
                :key="tag"
              >{{ tag }}
              </a-tag>
            </template>
          </div>
        </a-form-item>

        <a-form-item
          :label-col="labelCol"
          :wrapper-col="wrapperCol"
          label="服务开发人员">
          <a-input v-decorator="['developer', {}]" disabled/>
        </a-form-item>
        <a-row type="flex" style="margin-top: 20px;margin-bottom: 20px">
          <a-col :span="5" :offset="5">
            <a-button type="primary">服务调用代码框架</a-button>
          </a-col>
          <a-col :span="5">
            <a-button type="primary">服务实现代码框架</a-button>
          </a-col>
        </a-row>

        <a-form-item
          :label-col="labelCol"
          :wrapper-col="wrapperCol"
          label="服务实现">
          <a-row type="flex">
            <a-col :span="21">
              <a-input placeholder="请上传服务实现文档" v-decorator="['serviceImplementationFileName', validatorRules.serviceImplementationFileName]"/>
            </a-col>
            <a-col :span="3">
              <JUpload @input="handleUploadServiceImplementationFileSuccess"></JUpload>
            </a-col>
          </a-row>
        </a-form-item>

        <a-form-item
          :label-col="labelCol"
          :wrapper-col="wrapperCol"
          label="用户文档">
          <a-row type="flex">
            <a-col :span="21">
              <a-input placeholder="请上传用户文档" v-decorator="['userManualFileName', validatorRules.userManualFileName]"/>
            </a-col>
            <a-col :span="3">
              <JUpload @input="handleUploadUserManualFileSuccess"></JUpload>
            </a-col>
          </a-row>
        </a-form-item>
        <a-form-item
          :label-col="labelCol"
          :wrapper-col="wrapperCol"
          label="使用案例">
          <a-row type="flex">
            <a-col :span="21">
              <a-input placeholder="请上传使用案例" v-decorator="['demoFileName', validatorRules.demoFileName]"/>
            </a-col>
            <a-col :span="3">
              <JUpload @input="handleUploadDemoFileSuccess"></JUpload>             
            </a-col>
          </a-row>
        </a-form-item>
        <a-form-item
          :label-col="labelCol"
          :wrapper-col="wrapperCol"
          label="最小实例数"
          hasFeedback>
          <a-input type="number" min='0' oninput="javascript:this.value=this.value.replace(/[^\d]/g,'')" placeholder="请输入最小实例数" v-decorator="['minInstance', validatorRules.minInstance]"/>
        </a-form-item>
        <a-form-item
          :label-col="labelCol"
          :wrapper-col="wrapperCol"
          label="最大实例数"
          hasFeedback>
          <a-input type="number" min='0' oninput="javascript:this.value=this.value.replace(/[^\d]/g,'')" placeholder="请输入最大实例数" v-decorator="['maxInstance', validatorRules.maxInstance]"/>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>
<script>
  import ARow from "ant-design-vue/es/grid/Row";
  import './ServiceRegisterModal.less'
  import pick from 'lodash.pick'
  import JUpload from '@/components/jantd/JUpload'
  import { httpAction } from '@/api/manage'

  export default {
    components: {ARow,JUpload},
    data() {
      return {
        labelCol: {
          xs: {span: 24},
          sm: {span: 5},
        },
        wrapperCol: {
          xs: {span: 24},
          sm: {span: 12},
        },
        visible: false,
        form: this.$form.createForm(this),
        confirmLoading: false,
        serviceLabel:[],
        validatorRules:{
          serviceImplementationFileName:{
            rules: [
              { required: true, message: '请上传服务实现文档!' },
            ]},
          userManualFileName:{
            rules: [
              { required: true, message: '请上传用户文档!' },
            ]},
          demoFileName:{
            rules: [
              { required: true, message: '请上传用户案例文档!' },
            ]},
          minInstance:{
            rules: [
              { required: true, message: '请输入最小实例数!' },
            ]},
          maxInstance:{
            rules: [
              { required: true, message: '请输入最大实例数!' },
            ]},
        },
        serviceImplementationFilePath:'',
        userManualFilePath:'',
        demoFilePath:'',
        loading:false,
        url: {
          edit: "/serviceInfo/edit-developer",
        },
      };
    },
    methods: {
      // 服务实现附件上传回调
      handleUploadServiceImplementationFileSuccess(value){
        this.serviceImplementationFilePath = value
        this.form.setFieldsValue({serviceImplementationFileName:this.handleFileName(value)})
      },
      // 用户文档附件上传回调
      handleUploadUserManualFileSuccess(value){
        this.userManualFilePath = value;
        this.form.setFieldsValue({userManualFileName:this.handleFileName(value)})
      },
      // 使用案例附件上传回调
      handleUploadDemoFileSuccess(value){
        this.demoFilePath = value
        this.form.setFieldsValue({demoFileName:this.handleFileName(value)})
      },
      handleFileName(value){
        console.log(value)
        let fileName = '';
        this.interfaceDescriptionFilePath = ''
        let num = value.lastIndexOf('/')+1
        let name = value.substring(num)
        if(name.split('_').length > 2){
          let count = name.lastIndexOf('_')
          fileName = name.substring(0,count)
        }else{
          fileName = name.split('_')[0]
        }
        let fileType = name.split('.')[1]
        return fileName+'.'+fileType
      },
      add() {
        this.edit({});
      },
      edit(record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'name','developer','serviceImplementationFileName','userManualFileName','demoFileName','minInstance','maxInstance'))
          if(record.serviceLabel){
            this.serviceLabel = record.serviceLabel.split(',');
          }
        });
      },
      handleOk () {
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
            formData.serviceImplementationFilePath = this.serviceImplementationFilePath;
            formData.userManualFilePath = this.userManualFilePath;
            formData.demoFilePath = this.demoFilePath;
            formData.developerStatus = "0";
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
      handleCancel () {
        this.close()
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
    }
  };
</script>
<style>
  #components-layout-demo-basic {
    text-align: center;
    background-color: #fff;
    padding: 10px;
  }

  #components-layout-demo-basic .ant-layout-header,
  #components-layout-demo-basic .ant-layout-footer {
    background: #fff;
    color: #fff;
  }

  #components-layout-demo-basic .ant-layout-footer {
    line-height: 1.5;
  }

  #components-layout-demo-basic .ant-layout-sider {
    background: #fff;
    color: #fff;
    /*line-height: 120px;*/
  }

  #components-layout-demo-basic .ant-layout-content {
    background: #fff;
    color: #fff;
    min-height: 120px;
    line-height: 120px;
  }

  #components-layout-demo-basic > .ant-layout {
    margin-bottom: 48px;
  }

  #components-layout-demo-basic > .ant-layout:last-child {
    margin: 0;
  }
</style>