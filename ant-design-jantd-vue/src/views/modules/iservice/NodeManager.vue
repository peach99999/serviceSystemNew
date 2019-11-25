<template>
  <div id="components-layout-demo-basic">
    <a-row type="flex" justify="center">
      <a-col :span="10">
        <a-input-search placeholder="搜索你想要的节点主机名" v-model="queryParam.name" @search="onSearch" enterButton="搜索"/>
      </a-col>
      <a-col :md="1" :sm="1">
          <a-button type="primary"  @click="searchReset" icon="reload" style="margin-left: 8px;left: 10px">重置</a-button>
      </a-col>
    </a-row>
      <a-table 
        bordered
        :fit="true"
        :dataSource="dataSource"
        :loading="loading" style="margin-top: 10px">
        <a-table-column min-width="140px" align = "center" title="节点编号">
          <template slot-scope="scope">
            <a @click="showNodeDetail(scope)">{{scope.nodeId}}</a>
          </template>
        </a-table-column>
        <a-table-column min-width="140px" align = "center" title="主机名">
          <template slot-scope="scope">
            {{scope.hostName}}
          </template>
        </a-table-column>
        <a-table-column min-width="140px" align = "center" title="IP">
          <template slot-scope="scope">
            {{scope.ip}}
          </template>
        </a-table-column>
        <a-table-column min-width="140px" align = "center" title="部署服务数">
          <template slot-scope="scope">
            {{scope.deployedServiceCount}}
          </template>
        </a-table-column>
        <a-table-column min-width="140px" align = "center" title="运行服务数">
          <template slot-scope="scope">
            {{scope.runningServices}}
          </template>
        </a-table-column>
        <a-table-column min-width="140px" align = "center" title="内存占用率">
          <template slot-scope="scope">
            <a-progress status="active" :percent=scope.cpuCount :showInfo="false" />
          </template>
        </a-table-column>
        <a-table-column min-width="140px" align = "center" title="CPU占用率">
          <template slot-scope="scope">
            <a-progress status="active" :percent=scope.memorySize :showInfo="false" />
          </template>
        </a-table-column>
      </a-table>
      <nodeManager-modal ref="modalForm"></nodeManager-modal>
  </div>
</template>
<script>
  import ATableColumn from "ant-design-vue/es/table/Column";
  import ARow from "ant-design-vue/es/grid/Row";
  import {queryAllNodes,queryNodeDetail} from '@/api/api';
  import NodeManagerModal from './modules/NodeManagerModal'

  export default {
    components: {ARow, ATableColumn,NodeManagerModal},
    data() {
      return {
        dataSource:[],
        loading: false,
        // ipagination: {
        //   current: 1,
        //   pageSize: 1,
        //   pageSizeOptions: ['10', '20', '30'],
        //   showTotal: (total, range) => {
        //     return range[0] + '-' + range[1] + ' 共' + total + '条'
        //   },
        //   showQuickJumper: true,
        //   showSizeChanger: true,
        //   total: 0
        // },
        queryParam: {},
        currentPageData: []
      
      }
    },
 
    created() {
      
    },
    mounted(){
      this.getAllNodes();
    },
    computed:{
    },
    methods: {
      handleTableChange(pagination){
        console.log(pagination)
        this.pagination = pagination
        this.getCurrentPageData(this.pagination)
      },
      getCurrentPageData(pagination) {
        let begin = (pagination.current - 1) * pagination.pageSize;
        let end = pagination.current * pagination.pageSize;
        this.currentPageData = this.dataSource.slice(
            begin,
            end
        );
      },
      showNodeDetail(record){
        console.log(record)
        this.$refs.modalForm.detail(record);
        this.$refs.modalForm.title = "节点详情";
      },
      searchReset(){
        this.refresh()
        this.getAllNodes()
      },
      onSearch(){
        let name = this.queryParam.name;
        let temp = [];
        this.dataSource.forEach(item=>{
          if(item.hostName.indexOf(name) >= 0){
            temp.push(item);
          }
        })
        this.dataSource = temp
      },
      refresh () {
        this.queryParam = {}
        this.dataSource = []
      },
      getAllNodes(){
        this.dataSource = []
        this.loading = true;

        queryAllNodes().then((res)=>{
          if(res.success){
            this.serviceDetatl = res.result;
            // 查询节点明细信息
            if(res.result.length > 0){
              for (let index = 0; index < res.result.length; index++) {
                const element = res.result[index];
                this.fetchNodeDetail(element);
              }
              this.loading = false
            }else{
              this.loading = false
            }
          }else {
          this.$message.error(res.message);
          this.loading = false
          }
        })
      },

      // 查询节点明细信息
      fetchNodeDetail(nodeId){
        queryNodeDetail({nodeId:nodeId}).then((res)=>{
          if(res.success){
            let temp = {}
            let runserviceNumber = 0
            let diskSize = 0
            temp.nodeId = res.result.node_id
            temp.hostName = res.result.host_name
            temp.ip = res.result.ip_address
            temp.deployedServiceCount = res.result.deployed_services.length
            temp.deployedServices = res.result.deployed_services
            for (var val in res.result.running_services) {
              runserviceNumber++
            }
            temp.runningServices = runserviceNumber
            temp.cpuCount = parseInt(res.result.cpu_count)
            temp.memorySize = parseInt(res.result.memory_size)
            // 线程数
            temp.threadCount = parseInt(res.result.thread_count)
            // cpu主频
            temp.cpuFrequency = res.result.cpu_frequency
            for (var val in res.result.disk_size) {
              diskSize = diskSize + parseInt(res.result.disk_size[val])
            }
            temp.diskSize = diskSize
            this.dataSource.push(temp)
          }else {
            this.$message.error(res.message);
          }
        })
      },
    },
      
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
</style>