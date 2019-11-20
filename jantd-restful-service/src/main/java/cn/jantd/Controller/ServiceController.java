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
    @ApiOperation("查询所有服务")
    public Result<QueryServicesDTO> queryAllServices() {
        return ServiceMockDataUtil.queryAllServices();
    }

    @GetMapping(value = "/v1/services/{nodeId}")
    @ApiOperation("查询某个节点上的服务")
    public Result<QueryServicesDTO> queryNodeServices(@PathVariable(value = "nodeId", required = true) String nodeId) {
        return ServiceMockDataUtil.queryNodeServices();
    }

    @PostMapping(value = "/v1/services/register")
    @ApiOperation("注册一个服务")
    public Result<String> registerService(@RequestBody @Validated RegisterParam registerParam) {
        return ServiceMockDataUtil.registerService();
    }

    @GetMapping(value = "/v1/service/{serviceId}")
    @ApiOperation("查询单个服务")
    public Result<ServiceDetailDTO> getServiceDetail(@PathVariable(value = "serviceId", required = true) String serviceId) {
        return ServiceMockDataUtil.getServiceDetail();
    }

    @DeleteMapping(value = "/v1/service/{serviceId}")
    @ApiOperation("删除一个服务")
    public Result<Object> deleteService(@PathVariable(value = "serviceId", required = true) String serviceId) {
        return ServiceMockDataUtil.deleteService();
    }

    @PatchMapping(value = "/v1/service/{serviceId}")
    @ApiOperation("更新服务信息")
    public Result<Object> updateService(@PathVariable(value = "serviceId", required = true) String serviceId) {
        return ServiceMockDataUtil.updateService();
    }

    @PostMapping(value = "/v1/service/{serviceId}/start")
    @ApiOperation("启动一个服务")
    public Result<Object> startService(@PathVariable(value = "serviceId", required = true) String serviceId) {
        return ServiceMockDataUtil.startService();
    }

    @PostMapping(value = "/v1/service/{serviceId}/stop")
    @ApiOperation("停止一个服务")
    public Result<Object> sstopService(@PathVariable(value = "serviceId", required = true) String serviceId) {
        return ServiceMockDataUtil.sstopService();
    }

    @PostMapping(value = "/v1/service/{serviceId}/deploy")
    @ApiOperation("部署一个服务")
    public Result<Object> deployService(@RequestBody @Validated DeployServiceParam deployServiceParam,@PathVariable(value = "serviceId", required = true) String serviceId) {
        return ServiceMockDataUtil.deployService();
    }

    @PostMapping(value = "/v1/service/{serviceId}/removedeploy")
    @ApiOperation("移除一个服务的部署")
    public Result<Object> removeDeployService(@RequestBody @Validated RemoveDeployServiceParam removeDeployServiceParam,@PathVariable(value = "serviceId", required = true) String serviceId) {
        return ServiceMockDataUtil.removeDeployService();
    }

    @PostMapping(value = "/v1/upload")
    @ApiOperation("服务文件上传")
    public Result<Object> uploadServiceFile(@RequestBody @Validated UploadServiceFileParam uploadServiceFileParam) {
        return ServiceMockDataUtil.uploadServiceFile();
    }

    @GetMapping(value = "/v1/nodes")
    @ApiOperation("查询所有节点")
    public Result<Object> queryAllNodes() {
        return ServiceMockDataUtil.queryAllNodes();
    }


    @GetMapping(value = "/v1/node/{nodeId}")
    @ApiOperation("查询节点信息【缓存信息】")
    public Result<QueryNodeDetailDTO> queryNodeDetail(@PathVariable(value = "nodeId", required = true) String nodeId) {
        return ServiceMockDataUtil.queryNodeDetail(nodeId);
    }

    @GetMapping(value = "/v1/stats")
    @ApiOperation("总体统计信息")
    public Result<AggregateStatisticsDTO> aggregateStatistics() {
        return ServiceMockDataUtil.aggregateStatistics();
    }

    @GetMapping(value = "/v1/stats/service/{serviceId}")
    @ApiOperation("单个服务统计")
    public Result<IndividualServiceStatisticsDTO> individualServiceStatistics(@PathVariable(value = "serviceId", required = true) String serviceId) {
        return ServiceMockDataUtil.individualServiceStatistics();
    }

    @GetMapping(value = "/v1/stats/node/{nodeId}")
    @ApiOperation("单个节点统计信息")
    public Result<IndividualNodeStatisticsDTO> individualNodeStatistics(@PathVariable(value = "nodeId", required = true) String nodeId) {
        return ServiceMockDataUtil.individualNodeStatistics();
    }

    @GetMapping(value = "/v1/stats/service/{serviceId}?node_id={nodeId}")
    @ApiOperation("单个节点的单个服务的统计信息")
    public Result<IndividualNodeServiceStatisticsDTO> individualNodeServiceStatistics(@PathVariable(value = "serviceId", required = true) String serviceId,
                                                                                      @PathVariable(value = "nodeId", required = true) String nodeId) {
        return ServiceMockDataUtil.individualNodeServiceStatistics();
    }

    @GetMapping(value = "/v1/log/{count}")
    @ApiOperation("获取最近的日志")
    public Result<LastLogDTO> getLastLogs(@PathVariable(value = "count", required = true) String count) {
        return ServiceMockDataUtil.getLastLogs(count);
    }

    @GetMapping(value = "/v1/logfile")
    @ApiOperation("下载日志文件")
    public Result<Object> downloadLogFile() {
        return ServiceMockDataUtil.downloadLogFile();
    }

    /**
     * 提交服务
     *
     * @param submitRegisterParam
     * @return
     */
    @PostMapping(value = "/submit-service")
    @ApiOperation("提交服务")
    public Result<Object> submitService(@RequestBody @Validated SubmitRegisterParam submitRegisterParam) {


        // 测试代码
        Result result = ServiceMockDataUtil.submitService(submitRegisterParam);
//        if (!ObjectUtils.isEmpty(result.getResult())) {
//            ServiceInfo serviceInfoEntity = serviceInfoService.getById(submitRegisterParam.getId());
//            if (serviceInfoEntity == null) {
//                result.error500("未找到对应实体");
//            } else {
//                serviceInfoEntity.setDeveloperStatus("1");
//                serviceInfoEntity.setServiceId(String.valueOf(result.getResult()));
//                boolean ok = serviceInfoService.updateById(serviceInfoEntity);
//                if (ok) {
//                    result.success("修改成功!");
//                }
//            }
//        }
        result.success("修改成功!");
        return Result.ok();
    }
}
