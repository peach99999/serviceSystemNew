<template>
  <div id="components-layout-demo-basic">
    <a-row type="flex" justify="center">
      <a-col :span="8">
        <a-input-search placeholder="搜索你想要的节点" @search="onSearch" enterButton="搜索" size="smill"/>
      </a-col>
    </a-row>
    <a-layout style="margin-top: 10px">
      <a-table :dataSource="data" :fit="true">
        <a-table-column min-width="140px" title="节点编号">
          <template slot-scope="scope">
            {{scope.name}}
          </template>
        </a-table-column>
        <a-table-column min-width="140px" title="主机名">
          <template slot-scope="scope">
            {{scope.name}}
          </template>
        </a-table-column>
        <a-table-column min-width="140px" title="IP">
          <template slot-scope="scope">
            {{scope.name}}
          </template>
        </a-table-column>
        <a-table-column min-width="140px" title="部署服务数">
          <template slot-scope="scope">
            {{scope.name}}
          </template>
        </a-table-column>
        <a-table-column min-width="140px" title="运行服务数">
          <template slot-scope="scope">
            {{scope.name}}
          </template>
        </a-table-column>
        <a-table-column min-width="140px" title="内存占用率">
          <template slot-scope="scope">
            <a-progress :percent="scope.num" :showInfo="false" />
          </template>
        </a-table-column>
        <a-table-column min-width="140px" title="CPU占用率">
          <template slot-scope="scope">
            <a-progress :percent="scope.num" :showInfo="false" />
          </template>
        </a-table-column>
      </a-table>
    </a-layout>
  </div>
</template>
<script>
  import ATableColumn from "ant-design-vue/es/table/Column";
  import ARow from "ant-design-vue/es/grid/Row";

  const data = [{
    key: '1',
    name: 'John Brown',
    num: '21',
    address: 'New York No. 1 Lake Park',
  }, {
    key: '2',
    name: 'Jim Green',
    num: '44',
    address: 'London No. 1 Lake Park',
  }, {
    key: '3',
    name: 'Joe Black',
    num: '66',
    address: 'Sidney No. 1 Lake Park',
  }];


  export default {
    data() {
      return {
        data
      }
    },
    components: {ARow, ATableColumn}
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