<template>
  <div id="components-layout-demo-basic">
    <a-form>
      <a-form-item
        :label-col="labelCol"
        :wrapper-col="wrapperCol"
        label="名称"
      >
        <a-input placeholder="请输入名称"/>
      </a-form-item>

      <a-form-item
        :label-col="labelCol"
        :wrapper-col="wrapperCol"
        label="标签"
      >
        <div style="float: left">
            <template v-for="(tag, index) in tags">
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
        <a-row type="flex">
          <a-col :span="2" :offset="8">
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
      };
    },
    methods: {
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