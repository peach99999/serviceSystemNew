<template>
  <a-drawer
    :title="title"
    :maskClosable="true"
    :width="800"
    placement="right"
    :closable="true"
    @close="handleCancel"
    :visible="visible">
  <div id="components-layout-demo-basic">
    <a-row type="flex" justify="center">
      <a-col :span="15">
        <a-input-search placeholder="搜索你想要的节点" v-model="queryParam.name" @search="onSearch" enterButton="搜索"/>
      </a-col>
      <a-col :md="1" :sm="1">
          <a-button type="primary"  @click="searchReset" icon="reload" style="margin-left: 8px;left: 10px">重置</a-button>
      </a-col>
    </a-row>
    <a-row type="flex" style="margin-top: 20px">
      <a-col :span="16">
        部署状态:
        <span v-if="one" style="color: #1890ff">
          {{"未部署"}}
        </span>
        <span v-if="two" style="color: #1890ff">
          >{{"部署中"}}
        </span>
        <span v-if="three" style="color: #1890ff">
          >{{"已部署"}}
        </span>
      </a-col>
      <a-col :span="8">
        <a-button type="primary">
          部署
        </a-button>
      </a-col>
    </a-row>
    <a-layout style="margin-top: 5px" id="layoutTable">
      <a-row type="flex">
        <a-col :span="21" :offset="4">
          <a-table 
            ref="table"
            size="middle"
            bordered
            rowKey="id"
            :columns="columns"
            :dataSource="dataSource"
            :pagination="false"
            :loading="loading"
            :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}">
            <template v-for="col in ['hostName','ip','instanceCount']" :slot="col" slot-scope="text, record">
              <div :key="col">
                <a-input
                  v-if="record.editable"
                  style="margin: -5px 0"
                  :value="text"
                  @change="e => handleChange(e.target.value, record.nodeId, col)"
                  type="number" min='0' oninput="javascript:this.value=this.value.replace(/[^\d]/g,'')" placeholder="请输入实例数"
                />
                <template v-else>{{text}}</template>
              </div>
            </template>
            <template slot="operation" slot-scope="text, record">
            <div class='editable-row-operations'>
              <span v-if="record.editable">
                <a @click="() => save(record.nodeId)">保存</a>
                <!-- <a-popconfirm title='确定取消?' @confirm="() => cancel(record.nodeId)">
                  <a>取消</a>
                </a-popconfirm> -->
              </span>
              <span v-else>
                <a @click="() => edit(record.nodeId)">编辑</a>
              </span>
            </div>
          </template>
          </a-table>
        </a-col>
      </a-row>
    </a-layout>
  </div>
  </a-drawer>
</template>
<script>
  import ATableColumn from "ant-design-vue/es/table/Column";
  import {queryAllNodes,queryNodeDetail} from '@/api/api';
  import ARow from "ant-design-vue/es/grid/Row";
  import './ServiceManagerModal.less'

  export default {
    components: {ARow, ATableColumn},
    data() {
      return {
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title: '节点名称',
            align:"center",
            dataIndex: 'hostName'
          },
          {
            title: 'IP',
            align:"center",
            dataIndex: 'ip'
          },
          {
            title: '实例数',
            align:"center",
            dataIndex: 'instanceCount',
            scopedSlots: { customRender: 'instanceCount' },
          },
          {
            title: '操作',
            dataIndex: 'operation',
            scopedSlots: { customRender: 'operation' },
          }
        ],
        dataSource:[],
        one:true,
        two:true,
        three:true,
        title:"",
        visible: false,
        serviceInfo:{},
        loading: false,
        queryParam: {},
        /* table选中keys*/
        selectedRowKeys: [],
        /* table选中records*/
        selectionRows: [],
      }
    },
    created () {
    },
    mounted(){
    
    },
    computed:{
    },
    methods: {
      getAllNodes(){
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
            temp.nodeId = res.result.node_id
            temp.hostName = res.result.host_name
            temp.ip = res.result.ip
            temp.instanceCount = ''
            this.dataSource.push(temp)
          }else {
            this.$message.error(res.message);
          }
        })
      },
      onSelectChange(selectedRowKeys, selectionRows) {
        this.selectedRowKeys = selectedRowKeys;
        this.selectionRows = selectionRows;
        console.log(this.selectedRowKeys,this.selectionRows)
      },
      onSearch(){
        let name = this.queryParam.name;
        let temp = [];
        this.dataSource.forEach(item=>{
          if(item.name.indexOf(name) >= 0){
            temp.push(item);
          }
        })
        this.dataSource = temp

      },
      searchReset(){

      },
      show(record){
        let that = this;
        that.refresh();
        that.visible = true;
        that.serviceInfo = record;
        // 获取所有节点
        this.getAllNodes();
      },
      refresh () {
        this.serviceInfo = {}
        this.queryParam = {}
        this.dataSource = []
      },
      handleCancel () {
        this.close()
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleChange (value, key, column) {
        console.log(value, key, column)
        const newData = [...this.dataSource]
        const target = newData.filter(item => key === item.nodeId)[0]
        console.log(target)
        if (target) {
          target[column] = value
          this.dataSource = newData
        }
      },
      edit (key) {
        console.log(key)
        const newData = [...this.dataSource]
        const target = newData.filter(item => key === item.nodeId)[0]
        if (target) {
          target.editable = true
          this.dataSource = newData
        }
        console.log(this.dataSource)
      },
      save (key) {
        console.log(key)
        const newData = [...this.dataSource]
        const target = newData.filter(item => key === item.nodeId)[0]
        if (target) {
          delete target.editable
          this.dataSource = newData
        }
        console.log(this.dataSource)
      },
      
    }
  }
</script>
<style>
  .editable-row-operations a {
    margin-right: 8px;
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
</style>