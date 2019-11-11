<template>
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
        <a-tree
          showLine
          :treeData="treeData"
          @select="this.onSelect"
        >
        </a-tree>
      </a-layout-sider>
      <a-layout>
        <a-layout-content>
          <a-table 
            :dataSource="dataSource" 
            :show-header="false" 
            :fit="true" 
            rowKey="id"
            :pagination="ipagination" 
            :loading="loading"
            @change="handleTableChange">
            <a-table-column min-width="140px">
              <template slot-scope="scope">
                <img :src="getAvatarView(scope.servicePhoto)" width="100px" height="130px">
              </template>
            </a-table-column>
            <a-table-column min-width="140px">
              <template slot-scope="service">
                <a-row type="flex" justify="space-around" align="middle">
                  <a-col :span="6">
                    <a href="#" @click="showServiceDetail(service)" style="font-weight: bold;font-size: 16px; color:rgba(0, 0, 0, 0.65)" >
                       {{service.name}}
                    </a>
                  </a-col>
                  <a-col :span="2" :offset="12">
                    <a-button type="primary" style="margin-left:5px" @click="deploy(service)">部署</a-button>
                  </a-col>
                  <a-col :span="2">
                    <a-button type="primary" style="margin-left:5px" @click="startService(service.serviceId)">启动</a-button>
                  </a-col>
                  <a-col :span="2">
                    <a-button type="primary" style="margin-left:5px" @click="stopService(service.serviceId)">停用</a-button>
                  </a-col>

                </a-row>
                <div class="divLine"/>
                <p class="text">
                  <a href="#" @click="showServiceDetail(service)">{{service.description}}</a>
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
                  部署时间： {{service.deploySubmissionTime == null?'':service.deploySubmissionTime.substring(0,10)}}
                </div>
              </template>
            </a-table-column>
          </a-table>
        </a-layout-content>
        <a-layout-footer>{{'12'}}</a-layout-footer>
      </a-layout>
    </a-layout>
    <servicePreview-modal ref="servicePreviewDetailForm"></servicePreview-modal>
    <serviceManager-modal ref="serviceManagerForm"  @ok="modalFormOk"></serviceManager-modal>
  </div>
</template>
<script>
  import ATableColumn from "ant-design-vue/es/table/Column";
  import ARow from "ant-design-vue/es/grid/Row";
  import { querySerciceCategery, stopService, startService } from '@/api/api';
  import { deleteAction, postAction, getAction } from '@/api/manage';
  import {JantdListMixin} from '@/mixins/JantdListMixin';
  import ServicePreviewModal from './modules/ServicePreviewModal';
  import ServiceManagerModal from './modules/ServiceManagerModal';

  export default {
    components: {ARow, ATableColumn, ServicePreviewModal,ServiceManagerModal},
    mixins: [JantdListMixin],
    data() {
      return {
        treeData: [],
        dataSource: [],
        columns: [],
        collapse: false,
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
          list: '/serviceInfo/list',
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
      // 启动服务
      startService(serviceId){
        let that = this
        console.log(serviceId)
        if(serviceId == null ){
          that.$message.error("请先注册服务")
          return
        }
        let params = {
          serviceId: serviceId
        }
        startService(params).then((res)=>{
          if(res.success){
            that.$message.success("服务启用成功!");
          }else {
            that.$message.error(res.message);
          }
        })
      },
      // 停止服务
      stopService(serviceId){
        let that = this
        if(serviceId == null ){
          that.$message.error("请先注册服务")
          return
        }
        console.log(serviceId)
        let params = {
          serviceId: serviceId
        }
        stopService(params).then((res)=>{
          if(res.success){
            that.$message.success("服务停用成功!");
          }else {
            that.$message.error(res.message);
          }
        })
      },
      onOpenChange(openKeys) {
        const latestOpenKey = openKeys.find(key => this.openKeys.indexOf(key) === -1)
        if (this.rootSubmenuKeys.indexOf(latestOpenKey) === -1) {
          this.openKeys = openKeys
        } else {
          this.openKeys = latestOpenKey ? [latestOpenKey] : []
        }
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
        var param = Object.assign({}, this.queryParam)
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
      // 获取服务分类
      listServiceCategory() {
        querySerciceCategery().then((res) => {
          if (res.success) {
            console.log(res.result)
            res.result.forEach(data => {
              this.treeData.push({"title":data.name,"key":data.id})
            })
          }
        })
      },
      deploy(record){
        // this.$router.push({ path:'/iservice/modules/ServiceManagerModal'})
        if(record.serviceId == null ){
          this.$message.warning("请先进行服务开发相关操作后重试!")
          return
        }
        this.$refs.serviceManagerForm.show(record);
        this.$refs.serviceManagerForm.title = "部署详情";
      }

    },
    mounted() {
      this.listServiceCategory();
    },
    watch: {
    },
    created: function () {
    }

  }

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