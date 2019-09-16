<template>
  <div>
    <a-row :gutter="20">
      <a-col :span="24">
        <a-row :gutter="20" class="mgb20">
          <a-col :span="8">
            <a-card shadow="hover" :body-style="{padding: '0px'}">
              <div class="grid-content grid-con-1">
                <i class="el-icon-lx-people grid-con-icon"></i>
                <!--<a-icon type="heart" />-->
                <div class="grid-cont-right">
                  <div>服务</div>
                  <div class="grid-num">{{servicecount}}</div>
                </div>
              </div>
            </a-card>
          </a-col>
          <a-col :span="8">
            <a-card shadow="hover" :body-style="{padding: '0px'}">
              <div class="grid-content grid-con-2">
                <i class="el-icon-lx-notice grid-con-icon"></i>
                <div class="grid-cont-right">
                  <div>节点</div>
                  <div class="grid-num">{{nodecount}}</div>
                </div>
              </div>
            </a-card>
          </a-col>
          <a-col :span="8">
            <a-card shadow="hover" :body-style="{padding: '0px'}">
              <div class="grid-content grid-con-3">
                <i class="el-icon-lx-goods grid-con-icon"></i>
                <div class="grid-cont-right">
                  <div>上线时间</div>
                  <div class="grid-num">{{uptime}}</div>
                </div>
              </div>
            </a-card>
          </a-col>
        </a-row>
      </a-col>
    </a-row>
    <!--<el-row :gutter="20">-->
      <!--<el-col :span="12">-->
        <!--<el-card shadow="hover">-->
          <!--<schart ref="bar" class="schart" canvasId="bar" :data="bardata" type="bar"-->
                  <!--:options="options"></schart>-->
        <!--</el-card>-->
      <!--</el-col>-->
      <!--<el-col :span="12">-->
        <!--<el-card shadow="hover">-->
          <!--<schart ref="pie" class="schart" canvasId="pie" :data="piedata" type="pie"-->
                  <!--:options="options2"></schart>-->
        <!--</el-card>-->
      <!--</el-col>-->
    <!--</el-row>-->
  </div>
</template>
<style scoped>

  .grid-content {
    display: flex;
    align-items: center;
    height: 100px;
  }

  .grid-cont-right {
    flex: 1;
    text-align: center;
    font-size: 14px;
    color: #999;
  }

  .grid-num {
    font-size: 15px;
    font-weight: bold;
  }

  .grid-con-icon {
    font-size: 50px;
    width: 100px;
    height: 100px;
    text-align: center;
    line-height: 100px;
    color: #fff;
  }

  .grid-con-1 .grid-con-icon {
    background: rgb(45, 140, 240);
  }

  .grid-con-1 .grid-num {
    color: rgb(45, 140, 240);
  }

  .grid-con-2 .grid-con-icon {
    background: rgb(100, 213, 114);
  }

  .grid-con-2 .grid-num {
    color: rgb(45, 140, 240);
  }

  .grid-con-3 .grid-con-icon {
    background: rgb(242, 94, 67);
  }

  .grid-con-3 .grid-num {
    color: rgb(242, 94, 67);
  }

  .user-info {
    display: flex;
    align-items: center;
    padding-bottom: 20px;
    border-bottom: 2px solid #ccc;
    margin-bottom: 20px;
  }

  .user-avator {
    width: 120px;
    height: 120px;
    border-radius: 50%;
  }

  .user-info-cont {
    padding-left: 50px;
    flex: 1;
    font-size: 14px;
    color: #999;
  }

  .user-info-cont div:first-child {
    font-size: 30px;
    color: #222;
  }

  .user-info-list {
    font-size: 14px;
    color: #999;
    line-height: 25px;
  }

  .user-info-list span {
    margin-left: 70px;
  }

  .mgb20 {
    margin-bottom: 20px;
  }

  .todo-item {
    font-size: 14px;
  }

  .todo-item-del {
    text-decoration: line-through;
    color: #999;
  }

  .schart {
    width: 100%;
    height: 300px;
  }
</style>