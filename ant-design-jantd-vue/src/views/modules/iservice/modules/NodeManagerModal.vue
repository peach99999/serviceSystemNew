<template>
  <a-drawer
    :title="title"
    :maskClosable="true"
    :width="900"
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
            {{"静态信息"}}
          </span>
        </a-col>
        <a-col :span="2" :offset="8">
          <span class="point">
          </span>
          <span style="font-weight: bold">
            {{"动态信息"}}
          </span>
        </a-col>
      </a-row>
      <a-row type="flex" justify="start" :gutter="30" style="margin-left: 1px">
        <a-col :span="9">
          <template v-bind="nodeInfo">
            <a-card style="height: 280px">
              <a-row type="flex" justify="start" style="margin-top: 10px">
                <a-col :span="7">
                  <span class="span-left">{{"主机名"}}</span>
                </a-col>
                <a-col :span="11">
                  <span class="span-margin">{{nodeInfo.hostName}}</span>
                </a-col>
              </a-row>
              <a-row type="flex" justify="start" style="margin-top: 10px">
                <a-col :span="7">
                  <span class="span-left">{{"IP:"}}</span>
                </a-col>
                <a-col :span="11">
                  <span class="span-margin">{{nodeInfo.ip}}</span>
                </a-col>
              </a-row>
              <a-row type="flex" justify="start" style="margin-top: 10px">
                <a-col :span="7">
                  <span class="span-left">{{"CPU数:"}}</span>
                </a-col>
                <a-col :span="11">
                  <span class="span-margin">{{nodeInfo.cpuCount}}</span>
                </a-col>
              </a-row>
              <a-row type="flex" justify="start" style="margin-top: 10px">
                <a-col :span="7">
                  <span class="span-left">{{"线程数:"}}</span>
                </a-col>
                <a-col :span="11">
                  <span class="span-margin">{{nodeInfo.threadCount}}</span>
                </a-col>
              </a-row>
              <a-row type="flex" justify="start" style="margin-top: 10px">
                <a-col :span="7">
                  <span class="span-left">{{"CPU主频:"}}</span>
                </a-col>
                <a-col :span="11">
                  <span class="span-margin">{{nodeInfo.cpuFrequency}}MHz</span>
                </a-col>
              </a-row>
              <a-row type="flex" justify="start" style="margin-top: 10px">
                <a-col :span="7">
                  <span class="span-left">{{"内存:"}}</span>
                </a-col>
                <a-col :span="11">
                  <span class="span-margin">{{nodeInfo.memorySize}}</span>
                </a-col>
              </a-row>
              <a-row type="flex" justify="start" style="margin-top: 10px">
                <a-col :span="7">
                  <span class="span-left">{{"磁盘:"}}</span>
                </a-col>
                <a-col :span="11">
                  <span class="span-margin">{{nodeInfo.diskSize}}G</span>
                </a-col>
              </a-row>
            </a-card>
          </template>
        </a-col>

        <a-col :span="10">
          <template v-bind="individualNodeStatisticsInfo">
            <a-card style="height: 280px">
              <a-row type="flex" justify="start" style="margin-top: 10px">
                <a-col :span="7">
                  <span class="span-left">{{"统计时间"}}</span>
                </a-col>
                <a-col :span="14" :offset="2">
                  <span>
                    <span style="float: left;">{{individualNodeStatisticsInfo.statsTime}}</span>
                  </span>
                </a-col>
              </a-row>
              <a-row type="flex" justify="start" style="margin-top: 10px">
                <a-col :span="7">
                  <span class="span-left">{{"CPU占用率:"}}</span>
                </a-col>
                <a-col :span="14" :offset="2">
                  <span>
                    <a-progress :percent= individualNodeStatisticsInfo.cpuAvailable status="active" />
                  </span>
                </a-col>
              </a-row>
              <a-row type="flex" justify="start" style="margin-top: 10px">
                <a-col :span="7">
                  <span class="span-left">{{"已用内存"}}</span>
                </a-col>
                <a-col :span="14" :offset="2">
                  <span>
                    <a-progress :percent=individualNodeStatisticsInfo.memoryAvalable status="active"/>
                  </span>
                </a-col>
              </a-row>
              <a-row type="flex" justify="start" style="margin-top: 10px">
                <a-col :span="7">
                  <span class="span-left">{{"磁盘使用"}}</span>
                </a-col>
                <a-col :span="14" :offset="2">
                  <span>
                    <a-progress :percent=individualNodeStatisticsInfo.diskAvailable status="active" />
                  </span>
                </a-col>
              </a-row>
              <a-row type="flex" justify="start" style="margin-top: 10px">
                <a-col :span="7">
                  <span class="span-left">{{"运行服务数:"}}</span>
                </a-col>
                <a-col :span="14">
                  <span class="span-margin">{{individualNodeStatisticsInfo.runningServicesCount}}</span>
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
            {{"服务列表"}}
          </span>
        </a-col>
      </a-row>
      <a-row type="flex" justify="start" style="margin-top: 5px">
        <a-col :span="30">
          <a-table 
            :dataSource="data" 
            :fit="true"
            bordered
            :loading="loading">
            <a-table-column min-width="140px" align = "center" title="服务编号">
              <template slot-scope="scope">
                {{scope.serviceId}}
              </template>
            </a-table-column>
            <a-table-column min-width="140px" align = "center" title="上线时间">
              <template slot-scope="scope">
                {{scope.deploySubmissionTime}}
              </template>
            </a-table-column>
            <a-table-column min-width="140px" title="CPU占用率" align = "center">
              <template slot-scope="scope">
                <a-progress status="active" :percent=scope.cpuUsed :showInfo="false" />
              </template>
            </a-table-column>
            <a-table-column min-width="140px" title="内存使用率" align = "center">
              <template slot-scope="scope">
                <a-progress status="active" :percent=scope.memoryUsed :showInfo="false" />
              </template>
            </a-table-column>
            <a-table-column min-width="140px" title="磁盘使用率" align = "center">
              <template slot-scope="scope">
                <a-progress status="active" :percent=scope.diskUsed :showInfo="false" />
              </template>
            </a-table-column>
            <a-table-column min-width="140px" title="调用次数" align = "center">
              <template slot-scope="scope">
                {{scope.callCount}}
              </template>
            </a-table-column>
            <a-table-column min-width="140px" title="平均耗时(ms)" align = "center">
              <template slot-scope="scope">
                {{scope.averageTimeCost}}
              </template>
            </a-table-column>
          </a-table>
        </a-col>
      </a-row>
    </div>
  </a-drawer>
