<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">

          <a-col :md="6" :sm="8">
            <a-form-item label="搜索日志">
              <a-input placeholder="请输入搜索关键词" v-model="queryParam.keyWord"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="10" >
            <span style="float: right;" class="table-page-search-submitButtons">
              <a-button type="primary" style="left: 10px" @click="search" icon="search">查询</a-button>
              <a-button type="primary"  @click="searchReset" icon="reload" style="margin-left: 8px;left: 10px">重置</a-button>
              <a-button type="primary"  @click="downloadFile" icon="download" style="margin-left: 8px;left: 10px">下载日志文件</a-button>
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>

    <!-- table区域-begin -->
      <a-table
        ref="table"
        size="middle"
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        @change="handleTableChange">

      </a-table>
    <!-- table区域-end -->
  </a-card>
</template>

<script>
  import { filterObj } from '@/utils/util';
  import { JantdListMixin } from '@/mixins/JantdListMixin'
  import { deleteAction, postAction, getAction } from '@/api/manage'

  export default {
    name: "LogList",
    mixins:[JantdListMixin],
    data () {
      return {
        description: '这是日志管理页面',
        // 查询条件
        queryParam: {
          keyWord:'',
        },
        lastQueryData:[],
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            align:"left",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title: '日志内容',
            align:"center",
            dataIndex: 'logContent',
            sorter: false
          }
        ],
        labelCol: {
          xs: { span: 1 },
          sm: { span: 2 },
        },
        wrapperCol: {
          xs: { span: 10 },
          sm: { span: 16 },
        },
        url: {
          list: "/communication/get-last-log",
          download: "/communication/download-Log-File"
        },
        downloadUrl:'',
      }
    },
    created() {
      // this.loadData(1);
    },
    
    methods: {
      getQueryParams(){
        var param = Object.assign({}, this.queryParam,this.isorter);
        param.pageNo = this.ipagination.current;
        param.pageSize = this.ipagination.pageSize;
        param.couuumt = '100';
        return filterObj(param);
      },
      loadData(arg) {
        if (!this.url.list) {
          this.$message.error('请设置url.list属性!')
          return
        }
        getAction(this.url.list, {count:"100"}).then((res) => {
          if (res.success) {
            this.lastQueryData = []
            console.log(res.result.logs)
            this.downloadUrl = res.result.downloadUrl
            for (let i = 0; i < res.result.logs.length; i++) {
              this.lastQueryData.push({"logContent":res.result.logs[i]})
            }
            // this.lastQueryData = this.dataSource
            this.search()
            // this.ipagination.total = parseInt(res.result.log_record_count)

          }
          this.loading = false
        })

      },
      // 搜索
      search(){
        var searchData = this.queryParam.keyWord;
        var tempData = [];
        console.log(searchData,this.lastQueryData.length)
        if(searchData != '' && searchData != undefined && searchData != null){
            for (let i = 0; i < this.lastQueryData.length; i++) {
              console.log(this.lastQueryData[i].logContent)
              if(this.lastQueryData[i].logContent.indexOf(searchData) != -1){
                tempData.push(this.lastQueryData[i])
              }
            }
        }
        console.log(tempData)
        if(tempData.length){
          this.dataSource = tempData
          this.ipagination.total = tempData.length
        }else{
          this.dataSource = this.lastQueryData
          this.ipagination.total = this.lastQueryData.length
        }
      },
      // 下载
      downloadFile(){
        let param = {}
        // getAction(this.url.download, param).then((res) => {
        //   if (res.success) {
        //     console.log(res.result)
        //     window.open(res.result)
        //   }else{
        //     this.$message.warning(res.message);
        //   }
        // })
        console.log(this.downloadUrl)
        window.open(this.downloadUrl)
        
      },
      // 重置
      searchReset(){
        var that = this;
        that.queryParam = {}; //清空查询区域参数
        that.loadData(this.ipagination.current);
      },
      onDateOk(value) {
        console.log(value);
      },
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>