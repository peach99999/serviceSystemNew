<template>
  <a-drawer
    :title="title"
    :maskClosable="true"
    :width="1050"
    placement="right"
    :closable="true"
    @close="handleCancel"
    :visible="visible">
    <div id="components-layout-demo-basic">
      <a-row type="flex" justify="start">
        <a-col :span="2">
          <span class="point">
          </span>
          <span style="font-weight: bold">
            {{"基本信息"}}
          </span>
        </a-col>
      </a-row>
      <a-row type="flex" justify="start" :gutter="30" style="margin-left: 1px">
        <a-col :span="15">
          <template v-bind="serviceInfo">
            <a-card style="height: 320px">
              <a-row type="flex" justify="start" style="margin-top: 15px">
                <a-col :span="4">
                  <span class="span-left">{{"名称:"}}</span>
                </a-col>
                <a-col :span="8">
                  <span class="span-margin">{{serviceInfo.name}}</span>
                </a-col>
                <a-col :span="4">
                  <span class="span-left">{{"标签:"}}</span>
                </a-col>
                <a-col :span="8">
                  <span class="span-margin">{{serviceInfo.serviceLabel}}</span>
                </a-col>
              </a-row>
              <a-row type="flex" justify="start" style="margin-top: 15px">
                <a-col :span="4">
                  <span class="span-left">{{"设计人员:"}}</span>
                </a-col>
                <a-col :span="8">
                  <span class="span-margin">{{serviceInfo.designer}}</span>
                </a-col>
                <a-col :span="4">
                  <span class="span-left">{{"单位:"}}</span>
                </a-col>
                <a-col :span="8">
                  <span class="span-margin">{{serviceInfo.designerDepartment}}</span>
                </a-col>
              </a-row>
              <a-row type="flex" justify="start" style="margin-top: 15px">
                <a-col :span="4">
                  <span class="span-left">{{"联系方式:"}}</span>
                </a-col>
                <a-col :span="8">
                  <span class="span-margin">{{serviceInfo.contactInformation}}</span>
                </a-col>
                <a-col :span="4">
                  <span class="span-left">{{"描述:"}}</span>
                </a-col>
                <a-col :span="8">
                  <a-tooltip v-if="serviceInfo.description !== undefined && serviceInfo.description.length > 20">
                    <span class="span-margin">{{serviceInfo.description.substring(0,20) + '....'}}</span>
                  </a-tooltip>
                  <span v-else class="span-margin">{{serviceInfo.description}}</span>
                </a-col>
              </a-row>
              <a-row type="flex" justify="start" style="margin-top: 15px">
                <a-col :span="4">
                  <span class="span-left">{{"接口描述文件:"}}</span>
                </a-col>
                <a-col :span="8">
                  <a @click="handleDownload(serviceInfo.interfaceDescriptionFilePath)" class="span-margin" style="color: #2f54eb">{{"下载文件"}}</a>
                </a-col>
                <a-col :span="4">
                  <span class="span-left">{{"开发人员:"}}</span>
                </a-col>
                <a-col :span="8">
                  <span class="span-margin">{{serviceInfo.developer}}</span>
                </a-col>
              </a-row>
              <a-row type="flex" justify="start" style="margin-top: 15px">
                <a-col :span="4">
                  <span class="span-left">{{"服务实现:"}}</span>
                </a-col>
                <a-col :span="8">
                  <a  @click="handleDownload(serviceInfo.serviceImplementationFilePath)" class="span-margin" style="color: #2f54eb">{{"下载文件"}}</a>
                </a-col>
                <a-col :span="4">
                  <span class="span-left">{{"使用案例:"}}</span>
                </a-col>
                <a-col :span="8">
                  <a @click="handleDownload(serviceInfo.demoFilePath)" class="span-margin" style="color: #2f54eb">{{"下载文件"}}</a>
                </a-col>
              </a-row>

              <a-row type="flex" justify="start" style="margin-top: 15px">
                <a-col :span="4">
                  <span class="span-left">{{"用户文档:"}}</span>
                </a-col>
                <a-col :span="8">
                  <a  @click="handleDownload(serviceInfo.userManualFilePath)" class="span-margin" style="color: #2f54eb">{{"下载文件"}}</a>
                </a-col>
                <a-col :span="4">
                  <span class="span-left">{{"服务调用框架:"}}</span>
                </a-col>
                <a-col :span="8">
                  <a @click="handleDownload(serviceInfo.serviceCallFramePath)" class="span-margin" style="color: #2f54eb">{{"下载文件"}}</a>
                </a-col>
              </a-row>
              <a-row type="flex" justify="start" style="margin-top: 15px">
                <a-col :span="4">
                  <span class="span-left">{{"服务实现框架:"}}</span>
                </a-col>
                <a-col :span="8">
                  <a  @click="handleDownload(serviceInfo.serviceImplementFramePath)" class="span-margin" style="color: #2f54eb">{{"下载文件"}}</a>
                </a-col>
              </a-row>
            </a-card>
          </template>
        </a-col>
      </a-row>

      <a-row type="flex" justify="start" style="margin-top: 20px">
        <a-col :span="2">
          <span class="point">
          </span>
          <span style="font-weight: bold">
            {{"节点列表"}}
          </span>
        </a-col>
      </a-row>
      <a-row type="flex" justify="start" style="margin-top: 5px">
        <a-col :span="28">
          <a-table 
            :dataSource="nodeDataSource" 
            bordered
            :fit="true" 
            :pagination="false"
            :loading="loading">
            <a-table-column min-width="140px" title="节点编号">
              <template slot-scope="scope">
                {{scope.nodeId}}
              </template>
            </a-table-column>
            <a-table-column min-width="140px" title="主机名">
              <template slot-scope="scope">
                {{scope.hostName}}
              </template>
            </a-table-column>
            <!-- <a-table-column min-width="140px" title="IP">
              <template slot-scope="scope">
                {{scope.ip}}
              </template>
            </a-table-column> -->
            <a-table-column min-width="140px" align = "center" title="本服务部署数">
              <template slot-scope="scope">
                {{scope.deployedServices}}
              </template>
            </a-table-column>
            <a-table-column min-width="140px" align = "center" title="本服务运行数">
              <template slot-scope="scope">
                {{scope.runningServices}}
              </template>
            </a-table-column>
            <a-table-column min-width="140px" title="CPU占用率">
              <template slot-scope="scope">
                <a-progress status="active" :percent=100-scope.cpuAvailable :showInfo="true" />
              </template>
            </a-table-column>
            <!-- <a-table-column min-width="140px" title="内存使用率">
              <template slot-scope="scope">
                <a-progress status="active" :percent=50 :showInfo="true" />
              </template>
            </a-table-column> -->
            <!-- <a-table-column min-width="140px" title="磁盘使用率">
              <template slot-scope="scope">
                <a-progress status="active" :percent=50 :showInfo="true" />
              </template>
            </a-table-column> -->
          </a-table>
        </a-col>
      </a-row>
      <a-row type="flex" justify="start" style="margin-top: 20px">
        <a-col :span="2">
          <span class="point">
          </span>
          <span style="font-weight: bold">
            {{"服务概括"}}
          </span>
        </a-col>
      </a-row>
      <a-row type="flex" justify="start" :gutter="30" style="margin-left: 1px">
        <a-col :span="15">
          <template v-bind="serviceStatistics">
            <a-card style="height: 240px">
              <a-row  type="flex" justify="start" style="margin-top: 15px">
                <a-col :span="4">
                  <span class="span-left">{{"服务ID:"}}</span>
                </a-col>
                <a-col :span="8">
                  <span class="span-margin">{{serviceInfo.serviceId}}</span>
                </a-col>
              </a-row>
              <a-row type="flex" justify="start" style="margin-top: 15px">
                <a-col :span="4">
                  <span class="span-left">{{"统计时间:"}}</span>
                </a-col>
                <a-col :span="8">
                  <span class="span-margin">{{statsTime}}</span>
                </a-col>
                <a-col :span="4">
                  <span class="span-left">{{"状态:"}}</span>
                </a-col>
                <a-col :span="8">
                  <span class="span-margin">{{transferStatus(serviceStatistics.status)}}</span>
                </a-col>
              </a-row>
              <a-row type="flex" justify="start" style="margin-top: 15px">
                <a-col :span="4">
                  <span class="span-left">{{"部署实例数:"}}</span>
                </a-col>
                <a-col :span="8">
                  <span class="span-margin">{{serviceStatistics.instance_count}}</span>
                </a-col>
                <a-col :span="4">
                  <span class="span-left">{{"运行节点数:"}}</span>
                </a-col>
                <a-col :span="8">
                  <span class="span-margin">{{serviceStatistics.running_nodes_count}}</span>
                </a-col>
              </a-row>
              <a-row type="flex" justify="start" style="margin-top: 15px">
                <a-col :span="4">
                  <span class="span-left">{{"CPU占用率:"}}</span>
                </a-col>
                <a-col :span="4">
                  <span >
                    <a-progress :percent=parseInt(serviceStatistics.total_cpu_used*100) status="active"/>
                  </span>
                </a-col>
                <a-col :span="4" :offset="4">
                  <span class="span-left">{{"调用次数:"}}</span>
                </a-col>
                <a-col :span="8">
                  <span class="span-margin">{{serviceStatistics.total_call_count}}</span>
                </a-col>
              </a-row>
              <a-row type="flex" justify="start" style="margin-top: 15px">
                <!-- <a-col :span="4">
                  <span class="span-left">{{"内存占用率:"}}</span>
                </a-col>
                <a-col :span="4">
                  <span>
                    <a-progress :percent=parseInt(serviceStatistics.total_memory_used*100) status="active"/>
                  </span>
                </a-col> -->
                <!-- <a-col :span="4" :offset="4">
                  <span class="span-left">{{"平均耗时:"}}</span>
                </a-col>
                <a-col :span="8">
                  <span class="span-margin">{{serviceStatistics.average_time_cost}}ms</span>
                </a-col> -->
              </a-row>
            </a-card>
          </template>
        </a-col>
      </a-row>
    </div>
  </a-drawer>
