<template>
  <a-modal
    :title="title"
    :width="800"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @cancel="handleCancel">
    <template slot="footer">
      <a-button key="remove" :loading="loading"  @click="remove">移除</a-button>
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
          <a-input v-model="realName" disabled/>
        </a-form-item>
        <a-row type="flex" style="margin-top: 20px;margin-bottom: 20px">
          <a-col :span="5" :offset="5">
            <a-button type="primary" @click="downLoadServiceCallFrame">服务调用代码框架</a-button>
          </a-col>
          <a-col :span="5">
            <a-button type="primary" @click="downLoadServiceImplementFrame">服务实现代码框架</a-button>
          </a-col>
        </a-row>

        <a-form-item
          :label-col="labelCol"
          :wrapper-col="downWrapperCol"
          label="服务实现">
          <a-row type="flex">
            <a-col :span="16">
              <a-input placeholder="请上传服务实现文档" v-decorator="['serviceImplementationFileName', validatorRules.serviceImplementationFileName]"/>
            </a-col>
            <a-col :span="5">
              <JZIPUpload style="margin-left:10px" @input="handleUploadServiceImplementationFileSuccess"></JZIPUpload>
            </a-col>
            <a-col :span="3">
              <a-button icon="download" @click="downLoadImplementationFile">下载</a-button>
            </a-col>
          </a-row>
        </a-form-item>

        <a-form-item
          :label-col="labelCol"
          :wrapper-col="downWrapperCol"
          label="用户文档">
          <a-row type="flex">
            <a-col :span="16">
              <a-input placeholder="请上传用户文档" v-decorator="['userManualFileName', {}]"/>
            </a-col>
            <a-col :span="5">
              <JUpload style="margin-left:10px" @input="handleUploadUserManualFileSuccess"></JUpload>
            </a-col>
            <a-col :span="3">
              <a-button icon="download" @click="dowmLoadUserManualFile">下载</a-button>
            </a-col>
          </a-row>
        </a-form-item>
        <a-form-item
          :label-col="labelCol"
          :wrapper-col="downWrapperCol"
          label="使用案例">
          <a-row type="flex">
            <a-col :span="16">
              <a-input placeholder="请上传使用案例" v-decorator="['demoFileName', {}]"/>
            </a-col>
            <a-col :span="5">
              <JUpload style="margin-left:10px" @input="handleUploadDemoFileSuccess"></JUpload>             
            </a-col>
            <a-col :span="3">
              <a-button icon="download" @click="downLoadDemoFile">下载</a-button>
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
  import omit from 'lodash.omit'
  import JUpload from '@/components/jantd/JUpload'
  import JZIPUpload from '@/components/jantd/JZIPUpload'
  import { httpAction } from '@/api/manage'
  import { mapActions, mapGetters } from 'vuex'

  export default {
    components: {ARow,JUpload,JZIPUpload},
    data() {
      return {
        title:'',
        labelCol: {
          xs: {span: 24},
          sm: {span: 5},
        },
        wrapperCol: {
          xs: {span: 24},
          sm: {span: 12},
        },
        downWrapperCol: {
          xs: {span: 24},
          sm: {span: 15},
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
              { validator: this.minInstanceValidate }
            ]},
          maxInstance:{
            rules: [
              { required: true, message: '请输入最大实例数!' },
              { validator: this.maxInstanceValidate }
            ]},
        },
        serviceImplementationFilePath:'',
        userManualFilePath:'',
        demoFilePath:'',
        loading:false,
        url: {
          edit: "/serviceInfo/edit-developer",
        },
        realName:'',
        serviceCallFramePath:'',
        serviceImplementFramePath:''
      };
    },
    methods: {
      ...mapGetters(["nickname"]),
      downLoadServiceCallFrame(){
        this.handleDownload(this.serviceCallFramePath)
      },
      downLoadServiceImplementFrame(){
        this.handleDownload(this.serviceImplementFramePath)
      },
      // 服务实现附件上传回调
      handleUploadServiceImplementationFileSuccess(value){
        this.serviceImplementationFilePath = value
        this.form.setFieldsValue({serviceImplementationFileName:this.handleFileName(value)})
      },
      // 服务实现附件下载
      downLoadImplementationFile(){
        this.handleDownload(this.serviceImplementationFilePath)
      },
      // 下載
      handleDownload(path){
        console.log(path)
        if(!path){
        this.$message.warning("请上传后再下载!")
          return;
        }
        if(path.indexOf(",")>0){
          path = path.substring(0,path.indexOf(","))
        }
        window.open(window._CONFIG['domianURL'] + "/sys/common/download/"+path);
      },
      // 用户文档附件上传回调
      handleUploadUserManualFileSuccess(value){
        this.userManualFilePath = value;
        this.form.setFieldsValue({userManualFileName:this.handleFileName(value)})
      },
      dowmLoadUserManualFile(){
        this.handleDownload(this.userManualFilePath)
      },
      // 使用案例附件上传回调
      handleUploadDemoFileSuccess(value){
        this.demoFilePath = value
        this.form.setFieldsValue({demoFileName:this.handleFileName(value)})
      },
      downLoadDemoFile(){
        this.handleDownload(this.demoFilePath)
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
        this.realName = this.nickname();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.serviceImplementationFilePath = record.serviceImplementationFilePath
        this.userManualFilePath = record.userManualFilePath
        this.demoFilePath = record.demoFilePath
        this.serviceCallFramePath = record.serviceCallFramePath
        this.serviceImplementFramePath = record.serviceCallFramePath
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'name','developer','serviceImplementationFileName','userManualFileName','demoFileName','minInstance','maxInstance'))
          if(record.serviceLabel){
            this.serviceLabel = record.serviceLabel.split(',');
          }
          if(!record.minInstance){
            this.form.setFieldsValue({minInstance:1,maxInstance:4})
          }
        });
      },
      remove(){
        if(!this.model.minInstance){
          this.$message.warning("保存后方可移除!")
          return;
        }
        const that = this;
        that.loading = true;
        that.confirmLoading = true;
        let formData = omit(this.model,'developer','serviceImplementationFileName','serviceImplementationFilePath','userManualFileName','userManualFilePath','demoFileName','demoFilePath','minInstance','maxInstance','developerStatus','developerSubmissionTime','developerSubmissionUser','developerSubmissionUserId')
        console.log(formData)
        let httpurl = this.url.edit;
        let method = 'put';
        
        httpAction(httpurl,formData,method).then((res)=>{
          if(res.success){
            that.$message.success("移除成功!");
            that.$emit('ok');
          }else{
            that.$message.warning(res.message);
          }
        }).finally(() => {
          that.confirmLoading = false;
          that.loading = false;
          that.close();
        })
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
            formData.developer = this.realName
            if(this.serviceImplementationFilePath != ''){
                formData.serviceImplementationFilePath = this.serviceImplementationFilePath;
            }
            if(this.userManualFilePath != ''){
              formData.userManualFilePath = this.userManualFilePath;
            }
            if(this.demoFilePath != ''){
              formData.demoFilePath = this.demoFilePath;
            }
            formData.developerStatus = "0";
            console.log(formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success("保存成功!");
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
      minInstanceValidate(rule,value,callback){
        let maxInstance = this.form.getFieldValue("maxInstance")
        console.log(maxInstance,value)
        if(!value || !maxInstance){
          callback()
        }else if(parseInt(value)<parseInt(maxInstance)){
          callback()
        }else{
          callback("最小实例数不能大于最大实例数!")
        }
      },
      maxInstanceValidate(rule,value,callback){
        let minInstance = this.form.getFieldValue("minInstance")
        console.log(minInstance,value)
        if(!value || !minInstance){
          callback()
        }else if(parseInt(minInstance)<parseInt(value)){
          callback()
        }else{
          callback("最大实例数不能小于最小实例数!")
        }
      }
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