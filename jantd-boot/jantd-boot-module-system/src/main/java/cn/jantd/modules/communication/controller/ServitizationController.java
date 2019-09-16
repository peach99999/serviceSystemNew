package cn.jantd.modules.communication.controller;

import cn.jantd.core.api.vo.Result;
import cn.jantd.modules.communication.dto.communication.*;
import cn.jantd.modules.communication.param.*;
import cn.jantd.modules.communication.service.IServitizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 服务化前端通信接口
 * @Author xiagf
 * @date 2019-09-11
 */
@RestController
@RequestMapping("/communication")
@Api(tags = "服务化前端通信接口")
@Slf4j
public class ServitizationController {
    @Autowired
    private IServitizationService servitizationService;

    @GetMapping(value = "/query-all-services")
    @ApiOperation("查询所有服务")
    public Result<QueryServicesDTO> queryAllServices() {
        return servitizationService.queryAllServices();
    }

    @GetMapping(value = "/query-node-services")
    @ApiOperation("查询某个节点上的服务")
    public Result<QueryServicesDTO> queryNodeServices(@RequestParam(name = "nodeId") String nodeId) {
        return servitizationService.queryNodeServices(nodeId);
    }

    @PostMapping(value = "/register-service")
    @ApiOperation("注册一个服务")
    public Result<String> registerService(@RequestBody @Validated RegisterParam registerParam) {
        return servitizationService.registerService(registerParam);
    }

    @GetMapping(value = "/get-service-detail")
    @ApiOperation("查询单个服务")
    public Result<ServiceDetailDTO> getServiceDetail(@RequestParam(name = "serviceId") String serviceId) {
        return servitizationService.getServiceDetail(serviceId);
    }

    @DeleteMapping(value = "/delete-Service")
    @ApiOperation("删除一个服务")
    public Result<Object> deleteService(@RequestParam(name = "serviceId") String serviceId) {
        return servitizationService.deleteService(serviceId);
    }

    @PutMapping(value = "/update-Service")
    @ApiOperation("更新服务信息")
    public Result<Object> updateService(@RequestBody UpdateServiceParam updateServiceParam) {
        return servitizationService.updateService(updateServiceParam);
    }

    @PostMapping(value = "/start-service")
    @ApiOperation("启动一个服务")
    public Result<Object> startService(@RequestBody @Validated StartStopServiceParam startStopServiceParam) {
        return servitizationService.startService(startStopServiceParam);
    }

    @PostMapping(value = "/stop-service")
    @ApiOperation("停止一个服务")
    public Result<Object> sstopService(@RequestBody @Validated StartStopServiceParam startStopServiceParam) {
        return servitizationService.stopService(startStopServiceParam);
    }

    @PostMapping(value = "/deploy-service")
    @ApiOperation("部署一个服务")
    public Result<Object> deployService(@RequestBody @Validated DeployServiceParam deployServiceParam) {
        return servitizationService.deployService(deployServiceParam);
    }

    @PostMapping(value = "/remove-deploy-service")
    @ApiOperation("移除一个服务的部署")
    public Result<Object> removeDeployService(@RequestBody @Validated RemoveDeployServiceParam removeDeployServiceParam) {
        return servitizationService.removeDeployService(removeDeployServiceParam);
    }

    @PostMapping(value = "/upload-service-file")
    @ApiOperation("服务文件上传")
    public Result<Object> uploadServiceFile(@RequestBody @Validated UploadServiceFileParam uploadServiceFileParam) {
        return servitizationService.uploadServiceFile(uploadServiceFileParam);
    }

    @GetMapping(value = "/query-all-node")
    @ApiOperation("查询所有节点")
    public Result<Object> queryAllNodes() {
        return servitizationService.queryAllNodes();
    }


    @GetMapping(value = "/query-node-detail")
    @ApiOperation("查询节点信息【缓存信息】")
    public Result<QueryNodeDetailDTO> queryNodeDetail(@RequestParam(name = "nodeId") String nodeId) {
        return servitizationService.queryNodeDetail(nodeId);
    }

    @GetMapping(value = "/aggregate-statistics")
    @ApiOperation("总体统计信息")
    public Result<AggregateStatisticsDTO> aggregateStatistics() {
        return servitizationService.aggregateStatistics();
    }

    @GetMapping(value = "/query-individual-service-statistics")
    @ApiOperation("单个服务统计")
    public Result<IndividualServiceStatisticsDTO> individualServiceStatistics(@RequestParam(name = "serviceId") String serviceId) {
        return servitizationService.individualServiceStatistics(serviceId);
    }

    @GetMapping(value = "/query-individual-node-Statistics")
    @ApiOperation("单个节点统计信息")
    public Result<IndividualNodeStatisticsDTO> individualNodeStatistics(@RequestParam(name = "nodeId") String nodeId) {
        return servitizationService.individualNodeStatistics(nodeId);
    }

    @GetMapping(value = "/query-individual-node-service-statistics")
    @ApiOperation("单个节点的单个服务的统计信息")
    public Result<IndividualNodeServiceStatisticsDTO> individualNodeServiceStatistics(@RequestParam(name = "nodeId") String nodeId,
                                                                               @RequestParam(name = "serviceId") String serviceId) {
        return servitizationService.individualNodeServiceStatistics(nodeId,serviceId);
    }

    @GetMapping(value = "/get-last-log")
    @ApiOperation("获取最近的日志")
    public Result<LastLogDTO> getLastLogs(@RequestParam(name = "count") String count) {
        return servitizationService.getLastLogs(count);
    }

    @GetMapping(value = "/download-Log-File")
    @ApiOperation("下载日志文件")
    public Result<Object> downloadLogFile() {
        return servitizationService.downloadLogFile();
    }
}
