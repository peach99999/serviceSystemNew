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
                  <span style="font-weight: bold; font-size: large">统计时间</span>
                </a-col>
              </a-row>
              <a-row type="flex" justify="center">
                <a-col :span="24">
                  <span>{{statsTime}}</span>
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
                  <span style="font-weight: bold; font-size: large">服务</span>
                </a-col>
              </a-row>
              <a-row type="flex" justify="center">
                <a-col :span="24">
                  <span>{{serverNum}}</span>
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
                  <span style="font-weight: bold;font-size: large">节点</span>
                </a-col>
              </a-row>
              <a-row type="flex" justify="center">
                <a-col :span="24">
                  <span>{{nodeNumber}}</span>
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
                  <span style="font-weight: bold ;font-size: large">上线时间</span>
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
            <span style="font-weight: bold">{{'服务统计'}}</span>
          </template>
          <div id="myChartBar" :style="{width:'80%',height:'300px'}"></div>
        </a-card>
      </a-col>
      <a-col :span="11" :offset="1">
        <a-card class="charts">
          <template slot="title">
            <span style="font-weight: bold">{{'节点统计'}}</span>
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
  import {timeFromNow} from '@/utils/util'
  import {timeToChina} from '@/utils/util'

  export default {
    components: {
      ARow,
      echarts
    },
    data() {
      return {
        serverNum: '',
        nodeNumber:'',
        upTime:'',
        statsTime:'',
        barData:[],
        url: {
          aggregateStatistics: "/communication/aggregate-statistics",
          queryAllServices: "/communication/query-all-services",
          queryAllNodes: "/communication/query-all-node",
        }
      }
    
    },
    created() {
    },
    mounted() {
      this.getServiceNumber()
      this.getNodeNumber()
      this.getAggregateStatistics()
      this.fetchTask()
    },
    methods: {
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
              data: [
                {value: 60, name: '未用节点数'},
                {value: 40, name: '已用节点数'},
              ],
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
          this.upTime = timeFromNow(res.result.up_time)
          this.statsTime = timeToChina(res.result.stats_time)
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
          this.nodeNumber = (res.result.length)
        }
      })
    }
    }
  }
</script>