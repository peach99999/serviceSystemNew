<template>
  <div id="components-layout-demo-basic">
    <a-row type="flex" justify="center">
      <a-col :span="8">
        <a-input-search placeholder="搜索你想要的服务" @search="onSearch" enterButton="搜索" size="smill"/>
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
          <a-table :dataSource="models" :show-header="false" :fit="true">
            <a-table-column min-width="140px">
              <template slot-scope="scope">
                <a href="#">
                  <img src="../../../../src/assets/img/img.jpg" width="80px" height="80px">
                </a>
              </template>
            </a-table-column>
            <a-table-column min-width="140px">
              <template slot-scope="scope">
                <a-row type="flex" justify="space-around" align="middle">
                  <a-col :span="4">
                    <span style="font-weight: bold;font-size: 16px" >
                       {{scope.row.name}}
                    </span>
                    <span style="font-size: 10px;color: #cf1322">
                      {{scope.row.designerStatus}}
                    </span>
                  </a-col>
                  <a-col :span="2" :offset="6">
                    <a-icon type="folder"/>
                    <span>文件</span>
                  </a-col>
                  <a-col :span="2">
                    <a-icon type="copy"/>
                    <span>文档</span>
                  </a-col>
                  <a-col :span="2">
                    <a-icon type="form"/>
                    <span>案例</span>
                  </a-col>
                  <a-col :span="2">
                    <a-icon type="laptop"/>
                    <span>代码</span>
                  </a-col>


                  <a-col :span="2" >
                    <a-button  >开发</a-button>
                  </a-col>
                  <a-col :span="2">
                    <a-button  >提交</a-button>
                  </a-col>
                  <a-col :span="2">
                    <a-button  >删除</a-button>
                  </a-col>

                </a-row>
                <p class="text">
                  <a href="#">{{scope.row.description}}</a>
                </p>
                <div class="can_div">
                  <div class="can_left">标签：
                    <span v-if="scope.row.serviceLabel">
                       <h4 v-for="tag in scope.row.serviceLabel.split(',')" class="biaoshi" :key="tag">
                          {{tag}}
                       </h4>
                     </span>
                  </div>
                </div>
                <div class="subit">
                  上传者：{{scope.row.designer}} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  发布时间： {{scope.row.createTime.substring(0,10)}}
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
  import {querySerciceCategery} from '@/api/api'
  export default {
    components: {ARow, ATableColumn},
    data() {
      return {
        treeData:[],
        rootSubmenuKeys: ['sub1', 'sub2', 'sub4'],
        openKeys: ['sub1'],
        labelid: "",
        keys: "",
        pageSize: 10,
        models: [
          {
            row: {
              name: '测试服务',
              designerStatus: '描述状态',
              description: '服务说明',
              serviceLabel: '标签',
              designer: '上传者',
              createTime: '发布时间'

            }
          }
        ],
        collapse: false,
        categoryId: "",
        labels: "",
        size: "",
        searchKeys: "",
        searchKey: "",
        searchCategory: "0",
        total: 0,
        currentPage: 1
      }
    },
    methods: {
      onOpenChange(openKeys) {
        const latestOpenKey = openKeys.find(key => this.openKeys.indexOf(key) === -1)
        if (this.rootSubmenuKeys.indexOf(latestOpenKey) === -1) {
          this.openKeys = openKeys
        } else {
          this.openKeys = latestOpenKey ? [latestOpenKey] : []
        }
      },
      getModelList(pageNum) {
        var vm = this;
        var categoryId = vm.categoryId;
        vm.$axios
          .post("/all/getServiceModelListAll?pageNum=" +
            pageNum +
            "&pageSize=" +
            this.pageSize,
            {keys: this.keys}
          )
          .then(data => {

            vm.models = [].concat(data.list);
            vm.total = data.total;

          })
          .catch(e => {
            // vm.$alert("login failure !!!", "tips");
          });
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
          .post("/all/getLabelServiceUser?pageNum=" + pageNum + "&pageSize=" + this.size,
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
</style>