</template>
<script>
  
  import {getIndividualServiceStatistics,getServiceDetail,queryNodeDetail,individualNodeStatistics} from '@/api/api'
  import { formatUtcDate } from '@/utils/util'
  export default {
    name: "ServicePreviewModal",
    data() {
      return {
        nodeDataSource:[],
        title:"",
        visible: false,
        loading: false,
        serviceInfo:'',
        url: {
          downloadUrl: window._CONFIG['domianURL']+"/sys/common/download",
        },
        serviceStatistics:{},
        serviceDetatl:{},
        temp:{},
        statsTime:'',
      }
    },
       
    created () {
    },
    computed:{
    },
    methods: {
      // 状态转换
      transferStatus(status){
        if(status == 'running'){
          return '正在运行'
        }else if(status == 'not_deployed'){
          return '未部署'
        }else if(status == 'deploying'){
          return '部署中'
        }else if(status == 'not_running'){
          return '未运行'
        }else if(status == 'starting'){
          return '启动中'
        }else {
          return ''
        }
      },
      // 下載
      handleDownload(path){
        console.log(path)
        if(!path){
        this.$message.warning("无文件!")
          return;
        }
        if(path.indexOf(",")>0){
          path = path.substring(0,path.indexOf(","))
        }
        window.open(window._CONFIG['domianURL'] + "/sys/common/download/"+path);
      },
      detail(record){
        let that = this;
        that.refresh();
        that.visible = true;
        that.loading = true;
        that.serviceInfo = record;
        // 获取服务概览
        that.getServiceStatistics(that.serviceInfo.serviceId);
        // 查询单个单个服务信息获取部署节点获取节点列表
        that.queryServiceDetail(record.serviceId);
      },
      // 查询单个单个服务信息获取部署节点获取节点列表
      queryServiceDetail(serviceId){
        getServiceDetail({serviceId:serviceId}).then((res)=>{
          if(res.success){
            this.serviceDetatl = res.result;
            // 查询节点明细信息
            this.getNodeDetail(this.serviceDetatl);
          }else {
            this.$message.error(res.message);
            this.loading = false
          }
        })
      },
      // 查询节点明细信息
      getNodeDetail(serviceDetatl){
        if(serviceDetatl.deployed_on_nodes.length > 0){
          // for (let index = 0; index < serviceDetatl.deployed_on_nodes.length; index++) {
          //   const element = serviceDetatl.deployed_on_nodes[index];
          //   this.fetchNodeDetail(element);
          // }
          this.fetchNodeDetail(0,serviceDetatl.deployed_on_nodes.length,serviceDetatl.deployed_on_nodes)
          
        }else{
          this.loading = false
        }
        
      },
      fetchNodeDetail(i,count,deployedNodes){
        var nodeId = deployedNodes[i]
        queryNodeDetail({nodeId:nodeId}).then((res)=>{
          if(res.success){
            var that = this
            that.temp = {}
            let runserviceNumber = 0
            let diskSize = 0
            let diskCount = 0
            that.temp.nodeId = res.result.node_id
            that.temp.hostName = res.result.host_name
            that.temp.ip = res.result.ip
            that.temp.deployedServices = res.result.deployed_services.length
            
            for (var val in res.result.running_services) {
              runserviceNumber++
            }
            that.temp.runningServices = runserviceNumber
            // this.temp.cpuCount = parseInt(res.result.cpu_count)
            // this.temp.memorySize = parseInt(res.result.memory_size)
         
            // for (var val in res.result.disk_size) {
            //   diskCount++
            //   diskSize = diskSize + parseInt(res.result.disk_size[val])
            // }
            // this.temp.diskSize = Math.round(diskSize/diskCount)
            // 查询节点统计信息
            individualNodeStatistics({nodeId:nodeId}).then((res)=>{
              if(res.success){
                let diskSize = 0
                let diskCount = 0
                that.temp.cpuAvailable = parseInt(parseFloat(res.result.cpu_available)*100)
                that.temp.memoryAvalable = parseInt(res.result.memory_avalable)
                for (var val in res.result.disk_available) {
                  diskCount++
                  diskSize = diskSize + parseInt(res.result.disk_available[val])
                }
                that.temp.diskAvailable = Math.round(diskSize/diskCount)
                that.nodeDataSource.push(that.temp)
                if(i+1<count){
                   this.fetchNodeDetail(i+1,count,deployedNodes)
                }else{
                  this.loading = false
                }
              }else {
                this.$message.error(res.message);
              }
              console.log(that.temp)
            })
          }else {
            this.$message.error(res.message);
          }
        })
      },
      refresh () {
        this.serviceInfo = {}
        this.serviceStatistics = {}
        this.serviceDetatl = {}
        this.nodeDataSource = []
        this.temp = {}
        this.statsTime = ''
      },
      // 获取服务概览
      getServiceStatistics(serviceId){
        getIndividualServiceStatistics({serviceId:serviceId}).then((res)=>{
          if(res.success){
            console.log(res.result)
            this.serviceStatistics = res.result
            this.statsTime = formatUtcDate(res.result.stats_time)
          }else {
            this.$message.error(res.message);
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

  .point {
    display: inline-block;
    width: 6px;
    height: 10px;
    border-radius: 50%;
    background: #1e9eff;
    top: 1px;
  }

  .span-left {
    float: left;
  }

  .span-margin {
    float: left;
    margin-left: 20px;
  }
</style>
