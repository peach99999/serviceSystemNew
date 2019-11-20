package cn.jantd.Controller;

import cn.jantd.Result;
import cn.jantd.communication.*;
import cn.jantd.param.*;
import cn.jantd.util.ServiceMockDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 服务化前端通信接口
 * @Author xiagf
 * @date 2019-09-11
 */
@RestController
@Api(tags = "服务化前端通信接口")
@Slf4j
public class ServiceController {


    @GetMapping(value = "/v1/services")
    @ApiOperation("查询所有服务/查询某个节点上的服务")
    public Object queryAllServices(@RequestParam(value = "nodeid", required = false) String nodeid) {
        if (nodeid == null) {
            return ServiceMockDataUtil.queryAllServices();
        } else {
            return ServiceMockDataUtil.queryNodeServices();
        }

    }

//    @GetMapping(value = "/v1/services/{nodeId}")
//    @ApiOperation("查询某个节点上的服务")
//    public Result<QueryServicesDTO> queryNodeServices(@PathVariable(value = "nodeId", required = true) String nodeId) {
//        return ServiceMockDataUtil.queryNodeServices();
//    }

    @PostMapping(value = "/v1/services/register")
    @ApiOperation("注册一个服务")
    public Object registerService(@RequestBody @Validated RegisterParam registerParam) {
        return ServiceMockDataUtil.registerService();
    }

    @GetMapping(value = "/v1/service/{serviceId}")
    @ApiOperation("查询单个服务")
    public Object getServiceDetail(@PathVariable(value = "serviceId", required = true) String serviceId) {
        return ServiceMockDataUtil.getServiceDetail();
    }

    @DeleteMapping(value = "/v1/service/{serviceId}")
    @ApiOperation("删除一个服务")
    public Object deleteService(@PathVariable(value = "serviceId", required = true) String serviceId) {
        return ServiceMockDataUtil.deleteService();
    }

    @PatchMapping(value = "/v1/service/{serviceId}")
    @ApiOperation("更新服务信息")
    public Object updateService(@RequestBody @Validated UpdateServiceParam updateServiceParam, @PathVariable(value = "serviceId", required = true) String serviceId) {
        return ServiceMockDataUtil.updateService();
    }

    @PostMapping(value = "/v1/service/{serviceId}/start")
    @ApiOperation("启动一个服务")
    public Object startService(@PathVariable(value = "serviceId", required = true) String serviceId) {
        return ServiceMockDataUtil.startService();
    }

    @PostMapping(value = "/v1/service/{serviceId}/stop")
    @ApiOperation("停止一个服务")
    public Object sstopService(@PathVariable(value = "serviceId", required = true) String serviceId) {
        return ServiceMockDataUtil.sstopService();
    }

    @PostMapping(value = "/v1/service/{serviceId}/deploy")
    @ApiOperation("部署一个服务")
    public Object deployService(@RequestBody @Validated DeployServiceParam deployServiceParam, @PathVariable(value = "serviceId", required = true) String serviceId) {
        return ServiceMockDataUtil.deployService();
    }

    @PostMapping(value = "/v1/service/{serviceId}/removedeploy")
    @ApiOperation("移除一个服务的部署")
    public Object removeDeployService(@RequestBody @Validated RemoveDeployServiceParam removeDeployServiceParam, @PathVariable(value = "serviceId", required = true) String serviceId) {
        return ServiceMockDataUtil.removeDeployService();
    }

    @PostMapping(value = "/v1/upload")
    @ApiOperation("服务文件上传")
    public Object uploadServiceFile(@RequestBody @Validated UploadServiceFileParam uploadServiceFileParam) {
        return ServiceMockDataUtil.uploadServiceFile();
    }

    @GetMapping(value = "/v1/nodes")
    @ApiOperation("查询所有节点")
    public Object queryAllNodes() {
        return ServiceMockDataUtil.queryAllNodes();
    }


    @GetMapping(value = "/v1/node/{nodeId}")
    @ApiOperation("查询节点信息【缓存信息】")
    public Object queryNodeDetail(@PathVariable(value = "nodeId", required = true) String nodeId) {
        return ServiceMockDataUtil.queryNodeDetail(nodeId);
    }

    @GetMapping(value = "/v1/stats")
    @ApiOperation("总体统计信息")
    public Object aggregateStatistics() {
        return ServiceMockDataUtil.aggregateStatistics();
    }

//    @GetMapping(value = "/v1/stats/service/{serviceId}")
//    @ApiOperation("单个服务统计")
//    public Result<IndividualServiceStatisticsDTO> individualServiceStatistics(@PathVariable(value = "serviceId", required = true) String serviceId) {
//        return ServiceMockDataUtil.individualServiceStatistics();
//    }

    @GetMapping(value = "/v1/stats/node/{nodeId}")
    @ApiOperation("单个节点统计信息")
    public Object individualNodeStatistics(@PathVariable(value = "nodeId", required = true) String nodeId) {
        return ServiceMockDataUtil.individualNodeStatistics();
    }

    @GetMapping(value = "/v1/stats/service/{serviceId}")
    @ApiOperation("单个节点的单个服务的统计信息/单个服务统计")
    public Object individualNodeServiceStatistics(@PathVariable(value = "serviceId", required = true) String serviceId,
                                                  @RequestParam(value = "nodeId", required = false) String nodeId) {

        if (nodeId == null) {
            return ServiceMockDataUtil.individualServiceStatistics();
        } else {
            return ServiceMockDataUtil.individualNodeServiceStatistics();
        }
    }

    @GetMapping(value = "/v1/log/{count}")
    @ApiOperation("获取最近的日志")
    public Object getLastLogs(@PathVariable(value = "count", required = true) String count) {
        return ServiceMockDataUtil.getLastLogs(count);
    }

    @GetMapping(value = "/v1/logfile")
    @ApiOperation("下载日志文件")
    public Object downloadLogFile() {
        return ServiceMockDataUtil.downloadLogFile();
    }

}
