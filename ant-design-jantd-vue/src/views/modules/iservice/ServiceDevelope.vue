@<template>
  <div id="components-layout-demo-basic">
    <a-row type="flex">
      <a-col :md="10" :sm="12" :offset="5">
          <a-input-search placeholder="搜索你想要的服务" v-model="queryParam.name" @search="searchQuery" enterButton="搜索"/>
      </a-col>
      <a-col :md="1" :sm="1">
          <a-button type="primary"  @click="searchReset" icon="reload" style="margin-left: 8px;left: 10px">重置</a-button>
      </a-col>
    </a-row>
    <a-layout style="margin-top: 10px">
      <a-layout-sider>
        <!-- <a-tree
          showLine
          :treeData="treeData"
          @select="this.onSelect"
        >
        </a-tree> -->
        <a-list itemLayout="horizontal" :dataSource="treeData">
          <a-list-item slot="renderItem" slot-scope="item">
            <a-list-item-meta >
              <a slot="title" href="#" @click="handleClick(item.key)" >{{item.title}}</a>
              <img
                slot="avatar"
                width="50px" height="50px"
                :src="getAvatarView(item.url)"
                @click="handleClick(item.key)"
              />
            </a-list-item-meta>
          </a-list-item>
        </a-list>
      </a-layout-sider>
      <a-layout>
        <a-layout-content>
          <a-table 
            :dataSource="dataSource" 
            :show-header="false" 
            :fit="true" 
            :pagination="ipagination" 
            :loading="loading"
            @change="handleTableChange">
            <a-table-column min-width="140px">
              <template slot-scope="scope">
                <img :src="getAvatarView(scope.servicePhoto)" width="128px" height="128px">
              </template>
            </a-table-column>
            <a-table-column min-width="140px">
              <template  slot-scope="service">
                <a-row type="flex" justify="space-around" align="middle">
                  <a-col :span="6">
                    <a href="#" @click="developerService(service)" style="font-weight: bold;font-size: 16px; color:rgba(0, 0, 0, 0.65)" >
                       {{service.name}}
                    </a>
                    <span v-if="service.serviceStates" style="font-size: 10px;color: #cf1322">
                      {{service.serviceStates}}
                    </span>
                    <span v-else style="font-size: 10px;color: #cf1322">
                      {{service.designerStatus_dictText}}
                    </span>
                  </a-col>
                  <a-col :span="2" :offset="4">
                    <a-icon type="folder"/>
                    <a @click="uploadFile(service.interfaceDescriptionFilePath)" style="color:rgba(0, 0, 0, 0.65)">文件</a>
                  </a-col>
                  <a-col :span="2">
                    <a-icon type="copy"/>
                    <a @click="uploadFile(service.userManualFilePath)" style="color:rgba(0, 0, 0, 0.65)">文档</a>
                  </a-col>
                  <a-col :span="2">
                    <a-icon type="form"/>
                    <a @click="uploadFile(service.demoFilePath)" style="color:rgba(0, 0, 0, 0.65)">案例</a>
                  </a-col>
                  <a-col :span="2">
                    <a-icon type="laptop"/>
                    <a @click="uploadFile(service.serviceImplementationFilePath)" style="color:rgba(0, 0, 0, 0.65)">实现</a>
                  </a-col>
                  <a-col :span="2" >
                    <a-button type="primary" @click="developerService(service)" style="margin-left:5px">开发</a-button>
                  </a-col>
                  <a-col :span="2">
                    <a-button @click="submitDeveloperService(service)" type="primary" style="margin-left:5px">提交</a-button>
                  </a-col>
                  <a-col :span="2">
                    <a-popconfirm title="确定删除吗?" @confirm="() => removeService(service.id)">
                      <a-button type="primary" style="margin-left:5px">删除</a-button>
                    </a-popconfirm>
                  </a-col>
                  

                </a-row>
                <div class="divLine"/>
                <p class="text" >
                  <a href="#" @click="developerService(service)">{{service.description}}</a>
                </p>
                <div class="can_div">
                  <div class="can_left">标签：
                    <span v-if="service.serviceLabel">
                       <h4 v-for="tag in service.serviceLabel.split(',')" class="biaoshi" :key="tag">
                          {{tag}}
                       </h4>
                     </span>
                  </div>
                </div>
                <div class="subit">
                  开发人员：{{service.developer}} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  开发时间： {{service.developerSubmissionTime == null?'':service.developerSubmissionTime.substring(0,10)}}
                </div>
              </template>
            </a-table-column>
          </a-table>
        </a-layout-content>
        <a-layout-footer>{{'12'}}</a-layout-footer>
      </a-layout>
    </a-layout>
    <!-- 表单区域 -->
    <ServiceDevelope-modal ref="modalForm" @ok="modalFormOk"></ServiceDevelope-modal>
    <servicePreview-modal ref="servicePreviewDetailForm"></servicePreview-modal>
  </div>
