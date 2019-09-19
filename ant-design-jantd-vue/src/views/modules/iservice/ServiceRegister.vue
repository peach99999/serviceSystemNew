<template>
  <div id="components-layout-demo-basic">
    <a-row type="flex">
      <a-col :md="10" :sm="12" :offset="5">
          <a-input-search placeholder="搜索你想要的服务" v-model="queryParam.name" @search="onSearch" enterButton="搜索"/>
      </a-col>
      <a-col :md="1" :sm="1">
          <a-button type="primary"  @click="searchReset" icon="reload" style="margin-left: 8px;left: 10px">重置</a-button>
      </a-col>
      <a-col :md="8" :sm="6">
        <a-button type="primary"><a-icon type="plus" />新增</a-button>
      </a-col>
    </a-row>

    <a-layout style="margin-top: 10px">
      <a-layout-sider>
        <a-tree
          showLine
          :treeData="treeData"
          @select="this.onSelect"
          @check="this.onCheck"
        >
        </a-tree>
      </a-layout-sider>
      <a-layout>
        <a-layout-content>
          <a-table 
            :dataSource="models" 
            :show-header="false" 
            :fit="true" 
            :pagination="ipagination" 
            :loading="loading"
            @change="handleTableChange">
            <a-table-column min-width="140px">
              <template slot-scope="scope">
                <a href="#">
                  <img src="../../../../src/assets/img/img.jpg" width="80px" height="80px">
                </a>
              </template>
            </a-table-column>
            <a-table-column min-width="140px">
              <template v-for="(service,index) in models" slot-scope="service">
                <a-row type="flex" :key="index" justify="space-around" align="middle">
                  <a-col :span="6">
                    <span style="font-weight: bold;font-size: 16px" >
                       {{service.name}}
                    </span>
                    <span style="font-size: 10px;color: #cf1322">
                      {{service.designerStatus_dictText}}
                    </span>
                  </a-col>
                  <a-col :span="2" :offset="12">
                    <a-icon type="copy"/>
                    <span>文件</span>
                  </a-col>
                  <a-col :span="2">
                    <a-icon type="form"/>
                    <span>修改</span>
                  </a-col>
                  <a-col :span="2">
                    <a-icon type="laptop"/>
                    <span>删除</span>
                  </a-col>
                </a-row>
                <div class="divLine" :key="index"/>
                <p class="text" :key="index" >
                  <a href="#">{{service.description}}</a>
                </p>
                <div class="can_div" :key="index" >
                  <div class="can_left">标签：
                    <span v-if="service.serviceLabel">
                       <h4 v-for="tag in service.serviceLabel.split(',')" class="biaoshi" :key="tag">
                          {{tag}}
                       </h4>
                     </span>
                  </div>
                </div>
                <div class="subit" :key="index" >
                  上传者：{{service.designer}} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  发布时间： {{service.createTime.substring(0,10)}}
                </div>
              </template>
            </a-table-column>
          </a-table>
        </a-layout-content>
        <a-layout-footer>{{'12'}}</a-layout-footer>
      </a-layout>
    </a-layout>
  </div>
