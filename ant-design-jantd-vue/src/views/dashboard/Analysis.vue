<template>
  <a-card>
    <a-row type="flex" align="middle">
      <a-col :span="5">
        <a-card
          class="analysis"
          hoverable
        >
          <a-row type="flex" align="middle">
            <a-col :span="8">
              <a href="#">
                <img src="../../../src/assets/img/statistics.jpg" width="80px" height="80px">
              </a>
            </a-col>
            <a-col :span="10" :offset="2">
              <a-row type="flex">
                <a-col :span="24">
                  <span style="font-weight: bold; font-size: large">本地服务数</span>
                </a-col>
              </a-row>
              <a-row type="flex" justify="center">
                <a-col :span="24">
                  <span>{{localServiceCount}}</span>
                </a-col>
              </a-row>
            </a-col>
          </a-row>
        </a-card>
      </a-col>
      <a-col :span="5" :offset="1">
        <a-card
          class="analysis"
          hoverable
        >
          <a-row type="flex" align="middle">
            <a-col :span="8">
              <a href="#">
                  <img src="../../../src/assets/img/service.jpg" width="80px" height="80px">
                </a>
            </a-col>
            <a-col :span="10" :offset="2">
              <a-row type="flex">
                <a-col :span="24">
                  <span style="font-weight: bold; font-size: large">运行服务数</span>
                </a-col>
              </a-row>
              <a-row type="flex" justify="center">
                <a-col :span="24">
                  <span>{{runningServerNumber}}</span>
                </a-col>
              </a-row>
            </a-col>
          </a-row>
        </a-card>
      </a-col>
      <a-col :span="5" :offset="1">
        <a-card
          class="analysis"
          hoverable
        >
          <a-row type="flex" align="middle">
            <a-col :span="8">
              <a href="#">
                <img src="../../../src/assets/img/node.jpg" width="80px" height="80px">
              </a>
            </a-col>
            <a-col :span="10" :offset="2">
              <a-row type="flex">
                <a-col :span="24">
                  <span style="font-weight: bold;font-size: large">部署服务数</span>
                </a-col>
              </a-row>
              <a-row type="flex" justify="center">
                <a-col :span="24">
                  <span>{{deployedServicNumber}}</span>
                </a-col>
              </a-row>
            </a-col>
          </a-row>
        </a-card>
      </a-col>
      <a-col :span="5" :offset="1">
        <a-card
          class="analysis"
          hoverable
        >
          <a-row type="flex" align="middle">
            <a-col :span="8">
              <a href="#">
                <img src="../../../src/assets/img/uptime.jpg" width="80px" height="80px">
              </a>
            </a-col>
            <a-col :span="10" :offset="2">
              <a-row type="flex">
                <a-col :span="24">
                  <span style="font-weight: bold ;font-size: large">上线时长</span>
                </a-col>
              </a-row>
              <a-row type="flex">
                <a-col :span="24">
                  <span>{{upTime}}</span>
                </a-col>
              </a-row>
            </a-col>
          </a-row>
        </a-card>
      </a-col>
    </a-row>
    <a-row type="flex" align="middle" style="margin-top: 50px">
      <a-col :span="11">
        <a-card class="charts">
          <template slot="title">
            <a href="#" @click="jumpToServicePreview()" style="font-weight: bold;font-size: 16px; color:rgba(0, 0, 0, 0.65)" >
              {{'服务统计'}}
            </a>
          </template>
          <div id="myChartBar" :style="{width:'80%',height:'300px'}"></div>
        </a-card>
      </a-col>
      <a-col :span="11" :offset="1">
        <a-card class="charts">
          <template slot="title">
            <a href="#" @click="jumpToNodeManager()" style="font-weight: bold;font-size: 16px; color:rgba(0, 0, 0, 0.65)" >
              {{'节点统计'}}
            </a>
          </template>
          <div id="myChartRing" :style="{width:'80%',height:'300px'}"></div>
        </a-card>
      </a-col>
    </a-row>
  </a-card>
