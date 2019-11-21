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
      <a-col :span="12">
        部署状态:
        <span v-if="notDeployed" style="color: #1890ff">
          {{"未部署"}}
        </span>
        <span v-if="deploying" style="color: #1890ff">
          {{"部署中"}}
        </span>
        <span v-if="deployed" style="color: #1890ff">
          {{"已部署"}}
        </span>
      </a-col>
      <a-col :span="3">
        <a-button type="primary" @click="deployedService" :loading="deployLoading" style="margin-left:200px">部署</a-button>
      </a-col>
      <a-col :span="4">
        <a-button type="primary" @click="removeDeployedService" :loading="removeDeployLoading" style="margin-left:200px">移除部署</a-button>
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
  import {queryAllNodes,queryNodeDetail,deployService,getServiceDetail,removeDeployService} from '@/api/api';
  import ARow from "ant-design-vue/es/grid/Row";
  import './ServiceManagerModal.less';
  import { httpAction } from '@/api/manage'

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
            title: '主机名称',
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
            align:"center",
            scopedSlots: { customRender: 'operation' },
          }
        ],
        dataSource:[],
        notDeployed:false,
        deploying:false,
        deployed:false,
        title:"",
        visible: false,
        serviceInfo:{},
        loading: false,
        queryParam: {},
        /* table选中keys*/
        selectedRowKeys: [],
        /* table选中records*/
        selectionRows: [],
        url: {
          edit: "/serviceInfo/edit-deploy",
        },
        deployedOnNodes:[],
        deployLoading:false,
        removeDeployLoading:false,
      }
    },
    created() {
      
    },
    mounted(){
    
    },
    computed:{
    },
    methods: {
      // 查询单个单个服务信息
      queryServiceDetail(serviceId){
        getServiceDetail({serviceId:serviceId}).then((res)=>{
          if(res.success){
            console.log(res.result)
            let status = res.result.status
            this.deployedOnNodes = res.result.deployed_on_nodes
            if(status == 'running'){
              this.deployed = true
            }else if(status == 'not_deployed'){
              this.deploying = true
            }else if(status == 'deploying'){
              this.deploying = true
            }else if(status == 'not_running'){
              this.deployed = true
            }else if(status == 'starting'){
              this.deployed = true
            }else {
              this.deploying = true
            }
          }else {
            this.$message.error(res.message);
          }
        })
      },
      searchReset(){
        this.deployLoading=false
        this.removeDeployLoading=false
        this.dataSource = []
        this.queryParam = {}
        this.getAllNodes()
      },
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
      // 移除服务部署
      removeDeployedService(){
        this.removeDeployLoading = true
        console.log(this.deployedOnNodes)
        if(this.deployedOnNodes.length > 0){
          console.log(this.deployedOnNodes)
          var param = {
            serviceId:this.serviceInfo.serviceId,
            nodes:this.deployedOnNodes
          }
          removeDeployService(param).then((res)=>{
            if(res.success){
              this.$message.success("服务移除成功!");
              this.removeDeployLoading = false
              this.$emit('ok');
              this.visible = false;
            }else {
              this.removeDeployLoading = false
              this.$message.error(res.message);
            }
          })
        }else{
          this.removeDeployLoading = false
          this.$message.warning("部署后方可移除!")
        }
      },
      // 部署服务
      deployedService(){
        var param = {}
        var nodes = {}
        param.serviceId = this.serviceInfo.serviceId
        if(this.selectionRows.length == 0){
          this.$message.warning("请选择节点!")
          return
        }
        try{
          this.selectionRows.forEach((item,index)=>{
            if(item.instanceCount == "" || item.instanceCount == null || item.instanceCount == undefined){
              console.log(item)
                this.$message.warning("请编辑" + item.hostName + "的实例数!")
                throw 'Jump out now!'
            }else{
              let nodeId = item.nodeId
              
              nodes[nodeId] = item.instanceCount
            }
          })
        }catch (e) {
          console.log(e)
          return
        }
        param.nodes = nodes
        console.log(param)
        deployService(param).then((res)=>{
          if(res.success){
            this.$message.success("部署成功!")
            // 更新部署人和部署时间
            
            let httpurl=this.url.edit;
            let method = 'put';
            let formData = {};
            formData.id = this.serviceInfo.id;
            
            console.log(formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                console.log(res.message);
              }else{
                console.log(res.message);
              }
            }).finally(() => {
              this.$emit('ok');
              this.visible = false;
            })
            
          }else {
            this.$message.error(res.message);
          }
        })
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
      show(record){
        let that = this;
        that.refresh();
        that.visible = true;
        that.serviceInfo = record;
        // 获取所有节点
        this.getAllNodes();
        this.queryServiceDetail(record.serviceId);
      },
      refresh () {
        this.serviceInfo = {}
        this.queryParam = {}
        this.dataSource = []
         /* table选中keys*/
        this.selectedRowKeys = []
        /* table选中records*/
        this.selectionRows = []
      },
      handleCancel () {
        this.close()
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleChange (value, key, column) {
        const newData = [...this.dataSource]
        const target = newData.filter(item => key === item.nodeId)[0]
        if (target) {
          target[column] = value
          this.dataSource = newData
        }
        setTimeout(()=>{
          this.save(key)
        },1500)
      },
      edit (key) {
        const newData = [...this.dataSource]
        const target = newData.filter(item => key === item.nodeId)[0]
        if (target) {
          target.editable = true
          this.dataSource = newData
        }
      },
      save (key) {
        const newData = [...this.dataSource]
        const target = newData.filter(item => key === item.nodeId)[0]
        if (target) {
          delete target.editable
          this.dataSource = newData
        }
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