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
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="名称"
          hasFeedback >
          <a-input placeholder="请输入名称" v-decorator="['name', {}]" :value="" />
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
          <a-input placeholder="请输入设计人员" v-decorator="['designer', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="单位">
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
          <a-input placeholder="请输入描述" v-decorator="['description', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="接口描述文件"
          hasFeedback >
          <a-input placeholder="请输入接口描述文件" v-decorator="['email', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="开发人员"
          hasFeedback >
          <a-input placeholder="请输入开发人员" v-decorator="['developer', {}]" />
        </a-form-item>
		
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import moment from "moment"

  export default {
    name: "ServiceRegisterModal",
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

        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules:{
        },
        url: {
          add: "/test/jantdDemo/add",
          edit: "/test/jantdDemo/edit",
        },
        formValue:{
          name: "",
          serviceLabel:"",
          designer:"",
          designerDepartment:"",
          contactInformation:"",
          description:"",
          interfaceDescriptionFileId:"",
          developer:""
        }
      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'name','serviceLabel','designer','designerDepartment','contactInformation','description','developer'))
        });
      },
      close () {
        this.$emit('close');
        this.visible = false;
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
      }
    }
  }
</script>

<style scoped>

</style>