</template>
<script>
  import ATableColumn from "ant-design-vue/es/table/Column";
  import ARow from "ant-design-vue/es/grid/Row";
  import {querySerciceCategery,deleteServiceInfo,uploadServiceFile,submitService} from '@/api/api';
  import { deleteAction, postAction, getAction } from '@/api/manage';
  import {JantdListMixin} from '@/mixins/JantdListMixin';
  import ServiceDevelopeModal from './modules/ServiceDevelopeModal';
  import ServicePreviewModal from './modules/ServicePreviewModal';

  export default {
    components: {ARow, ATableColumn,ServiceDevelopeModal,ServicePreviewModal},
    mixins: [JantdListMixin],
    data() {
      return {
        treeData:[],
        dataSource: [],
        columns: [],
        categoryId: "",
        queryParam: {
          name:'',
          categoryId:this.categoryId
        },
        loading: false,
        ipagination: {
          current: 1,
          pageSize: 10,
          pageSizeOptions: ['10', '20', '30'],
          showTotal: (total, range) => {
            return range[0] + '-' + range[1] + ' 共' + total + '条'
          },
          showQuickJumper: true,
          showSizeChanger: true,
          total: 0
        },
        url: {
          list: '/serviceInfo/list-myself',
          edit: "/serviceInfo/edit-register",
          imgerver: window._CONFIG['domianURL']+"/sys/common/view",
        },
         /* 排序参数 */
        isorter:{
          column: 'createTime',
          order: 'desc',
        },
      }
    },
    methods: {
      handleClick(categoryId){
        console.log(categoryId)
        this.categoryId = categoryId;
        this.ipagination.current = 1
        this.getModelList();
      },
      getAvatarView(url){
        return this.url.imgerver +"/"+ url
      },
      // 服务详情
      showServiceDetail(record){
        if(record.serviceId == null){
          this.$message.warning('请先注册服务!');
        }else{
          this.$refs.servicePreviewDetailForm.detail(record);
          this.$refs.servicePreviewDetailForm.title = "服务详情";
        }
      },
      // 提交
      submitDeveloperService(record){
        console.log(record.minInstance)
        this.loading = true
        if(record.minInstance == null){
            this.$message.error("请先设置开发相关内容后再提交！")
            this.loading = false
            return;
        }
        // 1.上传服务实现文件获取文件id
        // 2.修改开发状态为已提交
        // 3.注册服务
        let param = {}
        param.fileUrl = record.serviceImplementationFilePath
        uploadServiceFile(param).then((res) => {
          if(res.success){
              // 服务注册
              let params = {
                id:record.id,
                minInstanceCount:record.minInstance,
                maxInstanceCount:record.maxInstance,
                fileId:res.result
              }
              submitService(params).then((res) => {
                if(res.success){
                  this.$message.success("服务提交成功！")
                  this.ipagination.current = 1
                  this.getModelList();
                }else{
                  this.loading = false
                  this.$message.warning(res.message);
                }
              })
            }else{
              this.loading = false
              this.$message.warning(res.message);
          }
        })
      },
      // 服务开发
      developerService (record) {
        this.$refs.modalForm.edit(record);
        this.$refs.modalForm.title = "开发明细";
      },
      // 删除服务
      removeService(serviceId){
        var that = this
          console.log(serviceId)
          deleteServiceInfo({id: serviceId}).then((res) => {
          if(res.success){
              that.$message.success(res.message);
              that.ipagination.current = 1
              that.getModelList();
            }else{
              that.$message.warning(res.message);
            }
        })
      },
      // 获取服务分类
      listServiceCategory() {
        querySerciceCategery().then((res) => {
          if (res.success) {
            console.log(res.result)
            res.result.forEach(data => {
              this.treeData.push({"title":data.name,"key":data.id,"url":data.servicePhoto})
            })
          }
        })
      },
      onSelect (selectedKeys, info) {
        console.log(selectedKeys[0])
        this.categoryId = selectedKeys[0];
        this.ipagination.current = 1
        this.getModelList();
        console.log(this.categoryId);
      },
      
      getModelList(pageNum) {
        //加载数据 若传入参数1则加载第一页的内容
        var param = Object.assign({}, this.queryParam,this.isorter)
        param.pageNo = this.ipagination.current
        param.pageSize = this.ipagination.pageSize
        param.categoryId = this.categoryId
        this.loading = true
        console.log(param)
        getAction(this.url.list, param).then((res) => {
          if (res.success) {
            console.log(res)
            this.dataSource = res.result.records
            this.ipagination.total = res.result.total
          }
          this.loading = false
        })
      },
    },
    mounted() {
      this.listServiceCategory();
    },
    watch: {
      '$route': 'getParams'
    },
    created: function () {
    }

  }

</script>
<style>
  .ant-list-item-meta-content {
    display: flex;
    align-self: center;
  }
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

  .m_left {
    width: 140px;
    height: 140px;
    float: left;
    margin-right: 15px;
  }

  .m_right {
    float: left;
    width: 520px;
  }

  .Biaoti {
    float: left;
    font-size: 18px;
    font-weight: bold;
  }

  .Status {
    float: left;
    font-size: 10px;
    color: #fe8a2d;
    margin-left: 5px;
  }

  .canshu {
    float: right;
    color: #999;
    font-size: 12px;
  }

  .canshu a:hover {
    color: #fe8a2d;
  }

  .canshu a {
    color: #999;
    text-decoration: none;
  }

  .text {
    width: 100%;
    display: block;
    float: left;
    height: 38px;
    line-height: 38px;
  }

  .text a {
    text-decoration: none;
  }

  .text a:hover {
    text-decoration: underline;
  }

  .can_div {
    width: 100%;
    display: block;
    float: left;
    height: 28px;
    line-height: 28px;
  }

  .can_left {
    float: left;
    color: #999999;
    font-size: 14px;
  }

  .can_right {
    float: right;
    color: #999999;
    font-size: 14px;
  }

  .biaoshi {
    background: #f0f0f0;
    display: inline;
    padding: 3px 10px;
    font-size: 12px;
    font-weight: normal;
    color: #999999;
  }

  .subit {
    width: 100%;
    color: #999;
    display: block;
    font-size: 14px;
    height: 28px;
    line-height: 28px;
    float: left;
  }
  .divLine {
    margin-bottom: 10px;
		border-bottom: 1px solid #f1f1f1;
  }
</style>