</template>
<script>
  import echarts from 'echarts'
  import ARow from "ant-design-vue/es/grid/Row"
  import './analysis.less'
  import { deleteAction, postAction, getAction } from '@/api/manage'
  import { timeFromNow, timeToChina,formatUtcDate,millSecondToDate} from '@/utils/util'
  import {queryNodeDetail} from '@/api/api'

  export default {
    components: {
      ARow,
      echarts
    },
    data() {
      return {
        runningServerNumber: '',
        deployedServicNumber:'',
        localServiceCount:'',
        upTime:'',
        statsTime:'',
        barData:[],
        parData:[],
        url: {
          aggregateStatistics: "/communication/aggregate-statistics",
          queryAllServices: "/communication/query-all-services",
          queryAllNodes: "/communication/query-all-node",
          getServiceCount: "serviceInfo/get-service-count"
        },
        allNodeCount:0,
        usedNodeCount:0,
      }
    
    },
    created() {
      
    },
    mounted() {
      // this.getServiceNumber()
      this.getNodeNumber()
      this.getServiceCount();
      this.getAggregateStatistics()
           
    },
    methods: {
      getServiceCount(){
        if (!this.url.getServiceCount) {
          this.$message.error('请设置url.getServiceCount!')
          return
        }
        getAction(this.url.getServiceCount).then((res) => {
          if (res.success) {
            this.localServiceCount = (res.result)
          }
        })
      },
      jumpToServicePreview(){
        this.$router.push({ path:'/iservice/ServicePreview'})
      },
      jumpToNodeManager(){
        this.$router.push({ path:'/iservice/NodeManager'})
      },
      fetchTask() {
        /* ECharts图表 */
        var myChart = echarts.init(document.getElementById('myChartRing'))
        myChart.setOption({
          tooltip: {
            trigger: 'item',
            formatter: '{b} :{d}%'
          },
          legend: {
            type: 'scroll',
            orient: 'horizontal',
            bottom: 10,
            data: ['未用节点数', '已用节点数']
          },
          grid: {
            left: '3%',
            right: '8%',
            width: '90%',
            containLabel: true
          },
          color: ['#3398DB', '#e2e2e2'],
          series: [
            {
              type: 'pie',
              radius: '55%',
              center: ['50%', '50%'],
              data:this.parData,
              itemStyle: {
                emphasis: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
              }
            }
          ]
        })
      },
      fetchTool() {
          /* ECharts图表 */
          var myChartbar = echarts.init(document.getElementById('myChartBar'))
          myChartbar.setOption({
            color: ['#01a5e2'],
            tooltip: {
              trigger: 'item',
              axisPointer: { // 坐标轴指示器，坐标轴触发有效
                type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
              }
            },
            grid: {
              left: '3%',
              right: '3%',
              bottom: '3%',
              containLabel: true
            },
            series : [
              {
                // name:'服务',
                type:'bar',
                barWidth: '60%',
                data:this.barData
              }
          
            ],
            xAxis: [
              {
                type: 'category',
                data : ['总服务数','部署服务数','运行服务数','运行服务实例数','主机数','已用主机数','调用次数'],
                axisTick: {
                  alignWithLabel: true
                }
              }
            ],
            yAxis: [
              {
                type: 'value'
              }
            ]
          })
      },
      // 查询服务总体统计信息
      getAggregateStatistics() {
      if (!this.url.aggregateStatistics) {
        this.$message.error('请设置url.aggregateStatistics属性!')
        return
      }
      getAction(this.url.aggregateStatistics).then((res) => {
        if (res.success) {
          console.log(res.result)
          this.upTime = millSecondToDate(res.result.up_time)
          // this.statsTime = timeToChina(formatUtcDate(res.result.stats_time))
          this.runningServerNumber = res.result.running_service_count
          this.deployedServicNumber = res.result.deployed_service_count
          this.barData.push(res.result.total_service_count)
          this.barData.push(res.result.deployed_service_count)
          this.barData.push(res.result.running_service_count)
          this.barData.push(res.result.running_service_instance_count)
          this.barData.push(res.result.host_count)
          this.barData.push(res.result.used_host_count)
          this.barData.push(res.result.total_call_count)
          console.log(this.barData)
        }
        this.fetchTool()
      })
    },
    // 获取服务数
    getServiceNumber(){
      if (!this.url.queryAllServices) {
        this.$message.error('请设置url.queryAllServices属性!')
        return
      }
      getAction(this.url.queryAllServices).then((res) => {
        if (res.success) {
          this.serverNum = (res.result.services.length)
        }
      })
    },
    // 获取节点数
    getNodeNumber(){
      if (!this.url.queryAllNodes) {
        this.$message.error('请设置url.queryAllServices属性!')
        return
      }
      getAction(this.url.queryAllNodes).then((res) => {
        if (res.success) {
          this.allNodeCount = (res.result.length)
          var count = 0
          console.log(this.allNodeCount)
          if(res.result.length > 0){
              // for (let index = 0; index < res.result.length; index++) {
              //   count ++
              //   const element = res.result[index];
                this.fetchNodeDetail(0,res.result.length,res.result);
              // }
          }
        }
        
      })
      
    },
  
    // 查询节点明细信息统计使用的节点数
      fetchNodeDetail(i,count,data){
        console.log(i,count,data)
        var nodeId = data[i]
        queryNodeDetail({nodeId:nodeId}).then((res)=>{
          if(res.success){
            var arr = Object.keys(res.result.running_services)
            if(arr.length > 0){
              this.usedNodeCount++
            }
            if(i+1<count){
              this.fetchNodeDetail(i+1,count,data);
            }else{
                this.parData.push({value:this.allNodeCount-this.usedNodeCount,name:"未用节点数"},{value:this.usedNodeCount,name:"已用节点数"})
                console.log(this.parData)
                this.fetchTask() 
            }
          }
        })
      },
    }
  }
</script>