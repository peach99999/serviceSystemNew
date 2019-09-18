<template>
  <div id="components-layout-demo-basic">
    <a-form>
      <a-form-item
        :label-col="labelCol"
        :wrapper-col="wrapperCol"
        label="名称"
        validate-status="error"
      >
        <a-input placeholder="请输入名称"/>
      </a-form-item>

      <a-form-item
        :label-col="labelCol"
        :wrapper-col="wrapperCol"
        label="标签"
      >
        <a-input placeholder="标签"/>
      </a-form-item>

      <a-form-item
        :label-col="labelCol"
        :wrapper-col="wrapperCol"
        label="设计人员"
      >
        <a-input/>
      </a-form-item>

      <a-form-item
        :label-col="labelCol"
        :wrapper-col="wrapperCol"
        label="单位"
      >
        <a-input/>
      </a-form-item>

      <a-form-item
        :label-col="labelCol"
        :wrapper-col="wrapperCol"
        label="联系方式"
      >
        <a-input/>
      </a-form-item>

      <a-form-item
        :label-col="labelCol"
        :wrapper-col="wrapperCol"
        label="描述"
      >
        <a-input/>
      </a-form-item>

      <a-form-item
        :label-col="labelCol"
        :wrapper-col="wrapperCol"
        label="接口描述文件"
      >
        <a-row type="flex">
          <a-col :span="21">
            <a-input/>
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
        label="开发人员"
      >
        <a-input/>
      </a-form-item>
      <a-form-item>
        <a-row type="flex" justify="center">
          <a-col :span="2">
            <a-button type="primary" @click="onSubmit">提交</a-button>
          </a-col>
          <a-col :span="2">
            <a-button @click="onSave">保存</a-button>
          </a-col>
        </a-row>
      </a-form-item>
    </a-form>
  </div>
</template>
<script>
  import ARow from "ant-design-vue/es/grid/Row";
  import './ServiceRegisterDetail.less'

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
      };
    },
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