</template>
<script>
  
import {individualNodeStatistics,individualNodeServiceStatistics} from '@/api/api'
import { deleteAction, postAction, getAction } from '@/api/manage';
import { formatUtcDate } from '@/utils/util'
  export default {
    name: "NodeManagerModal",
    data() {
      return {
        data:[],
        title:"",
        visible: false,
        loading: false,
        nodeInfo:{},
        individualNodeStatisticsInfo:{},
        ipagination: {
          current: 1,
          pageSize: 1,
          pageSizeOptions: ['10', '20', '30'],
          showTotal: (total, range) => {
            return range[0] + '-' + range[1] + ' 共' + total + '条'
          },
          showQuickJumper: true,
          showSizeChanger: true,
          total: 0
        },
        temp:{},
        url: {
          serviceDetail: '/serviceInfo/query-by-service-id',
        },
      }
      
    },
    created() {
      
    },
    mounted(){
    },
    computed:{
    },
    methods: {
      detail(record){
        let that = this;
        that.refresh();
        that.visible = true;
        that.loading = true;
        that.nodeInfo = record;
        // 获取节点统计信息
        that.queryIndividualNodeStatisticsInfo(record.nodeId);
        // 获取服务列表详情
        that.queryIndividualNodeServiceStatistics(record)
      },
      queryIndividualNodeServiceStatistics(record){
        console.log(record)
        for (let index = 0; index < record.deployedServices.length; index++) {
          const serviceId = record.deployedServices[index];
          individualNodeServiceStatistics({nodeId:record.nodeId,serviceId:serviceId}).then((res)=>{
            if(res.success){
              this.temp = {}
              this.temp.serviceId = res.result.service_id
              this.temp.cpuUsed = parseInt(res.result.cpu_used)
              this.temp.memoryUsed = parseInt(res.result.memory_used)
              this.temp.diskUsed = parseInt(res.result.disk_used)
              this.temp.callCount = parseInt(res.result.call_count)
              this.temp.averageTimeCost =parseInt(res.result.average_time_cost)
              // 查询部署时间
              getAction(this.url.serviceDetail, {serviceId:res.result.service_id}).then((res) => {
                if (res.success) {
                  this.temp.deploySubmissionTime = res.result.deploySubmissionTime
                  this.data.push(this.temp)
                }
              })
              console.log(this.data)
            }else {
              this.$message.error(res.message);
            }
          })
        }
        this.loading = false
      },
      queryIndividualNodeStatisticsInfo(nodeId){
        individualNodeStatistics({nodeId:nodeId}).then((res)=>{
          if(res.success){
            let temp = {}
            let diskSize = 0
            let diskCount = 0
            temp.statsTime = formatUtcDate(res.result.stats_time)
            temp.hostUpTime = parseInt(res.result.host_up_time)
            temp.cpuAvailable = parseInt(res.result.cpu_available)
            temp.memoryAvalable = parseInt(res.result.memory_avalable)
            for (var val in res.result.disk_available) {
              diskCount++
              diskSize = diskSize + parseInt(res.result.disk_available[val])
            }
            temp.diskAvailable = Math.round(diskSize/diskCount)
            temp.runningServicesCount = parseInt(res.result.running_services_count)
            this.individualNodeStatisticsInfo = temp
          }else {
            this.$message.error(res.message);
          }
        })
      },
      refresh () {
        this.data = []
        this.nodeInfo = {}
        this.individualNodeStatisticsInfo = {}
      },
      handleCancel () {
        this.close()
      },
      close () {
        this.$emit('close');
        this.visible = false;
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
