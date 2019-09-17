<template>
  <a-card>
    <a-row type="flex" align="middle">
      <a-col :span="7">
        <a-card
          class="analysis"
          hoverable
        >
          <a-row type="flex" align="middle">
            <a-col :span="12">
              <img
                style="height: 80px;width: 80px"
                src="https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png"
              />
            </a-col>
            <a-col :span="10" :offset="2">
              <a-row type="flex">
                <a-col :span="24">
                  <span style="font-weight: bold">服务</span>
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
      <a-col :span="7" :offset="1">
        <a-card
          class="analysis"
          hoverable
        >
          <a-row type="flex" align="middle">
            <a-col :span="12">
              <img
                style="height: 80px;width: 80px"
                src="https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png"
              />
            </a-col>
            <a-col :span="10" :offset="2">
              <a-row type="flex">
                <a-col :span="24">
                  <span style="font-weight: bold">节点</span>
                </a-col>
              </a-row>
              <a-row type="flex" justify="center">
                <a-col :span="24">
                  <span>{{"60"}}</span>
                </a-col>
              </a-row>
            </a-col>
          </a-row>
        </a-card>
      </a-col>
      <a-col :span="7" :offset="1">
        <a-card
          class="analysis"
          hoverable
        >
          <a-row type="flex" align="middle">
            <a-col :span="6">
              <img
                style="height: 80px;width: 80px"
                src="https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png"
              />
            </a-col>
            <a-col :span="10" :offset="2">
              <a-row type="flex">
                <a-col :span="24">
                  <span style="font-weight: bold">上线时间</span>
                </a-col>
              </a-row>
              <a-row type="flex">
                <a-col :span="24">
                  <span>{{"1月20号10小时25分18秒"}}</span>
                </a-col>
              </a-row>
            </a-col>
          </a-row>
        </a-card>
      </a-col>
    </a-row>
    <a-row type="flex" align="middle" style="margin-top: 50px">
      <a-col :span="10">
        <a-card class="charts">
          <template slot="title">
            <span style="font-weight: bold">{{'服务统计'}}</span>
          </template>
          <div id="myChartBar" :style="{width:'80%',height:'220px'}"></div>
        </a-card>
      </a-col>
      <a-col :span="10" :offset="2">
        <a-card class="charts">
          <template slot="title">
            <span style="font-weight: bold">{{'节点统计'}}</span>
          </template>
          <div id="myChartRing" :style="{width:'80%',height:'220px'}"></div>
        </a-card>
      </a-col>
    </a-row>
  </a-card>
</template>
<script>
  import echarts from 'echarts'
  import ARow from "ant-design-vue/es/grid/Row";
  import './analysis.less'

  export default {
    components: {
      ARow,
      echarts
    },
    data() {
      return {
        serverNum: '10',
        list: [],
        task: {
          data: [],
          options: {}
        },
        tool: {
          data: [],
          options: {}
        },
        barName: [],
        barData: []
      }
    },
    created() {
    },
    mounted() {
      this.fetchTool()
      this.fetchTask()
    },
    methods: {
      fetchTask() {
        /* ECharts图表 */
        var myChart = echarts.init(document.getElementById('myChartRing'))
        myChart.setOption({
          title: {
            text: '任务完成量',
            x: 'center',
            textStyle: {
              color: '#333333',
              fontSize: '13',
              fontWeight: '540'
            }
          },
          tooltip: {
            trigger: 'item',
            formatter: '{b} :{d}%'
          },
          legend: {
            type: 'scroll',
            orient: 'horizontal',
            bottom: 10,
            data: ['西凉', '益州', '兖州', '荆州', '幽州']
          },
          grid: {
            left: '3%',
            right: '8%',
            width: '90%',
            containLabel: true
          },
          color: ['#01a5e2', '#e2e2e2', '#fe9846', '#d48265', '#91c7ae', '#749f83', '#ca8622', '#bda29a', '#6e7074', '#546570', '#c4ccd3'],
          series: [
            {
              type: 'pie',
              radius: '55%',
              center: ['50%', '50%'],
              data: [
                {value: 1548, name: '幽州'},
                {value: 535, name: '荆州'},
                {value: 510, name: '兖州'},
                {value: 634, name: '益州'},
                {value: 735, name: '西凉'}
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
            title: {
              text: '工具统计',
              x: 'center',
              textStyle: {
                color: '#333333',
                fontSize: '13',
                fontWeight: '540'
              }
            },
            tooltip: {
              trigger: 'item',
              axisPointer: { // 坐标轴指示器，坐标轴触发有效
                type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
              }
            },
            legend: {
              type: 'scroll',
              orient: 'horizontal',
              bottom: 10,
              data:['直接访问','邮件营销','联盟广告','视频广告','搜索引擎','百度','谷歌','必应','其他']
            },
            grid: {
              left: '3%',
              right: '2%',
              width: '100%',
              containLabel: true
            },
            series : [
              {
                name:'直接访问',
                type:'bar',
                data:[320, 332, 301, 334, 390, 330, 320]
              },
              {
                name:'邮件营销',
                type:'bar',
                stack: '广告',
                data:[120, 132, 101, 134, 90, 230, 210]
              },
              {
                name:'联盟广告',
                type:'bar',
                stack: '广告',
                data:[220, 182, 191, 234, 290, 330, 310]
              },
              {
                name:'视频广告',
                type:'bar',
                stack: '广告',
                data:[150, 232, 201, 154, 190, 330, 410]
              },
              {
                name:'搜索引擎',
                type:'bar',
                data:[862, 1018, 964, 1026, 1679, 1600, 1570],
                markLine : {
                  lineStyle: {
                    normal: {
                      type: 'dashed'
                    }
                  },
                  data : [
                    [{type : 'min'}, {type : 'max'}]
                  ]
                }
              },
              {
                name:'百度',
                type:'bar',
                barWidth : 5,
                stack: '搜索引擎',
                data:[620, 732, 701, 734, 1090, 1130, 1120]
              },
              {
                name:'谷歌',
                type:'bar',
                stack: '搜索引擎',
                data:[120, 132, 101, 134, 290, 230, 220]
              },
              {
                name:'必应',
                type:'bar',
                stack: '搜索引擎',
                data:[60, 72, 71, 74, 190, 130, 110]
              },
              {
                name:'其他',
                type:'bar',
                stack: '搜索引擎',
                data:[62, 82, 91, 84, 109, 110, 120]
              }
            ],
            xAxis: [
              {
                type: 'category',
                data : ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
                axisTick: {
                  alignWithLabel: true
                }
              }
            ],
            color: ['#01a5e2', '#e2e2e2', '#fe9846', '#d48265', '#91c7ae', '#749f83', '#ca8622', '#bda29a', '#6e7074', '#546570', '#c4ccd3'],
            yAxis: [
              {
                type: 'value'
              }
            ]
          })
      }
    }
  }
</script>