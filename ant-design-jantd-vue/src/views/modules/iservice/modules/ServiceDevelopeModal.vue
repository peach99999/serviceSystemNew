<template>
  <a-modal
    :title="title"
    :width="800"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">

    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item
          :label-col="labelCol"
          :wrapper-col="wrapperCol"
          label="服务名称"
        >
          <a-input placeholder="请输入名称" :value="formValue.name" v-decorator="['name', {}]"/>
        </a-form-item>

        <a-form-item
          :label-col="labelCol"
          :wrapper-col="wrapperCol"
          label="标签"
        >
          <div style="float: left">
            <template v-for="(tag, index) in formValue.serviceLabel">
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
          :label-col="labelCol"
          :wrapper-col="wrapperCol"
          label="服务开发人员"
        >
          <a-input :value="formValue.developerSubmissionUser" v-decorator="['developerSubmissionUser', {}]"/>
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
          label="服务实现"
        >
          <a-row type="flex">
            <a-col :span="21">
              <a-input :value="formValue.serviceImplementationFileId" v-decorator="['serviceImplementationFileId', {}]"/>
            </a-col>
            <a-col :span="3">
              <a-upload
                v-decorator="['upload', {
          valuePropName: 'fileList',
          getValueFromEvent: normFile,
        }]"
                name="logo"
                action="/upload.do"
                list-type="picture"
                showUploadList="false"
              >
                <a-button>
                  <a-icon type="upload"/>
                  浏览
                </a-button>
              </a-upload>
            </a-col>
          </a-row>
        </a-form-item>

        <a-form-item
          :label-col="labelCol"
          :wrapper-col="wrapperCol"
          label="用户文档"
        >
          <a-row type="flex">
            <a-col :span="21">
              <a-input :value="formValue.userManualFileId" v-decorator="['userManualFileId', {}]"/>
            </a-col>
            <a-col :span="3">
              <a-upload
                v-decorator="['upload', {
          valuePropName: 'fileList',
          getValueFromEvent: normFile,
        }]"
                name="logo"
                action="/upload.do"
                list-type="picture"
                showUploadList="false"
              >
                <a-button>
                  <a-icon type="upload"/>
                  浏览
                </a-button>
              </a-upload>
            </a-col>
          </a-row>
        </a-form-item>


        <a-form-item
          :label-col="labelCol"
          :wrapper-col="wrapperCol"
          label="使用案例"
        >
          <a-row type="flex">
            <a-col :span="21">
              <a-input :value="formValue.demoFileId" v-decorator="['demoFileId', {}]"/>
            </a-col>
            <a-col :span="3">
              <a-upload
                v-decorator="['upload', {
          valuePropName: 'fileList',
          getValueFromEvent: normFile,
        }]"
                name="logo"
                action="/upload.do"
                list-type="picture"
                showUploadList="false"
              >
                <a-button>
                  <a-icon type="upload"/>
                  浏览
                </a-button>
              </a-upload>
            </a-col>
          </a-row>
        </a-form-item>


        <a-form-item
          :label-col="labelCol"
          :wrapper-col="wrapperCol"
          label="最小实例数"
        >
          <a-input :value="formValue.minInstance" v-decorator="['minInstance', {}]"/>
        </a-form-item>
        <a-form-item
          :label-col="labelCol"
          :wrapper-col="wrapperCol"
          label="最大实例数"
        >
          <a-input :value="formValue.maxInstance" v-decorator="['maxInstance', {}]"/>
        </a-form-item>
        <!--<a-form-item>-->
        <!--<a-row type="flex" >-->
        <!--<a-col :span="2" :offset="8">-->
        <!--<a-button type="primary" @click="onSubmit">提交</a-button>-->
        <!--</a-col>-->
        <!--<a-col :span="2">-->
        <!--<a-button @click="onSave">保存</a-button>-->
        <!--</a-col>-->
        <!--</a-row>-->
        <!--</a-form-item>-->
      </a-form>
    </a-spin>
  </a-modal>
</template>
<script>
  import ARow from "ant-design-vue/es/grid/Row";
  import './ServiceRegisterModal.less'

  export default {
    components: {ARow},
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
        tagInputVisible: false,
        tags: [],
        tagInputValue: '',
        visible: false,
        form: this.$form.createForm(this),
        confirmLoading: false,
        formValue:{
          name:"1",
          serviceLabel:"1",
          developerSubmissionUser:"1",
          serviceImplementationFileId: "1",
          userManualFileId: "1",
          demoFileId: "1",
          minInstance: "1",
          maxInstance: "1"
        }
      };
    },
    methods: {
      add() {
        this.edit({});
      },
      edit(record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
      },
      showTagInput() {
        this.tagInputVisible = true
        this.$nextTick(() => {
          this.$refs.tagInput.focus()
        })
      },
      handleInputChange(e) {
        this.tagInputValue = e.target.value
      },
      handleTagInputConfirm() {
        const inputValue = this.tagInputValue
        let tags = this.tags
        if (inputValue && !tags.includes(inputValue)) {
          tags = [...tags, inputValue]
        }

        Object.assign(this, {
          tags,
          tagInputVisible: false,
          tagInputValue: ''
        })
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
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
            //时间格式化
            formData.punchTime = formData.punchTime?formData.punchTime.format('YYYY-MM-DD HH:mm:ss'):null;
            formData.birthday = formData.birthday?formData.birthday.format():null;

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