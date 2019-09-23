<template>
  <div id="components-layout-demo-basic">
    <a-row type="flex" justify="start">
      <a-col :span="2">
        <span class="point">
        </span>
        <span style="font-weight: bold">
          {{"静态信息"}}
        </span>
      </a-col>
      <a-col :span="2" :offset="6">
        <span class="point">
        </span>
        <span style="font-weight: bold">
          {{"动态信息"}}
        </span>
      </a-col>
    </a-row>
    <a-row type="flex" justify="start" :gutter="30" style="margin-left: 1px">
      <a-col :span="8">
        <template>
          <a-card style="height: 280px">
            <a-row type="flex" justify="start" style="margin-top: 10px">
              <a-col :span="6">
                <span class="span-left">{{"主机名:"}}</span>
              </a-col>
              <a-col :span="11">
                <span class="span-margin">{{"名称"}}</span>
              </a-col>
            </a-row>
            <a-row type="flex" justify="start" style="margin-top: 10px">
              <a-col :span="6">
                <span class="span-left">{{"IP:"}}</span>
              </a-col>
              <a-col :span="11">
                <span class="span-margin">{{"127.0.0.1"}}</span>
              </a-col>
            </a-row>
            <a-row type="flex" justify="start" style="margin-top: 10px">
              <a-col :span="6">
                <span class="span-left">{{"CPU数:"}}</span>
              </a-col>
              <a-col :span="11">
                <span class="span-margin">{{"12"}}</span>
              </a-col>
            </a-row>
            <a-row type="flex" justify="start" style="margin-top: 10px">
              <a-col :span="6">
                <span class="span-left">{{"线程数:"}}</span>
              </a-col>
              <a-col :span="11">
                <span class="span-margin">{{"12"}}</span>
              </a-col>
            </a-row>
            <a-row type="flex" justify="start" style="margin-top: 10px">
              <a-col :span="6">
                <span class="span-left">{{"CPU主频:"}}</span>
              </a-col>
              <a-col :span="11">
                <span class="span-margin">{{"12"}}</span>
              </a-col>
            </a-row>
            <a-row type="flex" justify="start" style="margin-top: 10px">
              <a-col :span="6">
                <span class="span-left">{{"内存:"}}</span>
              </a-col>
              <a-col :span="11">
                <span class="span-margin">{{"200G"}}</span>
              </a-col>
            </a-row>
            <a-row type="flex" justify="start" style="margin-top: 10px">
              <a-col :span="6">
                <span class="span-left">{{"磁盘:"}}</span>
              </a-col>
              <a-col :span="11">
                <span class="span-margin">{{"4MB"}}</span>
              </a-col>
            </a-row>
          </a-card>
        </template>
      </a-col>

      <a-col :span="8">
        <template>
          <a-card style="height: 280px">
            <a-row type="flex" justify="start" style="margin-top: 10px">
              <a-col :span="6">
                <span class="span-left">{{"CPU占用率:"}}</span>
              </a-col>
              <a-col :span="11" :offset="2">
                <span>
                   <a-progress :percent="50"/>
                </span>
              </a-col>
            </a-row>
            <a-row type="flex" justify="start" style="margin-top: 10px">
              <a-col :span="6">
                <span class="span-left">{{"已用内存"}}</span>
              </a-col>
              <a-col :span="11" :offset="2">
                <span>
                   <a-progress :percent="50"/>
                </span>
              </a-col>
            </a-row>
            <a-row type="flex" justify="start" style="margin-top: 10px">
              <a-col :span="6">
                <span class="span-left">{{"磁盘使用"}}</span>
              </a-col>
              <a-col :span="11" :offset="2">
                <span>
                   <a-progress :percent="50"/>
                </span>
              </a-col>
            </a-row>
            <a-row type="flex" justify="start" style="margin-top: 10px">
              <a-col :span="6">
                <span class="span-left">{{"调用次数:"}}</span>
              </a-col>
              <a-col :span="11">
                <span class="span-margin">{{"12次"}}</span>
              </a-col>
            </a-row>
            <a-row type="flex" justify="start" style="margin-top: 10px">
              <a-col :span="6">
                <span class="span-left">{{"平均耗时:"}}</span>
              </a-col>
              <a-col :span="11">
                <span class="span-margin">{{"12ms"}}</span>
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
      <a-col :span="20">
        <a-table :dataSource="data" :fit="true">
          <a-table-column min-width="140px" title="服务IP">
            <template slot-scope="scope">
              {{scope.name}}
            </template>
          </a-table-column>
          <a-table-column min-width="140px" title="上线时间">
            <template slot-scope="scope">
              {{scope.name}}
            </template>
          </a-table-column>
          <a-table-column min-width="140px" title="CPU占用率">
            <template slot-scope="scope">
              <a-progress :percent="50" :showInfo="false" />
            </template>
          </a-table-column>
          <a-table-column min-width="140px" title="内存使用率">
            <template slot-scope="scope">
              <a-progress :percent="50" :showInfo="false" />
            </template>
          </a-table-column>
          <a-table-column min-width="140px" title="磁盘使用率">
            <template slot-scope="scope">
              <a-progress :percent="50" :showInfo="false" />
            </template>
          </a-table-column>
          <a-table-column min-width="140px" title="调用次数">
            <template slot-scope="scope">
              {{scope.name}}
            </template>
          </a-table-column>
          <a-table-column min-width="140px" title="平均耗时(ms)">
            <template slot-scope="scope">
              {{scope.name}}
            </template>
          </a-table-column>
        </a-table>
      </a-col>
    </a-row>

  </div>
</template>
<script>
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