</template>
<script>
  import ATableColumn from "ant-design-vue/es/table/Column";
  import ARow from "ant-design-vue/es/grid/Row";
  import { querySerciceCategery } from '@/api/api';
  import { deleteAction, postAction, getAction } from '@/api/manage';
  export default {
    components: {ARow, ATableColumn},
    data() {
      return {
        treeData: [],
        rootSubmenuKeys: ['sub1', 'sub2', 'sub4'],
        openKeys: ['sub1'],
        labelid: "",
        keys: "",
        pageSize: 10,
        models: [],
        collapse: false,
        categoryId: "",
        labels: "",
        size: "",
        searchKeys: "",
        searchKey: "",
        searchCategory: "0",
        total: 0,
        currentPage: 1,
        queryParam: {
          name:'',
          categoryId:this.categoryId
        },
        loading: false,
        ipagination: {
          current: 1,
          pageSize: 10,
          pageSizeOptions: ['10', '20', '30'],
          showTotal: (total, range) => {
            return range[0] + '-' + range[1] + ' 共' + total + '条'
          },
          showQuickJumper: true,
          showSizeChanger: true,
          total: 0
        },
        url: {
          queryServiceInfo: '/serviceInfo/list',
        }
      }
     
    },
    methods: {
      // 搜索
      onSearch() {
        this.ipagination.current = 1
        this.getModelList();
      },
      handleTableChange(pagination){
        this.ipagination = pagination;
        this.getModelList();
      },
      // 重置
      searchReset(){
        var that = this;
        that.queryParam = {} //清空查询区域参数
        this.categoryId = ''
        this.ipagination.current = 1
        that.getModelList();
      },
      // 获取服务分类
      listServiceCategory() {
        querySerciceCategery().then((res) => {
          if (res.success) {
            console.log(res.results)
            res.result.forEach(data => {
              this.treeData.push({"title":data.name,"key":data.id})
            })
          }
        })
      },
      onSelect (selectedKeys, info) {
        console.log(selectedKeys[0])
        this.categoryId = selectedKeys[0];
        this.ipagination.current = 1
        this.getModelList();
        console.log(this.categoryId);
      },
      onCheck (checkedKeys, info) {
        console.log('onCheck', checkedKeys)
      },
      onOpenChange(openKeys) {
        const latestOpenKey = openKeys.find(key => this.openKeys.indexOf(key) === -1)
        if (this.rootSubmenuKeys.indexOf(latestOpenKey) === -1) {
          this.openKeys = openKeys
        } else {
          this.openKeys = latestOpenKey ? [latestOpenKey] : []
        }
      },
      getModelList(pageNum) {
        //加载数据 若传入参数1则加载第一页的内容
        var param = Object.assign({}, this.queryParam)
        param.pageNo = this.ipagination.current
        param.pageSize = this.ipagination.pageSize
        param.categoryId = this.categoryId
        this.loading = true
        console.log(param)
        getAction(this.url.queryServiceInfo, param).then((res) => {
          if (res.success) {
            console.log(res)
            this.models = res.result.records
            this.ipagination.total = res.result.total
          }
          this.loading = false
        })
        
      },
      createLabel() {
        var vm = this;
        this.$prompt("添加").then(({value}) => {
          var label = {name: value};
          vm.$axios.post("/all/addServiceLabel", label).then(data => {
            vm.labels.push(data);
          });
        });

      },
      fetchLabels() {
        var vm = this;
        this.$axios.get("/all/getServiceLabels").then(data => {
          vm.labels = data;
        });
      },
      selectLabel(nodeobj, node, nodecomp) {
        this.labelid = nodeobj.id;
        this.fetchData(1, nodeobj, node, nodecomp);
      },
      fetchData(pageNum, nodeobj, node, nodecomp) {
        var vm = this;
        this.$axios
          .post("/all/getLabelServiceUser?pageNum=" + pageNum + "&pageSize=" + this.pageSize,
            {labelid: nodeobj.id, keys: this.searchKey})
          .then(data => {
            vm.models = [].concat(data.list);
          });
      },
      addService() {
        if (this.labelid != "") {
          this.$router.push({path: '/detail', query: {labelid: this.labelid, method: "add"}});
        } else {
          this.$message('请先选择分类');
        }
      },
      search() {
        var vm = this;
        var searchKeys = vm.searchKeys;
        this.$axios.post("all/searchServicekeys", {searchKeys: searchKeys})
          .then(data => {
            vm.models = [].concat(data.list);
          })
      },
      update(rowService) {
        var vm = this;
        this.$router.push({path: '/detail', query: {rowService: rowService, method: "update"}});
      },
      deleteService(rowService) {
        var vm = this;
        vm.$axios
          .post("/all/deleteService", rowService
          )
          .then(data => {
            vm.models = [].concat(data.list);
          })
          .catch(e => {
            // vm.$alert("login failure !!!", "tips");
          });
      },
      getParams() {
        //获取明细传过来数据
        var vm = this;
        if (this.$route.query.method == "onsubmit") {

          vm.onSubmit(vm.$route.query.allServiceModel);
        }
      },
      onSubmit(data) {
        this.models = [].concat(data.list);
      },
      getPage() {

      },

      //树的添加方法
      append(node, data) {
        console.log(node, data)
        console.log(node, data)
      },
      //删除树
      remove(node, data) {
        var vm = this;
        this.$confirm('此操作将永久删除该标签, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          var label = {id: data.id};
          vm.$axios.post("/all/removeTreeLabel", label).then(data => {
            vm.fetchLabels();
          });
        }).catch(() => {
          // this.$message({
          //     type: 'info',
          //     message: '已取消删除'
          // });
        });
      },
      //每页显示数量
      handleSizeChange(pageSize) {
        var vm = this;
        vm.pageSize = pageSize;
        vm.$axios
          .post("/serve/changePage?start=" + vm.start + "&pageSize=" + vm.pageSize + "&keys=" + vm.keyword)
          .then(data => {
              vm.nodes = [].concat(data);
            }
          )
          .catch(e => {

          })
      },
      //页面改变是触发
      currentChange(page) {
        var vm = this;
        vm.$axios
          .post("/serve/changePage?start=" + start + "&pageSize=" + vm.pageSize + "&keys=" + vm.keyword)
          .then(data => {
              vm.nodes = [].concat(data);
            }
          )
          .catch(e => {

          })
      }

    },
    mounted() {
      this.listServiceCategory();
      this.getModelList(1);
      this.fetchLabels();
      this.getPage();
    },
    watch: {
      '$route': 'getParams'
    },
    created: function () {
      this.getParams();
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

  .m_left {
    width: 140px;
    height: 140px;
    float: left;
    margin-right: 15px;
  }

  .m_right {
    float: left;
    width: 520px;
  }

  .Biaoti {
    float: left;
    font-size: 18px;
    font-weight: bold;
  }

  .Status {
    float: left;
    font-size: 10px;
    color: #fe8a2d;
    margin-left: 5px;
  }

  .canshu {
    float: right;
    color: #999;
    font-size: 12px;
  }

  .canshu a:hover {
    color: #fe8a2d;
  }

  .canshu a {
    color: #999;
    text-decoration: none;
  }

  .text {
    width: 100%;
    display: block;
    float: left;
    height: 38px;
    line-height: 38px;
  }

  .text a {
    text-decoration: none;
  }

  .text a:hover {
    text-decoration: underline;
  }

  .can_div {
    width: 100%;
    display: block;
    float: left;
    height: 28px;
    line-height: 28px;
  }

  .can_left {
    float: left;
    color: #999999;
    font-size: 14px;
  }

  .can_right {
    float: right;
    color: #999999;
    font-size: 14px;
  }

  .biaoshi {
    background: #f0f0f0;
    display: inline;
    padding: 3px 10px;
    font-size: 12px;
    font-weight: normal;
    color: #999999;
  }

  .subit {
    width: 100%;
    color: #999;
    display: block;
    font-size: 14px;
    height: 28px;
    line-height: 28px;
    float: left;
  }
  .divLine {
    margin-bottom: 10px;
		border-bottom: 1px solid #f1f1f1;
  }  
</style>